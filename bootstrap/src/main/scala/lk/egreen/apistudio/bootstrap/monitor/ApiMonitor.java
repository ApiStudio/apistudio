package lk.egreen.apistudio.bootstrap.monitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by dewmal on 8/5/16.
 */

@Path("resource")
public class ApiMonitor {

    private static final Logger LOGGER = LogManager.getLogger(ApiMonitor.class);


    @Inject
    Provider<MonitoringStatistics> monitoringStatisticsProvider;

    @GET
    public String getSomething() {
        LOGGER.info(monitoringStatisticsProvider.get());

        final MonitoringStatistics snapshot
                = monitoringStatisticsProvider.get().snapshot();

        final TimeWindowStatistics timeWindowStatistics
                = snapshot.getRequestStatistics()
                .getTimeWindowStatistics().get(0l);




        return "request count: " + timeWindowStatistics.getRequestCount()
                + ", average request processing [ms]: "
                + timeWindowStatistics.getAverageDuration();
    }


}
