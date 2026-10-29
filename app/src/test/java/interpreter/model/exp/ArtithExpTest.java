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

class ArithExpTest {
    private IDictionary<String, IValue> symTable;

    @BeforeEach
    void setUp() {
        symTable = new MyDictionary<>();
    }

    private static IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void additionWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.ADD, num(2), num(3));
        assertEquals(new IntValue(5), e.eval(symTable));
    }

    @Test
    void subtractionWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.SUB, num(7), num(3));
        assertEquals(new IntValue(4), e.eval(symTable));
    }

    @Test
    void multiplicationWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.MUL, num(4), num(5));
        assertEquals(new IntValue(20), e.eval(symTable));
    }

    @Test
    void divisionWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.DIV, num(10), num(2));
        assertEquals(new IntValue(5), e.eval(symTable));
    }

    @Test
    void respectsOperatorPrecedenceEncodedInTheTree() throws MyException {
        // 2 + 3 * 5
        IExp e = new ArithExp(ArithOp.ADD, num(2),
                new ArithExp(ArithOp.MUL, num(3), num(5)));
        assertEquals(new IntValue(17), e.eval(symTable));
    }

    @Test
    void divisionByZeroThrows() {
        IExp e = new ArithExp(ArithOp.DIV, num(1), num(0));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    @Test
    void firstOperandNotIntegerThrows() {
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), num(1));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    @Test
    void secondOperandNotIntegerThrows() {
        IExp e = new ArithExp(ArithOp.ADD, num(1), new ValueExp(new BoolValue(true)));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    @Test
    void usesVariableFromSymTable() throws MyException {
        symTable.put("a", new IntValue(17));
        IExp e = new ArithExp(ArithOp.ADD, new VarExp("a"), num(1));
        assertEquals(new IntValue(18), e.eval(symTable));
    }

    @Test
    void undefinedVariableInOperandThrows() {
        IExp e = new ArithExp(ArithOp.ADD, new VarExp("x"), num(1));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }
}