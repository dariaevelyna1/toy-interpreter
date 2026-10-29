package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyListTest {
    private MyList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyList<>();
    }

    @Test
    void newListHasSizeZero() {
        assertEquals(0, list.size());
    }

    @Test
    void addIncreasesSize() {
        list.add(10);
        list.add(20);
        assertEquals(2, list.size());
    }

    @Test
    void getReturnsElementsInOrderOfAdding() throws MyException {
        list.add(10);
        list.add(20);
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void getWithInvalidIndexThrows() {
        list.add(10);
        assertThrows(ADTException.class, () -> list.get(5));
    }
}