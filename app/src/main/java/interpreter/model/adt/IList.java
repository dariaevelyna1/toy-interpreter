package interpreter.model.adt;

public interface IList<T> {
    void add(T element);
    int size();
    T get(int index);
}
