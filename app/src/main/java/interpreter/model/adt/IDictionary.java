package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.Map;

public interface IDictionary<K,V> {
    V lookup(K key) throws ADTException;
    void update(K key, V newValue) throws ADTException;
    boolean isDefined(K key);
    void put(K key, V value);
    void remove(K key) throws ADTException;
    Map<K,V> toMap();


}
