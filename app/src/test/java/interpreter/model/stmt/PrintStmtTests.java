package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrintStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void addsValueToOut() throws MyException {
        new PrintStmt(new ValueExp(new IntValue(7))).execute(state);
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(7), state.getOut().get(0));
    }

    @Test
    void addsValueOfExpressionToOut() throws MyException {
        new PrintStmt(new ArithExp(ArithOp.MUL,
                new ValueExp(new IntValue(3)), new ValueExp(new IntValue(4)))).execute(state);
        assertEquals(new IntValue(12), state.getOut().get(0));
    }

    @Test
    void keepsOrderOfPrints() throws MyException {
        new PrintStmt(new ValueExp(new IntValue(1))).execute(state);
        new PrintStmt(new ValueExp(new IntValue(2))).execute(state);
        assertEquals(new IntValue(1), state.getOut().get(0));
        assertEquals(new IntValue(2), state.getOut().get(1));
    }

    @Test
    void printingUndefinedVariableThrows() {
        IStmt s = new PrintStmt(new VarExp("x"));
        assertThrows(ExpressionException.class, () -> s.execute(state));
    }
}