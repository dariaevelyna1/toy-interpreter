package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void pushesFirstOnTopOfSecond() throws MyException {
        IStmt first = new PrintStmt(new ValueExp(new IntValue(1)));
        IStmt second = new PrintStmt(new ValueExp(new IntValue(2)));

        new CompStmt(first, second).execute(state);

        assertEquals(first, state.getExeStack().pop());
        assertEquals(second, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }
}