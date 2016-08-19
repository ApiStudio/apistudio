package io.egreen.apistudio.bootstrap;

import org.glassfish.tyrus.spi.ServerContainer;

import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.http.server.AddOn;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpServerFilter;
import org.glassfish.grizzly.http.server.NetworkListener;


/**
 * Created by dewmal on 8/19/16.
 */
public class ApiWebSocketAddOn implements AddOn {
    private final ServerContainer serverContainer;
    private final String contextPath;

    ApiWebSocketAddOn(ServerContainer serverContainer, String contextPath) {
        this.serverContainer = serverContainer;
        this.contextPath = contextPath;
    }

    @Override
    public void setup(NetworkListener networkListener, FilterChainBuilder builder) {
        // Get the index of HttpServerFilter in the HttpServer filter chain
        final int httpServerFilterIdx = builder.indexOfType(HttpServerFilter.class);

        if (httpServerFilterIdx >= 0) {
            // Insert the WebSocketFilter right before HttpServerFilter
            builder.add(httpServerFilterIdx, new GrizzlyServerFilter(serverContainer, contextPath));
        }

    }
}