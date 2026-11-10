package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.model.PrgState;
import interpreter.model.stmt.NopStmt;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RelationalExpTest {

    private final PrgState state = PrgState.of(new NopStmt());

    private BoolValue eval(RelOp op, int a, int b) throws Exception {
        return (BoolValue) new RelationalExp(op,
                new ValueExp(new IntValue(a)), new ValueExp(new IntValue(b)))
                .eval(state.getSymTable());
    }

    @Test
    void lessThanTrueAndFalse() throws Exception {
        assertEquals(new BoolValue(true), eval(RelOp.LT, 3, 5));
        assertEquals(new BoolValue(false), eval(RelOp.LT, 5, 3));
    }

    @Test
    void lessOrEqualTrueAndFalse() throws Exception {
        assertEquals(new BoolValue(true), eval(RelOp.LE, 3, 5));
        assertEquals(new BoolValue(true), eval(RelOp.LE, 5, 5));
        assertEquals(new BoolValue(false), eval(RelOp.LE, 6, 5));
    }

    @Test
    void equalTrueAndFalse() throws Exception {
        assertEquals(new BoolValue(true), eval(RelOp.EQ, 5, 5));
        assertEquals(new BoolValue(false), eval(RelOp.EQ, 5, 6));
    }

    @Test
    void notEqualTrueAndFalse() throws Exception {
        assertEquals(new BoolValue(true), eval(RelOp.NE, 5, 6));
        assertEquals(new BoolValue(false), eval(RelOp.NE, 5, 5));
    }

    @Test
    void greaterThanTrueAndFalse() throws Exception {
        assertEquals(new BoolValue(true), eval(RelOp.GT, 5, 3));
        assertEquals(new BoolValue(false), eval(RelOp.GT, 3, 5));
    }

    @Test
    void greaterOrEqualTrueAndFalse() throws Exception {
        assertEquals(new BoolValue(true), eval(RelOp.GE, 5, 5));
        assertEquals(new BoolValue(true), eval(RelOp.GE, 6, 5));
        assertEquals(new BoolValue(false), eval(RelOp.GE, 4, 5));
    }

    @Test
    void nonIntegerOperandThrows() {
        RelationalExp exp = new RelationalExp(RelOp.LT,
                new ValueExp(new StringValue("x")), new ValueExp(new IntValue(5)));
        assertThrows(ExpressionException.class, () -> exp.eval(state.getSymTable()));
    }
}