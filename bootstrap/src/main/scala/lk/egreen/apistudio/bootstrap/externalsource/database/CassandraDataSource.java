package lk.egreen.apistudio.bootstrap.externalsource.database;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

/**
 * Created by dewmal on 7/19/16.
 */
@ApplicationScoped
public class CassandraDataSource {

    private static final Logger LOGGER = LogManager.getLogger(CassandraDataSource.class);

    private Cluster cluster;
    private Session session;
    private MappingManager manager;

    @PostConstruct
    void init() {
        connect(System.getProperty("cassandra.node", "localhost"));
        LOGGER.info("Connect Cassandra Done...");
    }

    public void connect(String node) {
        if (session == null || session.isClosed()) {


            cluster = Cluster.builder()
                    .addContactPoint(node)
                    .build();
            Metadata metadata = cluster.getMetadata();
            LOGGER.info("Connected to cluster: %s\n" +
                    metadata.getClusterName());
            for (Host host : metadata.getAllHosts()) {
                LOGGER.info("Datatacenter: %s; Host: %s; Rack: %s\n" +
                        host.getDatacenter() + " " + host.getAddress() + " " + host.getRack());
            }

            if (session != null) {
                session.close();
            }


            session = cluster.connect();


            if (manager == null) {
                manager = new MappingManager(session);
            }
        }
    }

    public void close() {
        cluster.close();
    }


    public Cluster getCluster() {
        return cluster;
    }

    public Session getSession() {
        return session;
    }

    public MappingManager getManager() {
        return manager;
    }
}
