package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {

    @Test
    void allocateReturnsOneTwoThree() {
        MyHeap heap = new MyHeap();
        assertEquals(1, heap.allocate(new IntValue(10)));
        assertEquals(2, heap.allocate(new IntValue(20)));
        assertEquals(3, heap.allocate(new IntValue(30)));
    }

    @Test
    void getReturnsTheStoredValue() throws ADTException {
        MyHeap heap = new MyHeap();
        int address = heap.allocate(new IntValue(7));
        assertEquals(new IntValue(7), heap.get(address));
    }

    @Test
    void updateChangesTheValue() throws ADTException {
        MyHeap heap = new MyHeap();
        int address = heap.allocate(new IntValue(7));
        heap.update(address, new IntValue(8));
        assertEquals(new IntValue(8), heap.get(address));
    }

    @Test
    void getMissingAddressThrows() {
        MyHeap heap = new MyHeap();
        assertThrows(ADTException.class, () -> heap.get(1));
    }

    @Test
    void getAddressZeroThrows() {
        MyHeap heap = new MyHeap();
        heap.allocate(new IntValue(7));
        assertThrows(ADTException.class, () -> heap.get(0));
    }

    @Test
    void updateMissingAddressThrows() {
        MyHeap heap = new MyHeap();
        assertThrows(ADTException.class, () -> heap.update(5, new IntValue(1)));
    }

    @Test
    void addressesAreNotReusedAfterSetContent() {
        MyHeap heap = new MyHeap();
        heap.allocate(new IntValue(1));
        heap.allocate(new IntValue(2));
        heap.setContent(Map.of());           // as if the garbage collector removed everything
        assertEquals(3, heap.allocate(new IntValue(3)));
    }
}