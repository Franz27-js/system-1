package DaFraDa.dobot.awattar;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AwattarApi {
	
	public static List<PriceEntry> fetchMarketPrices() {
        List<PriceEntry> prices = new ArrayList<>();
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.awattar.de/v1/marketdata"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());

            JsonNode data = root.get("data");
            if (data != null && data.isArray()) {
            	int index = 0;
                for (JsonNode node : data) {
                    PriceEntry entry = mapper.treeToValue(node, PriceEntry.class);
                    entry.setHourIndex(index++);
                    prices.add(entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prices;
    }
}
