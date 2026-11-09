package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void doesNotChangeTheState() throws MyException {
        PrgState result = new NopStmt().execute(state);
        assertSame(state, result);
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(0, state.getOut().size());
    }
}