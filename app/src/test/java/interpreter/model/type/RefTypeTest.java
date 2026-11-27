package interpreter.model.type;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.model.stmt.VarDeclStmt;
import interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTest {

    @Test
    void refVariableGetsDefaultValueZeroAndInnerType() throws MyException {
        PrgState state = PrgState.of(new NopStmt());
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);
        assertEquals(new RefValue(0, new IntType()), state.getSymTable().lookup("v"));
    }

    @Test
    void refRefIntDefaultValueHasRefIntInside() {
        RefType refRefInt = new RefType(new RefType(new IntType()));
        assertEquals(new RefValue(0, new RefType(new IntType())), refRefInt.defaultValue());
    }

    @Test
    void nestedRefTypesAreEqual() {
        assertEquals(new RefType(new RefType(new IntType())),
                new RefType(new RefType(new IntType())));
    }

    @Test
    void refRefIntDiffersFromRefInt() {
        assertNotEquals(new RefType(new RefType(new IntType())),
                new RefType(new IntType()));
    }

    @Test
    void refIntDiffersFromRefBool() {
        assertNotEquals(new RefType(new IntType()), new RefType(new BoolType()));
    }

    @Test
    void refValueHasRefTypeOfItsLocationType() {
        assertEquals(new RefType(new IntType()), new RefValue(1, new IntType()).getType());
        assertEquals(new RefType(new RefType(new IntType())),
                new RefValue(10, new RefType(new IntType())).getType());
    }
}