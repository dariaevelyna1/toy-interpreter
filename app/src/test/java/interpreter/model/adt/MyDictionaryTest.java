package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import interpreter.exceptions.MyException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {
    private MyDictionary<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new MyDictionary<>();
    }

    @Test
    void keyIsNotDefinedInNewDictionary() {
        assertFalse(dict.isDefined("a"));
    }

    @Test
    void putThenLookupReturnsValue() throws MyException {
        dict.put("a", 5);
        assertTrue(dict.isDefined("a"));
        assertEquals(5, dict.lookup("a"));
    }

    @Test
    void updateChangesValue() throws MyException {
        dict.put("a", 5);
        dict.update("a", 7);
        assertEquals(7, dict.lookup("a"));
    }

    @Test
    void lookupOfMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.lookup("x"));
    }

    @Test
    void updateOfMissingKeyThrows() {
        assertThrows(ADTException.class, () -> dict.update("x", 1));
    }
}