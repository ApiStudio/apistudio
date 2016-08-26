package io.egreen.apistudio.datalayer.mongodb.dao;

import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import java.util.List;

/**
 * Created by dewmal on 8/4/16.
 */
public interface DAOController<T> {


    Key<T> create(T entity);

    UpdateResults update(Key<T> key, UpdateOperations<T> updateModel);

    void delete(Key<T> key);

    T get(Object key);

    T get(String keyName, Object key);

    List<T> getAll(int offset, int limit);
}
