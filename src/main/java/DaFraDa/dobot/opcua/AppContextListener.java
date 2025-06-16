package DaFraDa.dobot.opcua;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    private OpcuaClient clientService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        clientService = new OpcuaClient();
        new Thread(clientService::start).start(); // im Hintergrund starten
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        clientService.stop();
    }
}
