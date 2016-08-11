package io.egreen.apistudio.monitor.api;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dewmal on 8/11/16.
 */
@Path("/request")
public class RequestCountService {
    private static final Logger LOGGER = LogManager.getLogger(RequestCountService.class);


    @Inject
    Provider<MonitoringStatistics> monitoringStatisticsProvider;


    @Path("/total")
    @GET
    public long getTotalCount() {
        final MonitoringStatistics snapshot
                = monitoringStatisticsProvider.get().snapshot();

        final TimeWindowStatistics timeWindowStatistics
                = snapshot.getRequestStatistics()
                .getTimeWindowStatistics().get(0l);
        return timeWindowStatistics.getRequestCount();
    }

}
