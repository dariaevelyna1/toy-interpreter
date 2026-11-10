package interpreter.model.value;

import interpreter.model.PrgState;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {

    @Test
    void stringVariableGetsDefaultValueEmptyString() throws Exception {
        PrgState state = PrgState.of(new VarDeclStmt("s", new StringType()));
        new VarDeclStmt("s", new StringType()).execute(state);
        assertEquals(new StringValue(""), state.getSymTable().lookup("s"));
    }

    @Test
    void equalStringValuesAreEqualsAndHaveSameHashCode() {
        StringValue a = new StringValue("hello");
        StringValue b = new StringValue("hello");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}