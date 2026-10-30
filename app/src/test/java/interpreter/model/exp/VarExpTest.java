package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VarExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    @Test
    void returnsValueOfIntVariable() throws MyException {
        symTable.put("v", new IntValue(2));
        assertEquals(new IntValue(2), new VarExp("v").eval(symTable));
    }

    @Test
    void returnsValueOfBoolVariable() throws MyException {
        symTable.put("a", new BoolValue(true));
        assertEquals(new BoolValue(true), new VarExp("a").eval(symTable));
    }

    @Test
    void undefinedVariableThrows() {
        IExp e = new VarExp("x");
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}