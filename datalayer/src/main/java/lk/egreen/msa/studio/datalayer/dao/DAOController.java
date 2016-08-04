package lk.egreen.msa.studio.datalayer.dao;

/**
 * Created by dewmal on 8/4/16.
 */
public interface DAOController<K, T> {


    T create(T entity);

    T update(T entity);

    void delete(K key);

    T get(K key);


}
