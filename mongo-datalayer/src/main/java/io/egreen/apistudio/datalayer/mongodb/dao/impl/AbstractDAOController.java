package io.egreen.apistudio.datalayer.mongodb.dao.impl;

import com.mongodb.MongoClient;
import io.egreen.apistudio.bootstrap.ApiStudio;
import io.egreen.apistudio.datalayer.mongodb.dao.DAOController;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by dewmal on 8/23/16.
 */
public class AbstractDAOController<T> implements DAOController<T> {


    protected Class<T> entityClass;

    private Datastore datastore;

    @PostConstruct
    protected void init() {
        final Morphia morphia = new Morphia();
// tell Morphia where to find your classes
// can be called multiple times with different packages or classes
        morphia.mapPackage(ApiStudio.getApplicationClass().getPackage().getName());
// create the Datastore connecting to the default port on the local host
        datastore = morphia.createDatastore(new MongoClient(), ApiStudio.getApplicationName());
        datastore.ensureIndexes();
    }


    public AbstractDAOController() {
//        throw new ExceptionInInitializerError("Please overide AbstractDAOController(Class<T> entityClass)");
    }

    public AbstractDAOController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public Key<T> create(T entity) {
        return datastore.save(entity);
    }

    public UpdateResults update(Key<T> key, UpdateOperations<T> updateModel) {
        return datastore.update(key, updateModel);
    }

    public void delete(Key<T> key) {
        datastore.delete(entityClass, key.getId());
    }


    public T get(Object key) {
        return get("code", key);
    }

    public T get(String keyName, Object key) {
        Query<T> query = datastore.createQuery(entityClass);
        query.filter(keyName + " =", key);
        return query.get();
    }

    @Override
    public List<T> getAll(int offset, int limit) {
        Query<T> tQuery = getDatastore().createQuery(entityClass);
        if (offset > 0) {
            tQuery.offset(offset);
        }
        if (limit > 0) {
            tQuery.limit(limit);
        }
        return tQuery.asList();
    }

    protected Datastore getDatastore() {
        return datastore;
    }
}
