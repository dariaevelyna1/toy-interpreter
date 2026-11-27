package interpreter.controller;

import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GarbageCollectorTest {
    // the state of Ex9 after new(v, 30): v -> (3, int), a -> (2, Ref(int))
    private final List<IValue> symTable = List.of(
            new RefValue(3, new IntType()),
            new RefValue(2, new RefType(new IntType())));
    private final Map<Integer, IValue> heap = Map.of(
            1, new IntValue(20),
            2, new RefValue(1, new IntType()),
            3, new IntValue(30),
            4, new IntValue(7));

    @Test
    void getAddrFromSymTableFindsTheAddresses() {
        assertEquals(Set.of(2, 3), GarbageCollector.getAddrFromSymTable(symTable));
    }

    @Test
    void unsafeCollectorRemovesLocationReachableOnlyThroughHeap() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.unsafeGarbageCollector(roots, heap);
        assertEquals(Set.of(2, 3), kept.keySet());   // 1 is wrongly removed
    }

    @Test
    void keepsTheLocationsReachableThroughTheHeap() {
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.safeGarbageCollector(roots, heap);
        assertEquals(Set.of(1, 2, 3), kept.keySet());   // 4 is garbage, 1 is not
    }

    @Test
    void reachableAddressesFollowsALongChain() {
        // 1 -> 2 -> 3 -> 4 (an int), and 5 is unreachable
        Map<Integer, IValue> chain = Map.of(
                1, new RefValue(2, new RefType(new RefType(new IntType()))),
                2, new RefValue(3, new RefType(new IntType())),
                3, new RefValue(4, new IntType()),
                4, new IntValue(1),
                5, new IntValue(2));
        assertEquals(Set.of(1, 2, 3, 4),
                GarbageCollector.reachableAddresses(Set.of(1), chain));
    }

    @Test
    void nullReferenceKeepsNothing() {
        List<IValue> onlyNull = List.of(new RefValue(0, new IntType()));
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(onlyNull);
        Map<Integer, IValue> kept = GarbageCollector.safeGarbageCollector(roots, heap);
        assertTrue(kept.isEmpty());
    }
}