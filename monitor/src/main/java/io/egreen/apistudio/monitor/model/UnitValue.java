package io.egreen.apistudio.monitor.model;

/**
 * Created by dewmal on 8/15/16.
 */
public class UnitValue<K, T> {

    private K key;
    private T data;

    public UnitValue() {
    }

    public UnitValue(K key, T data) {
        this.key = key;
        this.data = data;
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
