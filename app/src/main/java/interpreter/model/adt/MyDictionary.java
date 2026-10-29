package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements IDictionary<K,V>{

    private final Map<K,V> dict = new HashMap<K,V>();


    @Override
    public V lookup(K key) {
        return dict.get(key);
    }


    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public void put(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if(! dict.containsKey(key)) {
            throw new ADTException("key not defined");
        }
        dict.put(key, value);
    }
    @Override
    public String toString() {
        return dict.toString();
    }


}
