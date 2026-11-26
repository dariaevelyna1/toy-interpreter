import interpreter.model.adt.IHeap;
import interpreter.model.value.IValue;

import java.util.HashMap;
import java.util.Map;

public class MyHeap implements IHeap {
    private Map<Integer, IValue> heap = new HashMap<>();
    private int nextFree = 1; // address 0 means null
    @Override
    public int allocate(IValue value) {
        int address = nextFree++;
        heap.put(address, value);
        return address;
    }
    @Override public void setContent(Map<Integer, IValue> content) {
        heap = new HashMap<>(content);
    }
    // TODO: get and update (a missing address -> ADTException), isDefined,
    // getContent (Map.copyOf(heap)), toString
}
