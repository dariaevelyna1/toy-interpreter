package interpreter.model.value;

import interpreter.model.type.BoolType;
import interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ValueTest {

    @Test
    void intValueHasIntType() {
        assertEquals(new IntType(), new IntValue(5).getType());
    }

    @Test
    void boolValueHasBoolType() {
        assertEquals(new BoolType(), new BoolValue(true).getType());
    }

    @Test
    void intValuesWithSameNumberAreEqual() {
        assertEquals(new IntValue(5), new IntValue(5));
    }

    @Test
    void intValuesWithDifferentNumbersAreNotEqual() {
        assertNotEquals(new IntValue(5), new IntValue(6));
    }
}