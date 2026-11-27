package interpreter.model.stmt;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.ReadHeapExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.exp.VarExp;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapStmtTest {
    private PrgState state;

    @BeforeEach
    void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);   // Ref int v
        new VarDeclStmt("x", new IntType()).execute(state);                // int x
    }



    @Test
    void newAllocatesAtAddressOneAndUpdatesTheVariable() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    void newWithUndeclaredVariableThrows() {
        NewStmt stmt = new NewStmt("nope", new ValueExp(new IntValue(1)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    void newWithNonReferenceVariableThrows() {
        NewStmt stmt = new NewStmt("x", new ValueExp(new IntValue(1)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    void newWithAValueOfTheWrongTypeThrows() {
        NewStmt stmt = new NewStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    // ---------- rH ----------

    @Test
    void readHeapReadsTheValue() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        ReadHeapExp exp = new ReadHeapExp(new VarExp("v"));
        assertEquals(new IntValue(20), exp.eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void readHeapOfNonReferenceThrows() {
        ReadHeapExp exp = new ReadHeapExp(new VarExp("x"));
        assertThrows(ExpressionException.class,
                () -> exp.eval(state.getSymTable(), state.getHeap()));
    }

    @Test
    void readHeapOfNullReferenceThrows() {
        // v is still (0, int): address 0 is not in the heap
        ReadHeapExp exp = new ReadHeapExp(new VarExp("v"));
        assertThrows(ExpressionException.class,
                () -> exp.eval(state.getSymTable(), state.getHeap()));
    }

    // ---------- wH ----------

    @Test
    void writeHeapChangesTheLocation() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);
        assertEquals(new IntValue(30), state.getHeap().get(1));
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v")); // v unchanged
    }

    @Test
    void writeHeapWithNonReferenceVariableThrows() {
        WriteHeapStmt stmt = new WriteHeapStmt("x", new ValueExp(new IntValue(1)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    void writeHeapWithAddressNotInHeapThrows() {
        // v is (0, int), nothing was allocated
        WriteHeapStmt stmt = new WriteHeapStmt("v", new ValueExp(new IntValue(1)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    void writeHeapWithWrongTypeThrows() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        WriteHeapStmt stmt = new WriteHeapStmt("v", new ValueExp(new BoolValue(true)));
        assertThrows(StatementException.class, () -> stmt.execute(state));
    }
}