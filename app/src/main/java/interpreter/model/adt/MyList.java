package interpreter.model.adt;

import interpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<T>();

    @Override
    public void add(T element) {
        list.add(element);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T get(int index) throws ADTException {
        if (index < 0 || index >= list.size()) {
            throw new ADTException("index " + index + " is out of bounds");
        }
        return list.get(index);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public List<T> toList() {
        return List.copyOf(list);
    }

}
