package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ValueExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {
    private PrgState state;
    private final IStmt thenS = new PrintStmt(new ValueExp(new IntValue(1)));
    private final IStmt elseS = new PrintStmt(new ValueExp(new IntValue(2)));

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void trueConditionPushesThenBranch() throws MyException {
        new IfStmt(new ValueExp(new BoolValue(true)), thenS, elseS).execute(state);
        assertEquals(thenS, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void falseConditionPushesElseBranch() throws MyException {
        new IfStmt(new ValueExp(new BoolValue(false)), thenS, elseS).execute(state);
        assertEquals(elseS, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    void nonBooleanConditionThrows() {
        IStmt s = new IfStmt(new ValueExp(new IntValue(5)), thenS, elseS);
        assertThrows(StatementException.class, () -> s.execute(state));
    }
}