package DaFraDa.dobot.opcua;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.QualifiedName;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;

import DaFraDa.dobot.database.Database;

public class OpcuaClient {

	private OpcUaClient client;
	private String opcuaUrl = "opc.tcp://";
	private AtomicReference<Double> temperatureCache;
	private AtomicReference<Double> humidityCache;

    public void start() {
        try {
            client = OpcUaClient.create(opcuaUrl);
            client.connect().get();

            UaSubscription subscription = client.getSubscriptionManager()
                    .createSubscription(1000.0).get();

            // Temperatur NodeId anpassen
            NodeId tempNode = new NodeId(2, "Sensor.Temperature");
            NodeId humNode = new NodeId(2, "Sensor.Humidity");

            // Temperatur überwachen
            subscribeToNode(subscription, tempNode, "Temperatur");
            subscribeToNode(subscription, humNode, "Luftfeuchtigkeit");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void subscribeToNode(UaSubscription subscription, NodeId nodeId, String label) throws Exception {
        ReadValueId readValueId = new ReadValueId(
            nodeId, AttributeId.Value.uid(), null, QualifiedName.NULL_VALUE);

        //MonitoringParameters parameters = new MonitoringParameters(
            //UInteger.valueOf(1), 1000.0, null, UInteger.valueOf(10), true);
        MonitoringParameters parameters = new MonitoringParameters(
                Unsigned.uint(1), 1000.0, null, Unsigned.uint(10), true);

        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);

        BiConsumer<UaMonitoredItem, DataValue> listener = (item, value) -> {
            Object val = value.getValue().getValue();
            String nodeIdStr = item.getReadValueId().getNodeId().getIdentifier().toString();

            if (nodeIdStr.contains("Temperatur")) {
                temperatureCache.set(((Number) val).doubleValue());
            } else if (nodeIdStr.contains("Luftfeuchtigkeit")) {
                humidityCache.set(((Number) val).doubleValue());
            }

            if (temperatureCache.get() != null && humidityCache.get() != null) {
            	List<Object> dataToDb = new ArrayList<>();
            	dataToDb.add(humidityCache.get());
            	dataToDb.add(temperatureCache.get());
                Database.writeDB("sensor_data", dataToDb);
            }
        };

        subscription.createMonitoredItems(
            TimestampsToReturn.Both,
            java.util.Collections.singletonList(request),
            (items, results) -> ((Iterable<MonitoredItemCreateRequest>) items).forEach(item -> ((UaMonitoredItem) item).setValueConsumer((Consumer<DataValue>) listener))
        ).get();
    }

    public void stop() {
        try {
            if (client != null) {
                client.disconnect().get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

