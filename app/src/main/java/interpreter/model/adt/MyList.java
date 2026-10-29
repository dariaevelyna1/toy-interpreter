package interpreter.model.adt;

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
    public T get(int i) {
        return list.get(i);
    }


}
