package interpreter.model.adt;

import interpreter.exceptions.ADTException;

public interface IDictionary<K,V> {
    V lookup(K key) throws ADTException;
    void update(K key, V newValue) throws ADTException;
    boolean isDefined(K key);
    void put(K key, V value);

}
