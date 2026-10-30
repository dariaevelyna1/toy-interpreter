package interpreter.model.adt;

import interpreter.exceptions.ADTException;

public interface IList<T> {
    void add(T element);
    int size();
    T get(int index) throws ADTException;
}
