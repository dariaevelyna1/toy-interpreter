package interpreter.model.type;

import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TypeTest {

    @Test
    void intDefaultValueIsZero() {
        assertEquals(new IntValue(0), new IntType().defaultValue());
    }

    @Test
    void boolDefaultValueIsFalse() {
        assertEquals(new BoolValue(false), new BoolType().defaultValue());
    }

    @Test
    void twoIntTypesAreEqual() {
        assertEquals(new IntType(), new IntType());
    }

    @Test
    void intTypeIsNotEqualToBoolType() {
        assertNotEquals(new IntType(), new BoolType());
    }
}