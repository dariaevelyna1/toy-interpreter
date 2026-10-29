package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.MyDictionary;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArtithExpTest {
    MyDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();
    IExp num(int n) {
        return new ValueExp(new IntValue(n));
    }

    @Test
    void additionExpWorks() throws MyException {

        IExp e = new ArithExp(ArithOp.ADD, num(2), num(3));
        assertEquals(new IntValue(5), e.eval(symTable));


    }

    @Test
    void substractionWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.SUB, num(5), num(4));
        assertEquals(new IntValue(1), e.eval(symTable));
    }

    @Test
    void multiplicationWorks() throws MyException {
        IExp e = new ArithExp(ArithOp.MUL,num(5), num(4) );
        assertEquals(new IntValue(20), e.eval(symTable));
    }

    @Test
    void divisionWorks() throws MyException{
        IExp e = new ArithExp(ArithOp.DIV, num(100), num(4));
        assertEquals(new IntValue(25), e.eval(symTable));
    }

    @Test
    void divisionDoesntWorkForZero() throws MyException {
        IExp e = new ArithExp(ArithOp.DIV, num(10), num(0));
        assertThrows(ExpressionException.class, () ->  e.eval(symTable));
    }

    @Test
    void expressionsWtihVariablesFromSymTable() throws MyException {
        symTable.put("a", new IntValue(3));
        IExp e = new ArithExp(ArithOp.ADD, num(2), new VarExp("a"));
        assertEquals(new IntValue(5), e.eval(symTable) );
    }
    @Test
    void nonIntegerOperandThrows() {
        IExp e = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), num(1));
        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

}
