package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssignStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    void assignsValueToDeclaredVariable() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        new AssignStmt("v", new ValueExp(new IntValue(2))).execute(state);
        assertEquals(new IntValue(2), state.getSymTable().lookup("v"));
    }

    @Test
    void assignsResultOfExpression() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        new AssignStmt("v", new ArithExp(ArithOp.ADD,
                new ValueExp(new IntValue(2)), new ValueExp(new IntValue(3)))).execute(state);
        assertEquals(new IntValue(5), state.getSymTable().lookup("v"));
    }

    @Test
    void assignmentToUndeclaredVariableThrows() {
        IStmt s = new AssignStmt("x", new ValueExp(new IntValue(1)));
        assertThrows(StatementException.class, () -> s.execute(state));
    }

    @Test
    void typeMismatchThrows() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);
        IStmt s = new AssignStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(StatementException.class, () -> s.execute(state));
    }
}