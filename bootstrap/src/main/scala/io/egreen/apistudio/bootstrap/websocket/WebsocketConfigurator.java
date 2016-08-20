package io.egreen.apistudio.bootstrap.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.weld.environment.se.WeldContainer;

import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by dewmal on 8/20/16.
 */
public class WebsocketConfigurator extends ServerEndpointConfig.Configurator {

    private static final Logger LOGGER = LogManager.getLogger(WebsocketConfigurator.class);

    public WebsocketConfigurator() {
        LOGGER.info("Web Socket working..");
    }

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {

        if (!endpointClass.isAnnotationPresent(ServerEndpoint.class)) {
            throw new InstantiationException(endpointClass.getName() + " wrong endpoint");
        }
        T t = WeldContainer.current().select(endpointClass).get();
        LOGGER.info(t);
        return t;

    }

}
