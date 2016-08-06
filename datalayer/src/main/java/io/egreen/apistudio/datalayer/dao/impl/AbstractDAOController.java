package io.egreen.apistudio.datalayer.dao.impl;

import com.datastax.driver.mapping.MappingSession;
import io.egreen.apistudio.bootstrap.database.CassandraDataSource;
import io.egreen.apistudio.datalayer.dao.DAOController;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Created by dewmal on 8/4/16.
 */
public class AbstractDAOController<K, T> implements DAOController<K, T> {


    private Class<K> keyClass;
    private Class<T> entityClass;

    @Inject
    protected CassandraDataSource cassandraDataSource;
    private MappingSession mappingSession;

    @PostConstruct
    protected void init() {
        mappingSession = new MappingSession(cassandraDataSource.getName_space(), cassandraDataSource.getSession());
    }


    public AbstractDAOController() {
    }

    public AbstractDAOController(Class<K> keyClass, Class<T> entityClass) {
        this.keyClass = keyClass;
        this.entityClass = entityClass;
    }

    public T create(T entity) {
        return mappingSession.save(entity);
    }

    public T update(T entity) {
        return mappingSession.save(entity);
    }

    public void delete(K key) {

    }

    public T get(K key) {
        return null;
    }


}
