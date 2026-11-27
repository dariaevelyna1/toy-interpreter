package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ArithExp;
import interpreter.model.exp.ArithOp;
import interpreter.model.exp.RelOp;
import interpreter.model.exp.RelationalExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {

    // int v; v = start; int count;
    // while (v > 0) (count = count + 1; v = v - 1)
    // -> count = number of times the body ran
    private int runLoop(int start) throws MyException {
        IStmt program =
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(start))),
                                new CompStmt(new VarDeclStmt("count", new IntType()),
                                        new WhileStmt(
                                                new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                                                new CompStmt(
                                                        new AssignStmt("count", new ArithExp(ArithOp.ADD,
                                                                new VarExp("count"), new ValueExp(new IntValue(1)))),
                                                        new AssignStmt("v", new ArithExp(ArithOp.SUB,
                                                                new VarExp("v"), new ValueExp(new IntValue(1)))))))));

        PrgState state = PrgState.of(program);
        while (!state.getExeStack().isEmpty()) {
            IStmt stmt = state.getExeStack().pop();
            stmt.execute(state);
        }
        IntValue count = (IntValue) state.getSymTable().lookup("count");
        return count.val();
    }

    @Test
    void loopRunsZeroTimes() throws MyException {
        assertEquals(0, runLoop(0));
    }

    @Test
    void loopRunsOnce() throws MyException {
        assertEquals(1, runLoop(1));
    }

    @Test
    void loopRunsSeveralTimes() throws MyException {
        assertEquals(5, runLoop(5));
    }

    @Test
    void whileWithANonBooleanConditionThrows() {
        PrgState state = PrgState.of(new NopStmt());
        WhileStmt loop = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());
        assertThrows(StatementException.class, () -> loop.execute(state));
    }
}