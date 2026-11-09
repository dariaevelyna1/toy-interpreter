//package interpreter.controller;
//
//import interpreter.exceptions.ExpressionException;
//import interpreter.exceptions.MyException;
//import interpreter.exceptions.StatementException;
//import interpreter.model.PrgState;
//import interpreter.model.exp.ArithExp;
//import interpreter.model.exp.ArithOp;
//import interpreter.model.exp.ValueExp;
//import interpreter.model.stmt.*;
//import interpreter.model.type.IntType;
//import interpreter.model.value.IntValue;
//import interpreter.repository.Repository;
//import interpreter.view.Examples;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ControllerTest {
//
//    // runs a whole program with display off and returns the final state
//    private static PrgState run(IStmt program) throws MyException {
//        PrgState  state = PrgState.of(program);
//        new Controller(new Repository(state), false).allStep();
//        return state;
//    }
//
//    @Test
//    void example1PrintsTwo() throws MyException {
//        PrgState state = run(Examples.ex1);
//        assertTrue(state.getExeStack().isEmpty());
//        assertEquals(1, state.getOut().size());
//        assertEquals(new IntValue(2), state.getOut().get(0));
//    }
//
//    @Test
//    void example2PrintsEighteen() throws MyException {
//        PrgState state = run(Examples.ex2);
//        assertTrue(state.getExeStack().isEmpty());
//        assertEquals(1, state.getOut().size());
//        assertEquals(new IntValue(18), state.getOut().get(0));
//    }
//
//    @Test
//    void example3PrintsTwo() throws MyException {
//        PrgState state = run(Examples.ex3);
//        assertTrue(state.getExeStack().isEmpty());
//        assertEquals(1, state.getOut().size());
//        assertEquals(new IntValue(2), state.getOut().get(0));
//    }
//
//    @Test
//    void oneStepOnEmptyStackThrows() throws MyException {
//        PrgState  state = PrgState.of(new NopStmt());
//        state.getExeStack().pop();   // now the stack is empty
//        Controller controller = new Controller(new Repository(state), false);
//        assertThrows(StatementException.class, () -> controller.oneStep(state));
//    }
//
//    @Test
//    void errorInProgramIsPassedUpFromAllStep() {
//        // int x; x = 1 / 0
//        IStmt program = new CompStmt(new VarDeclStmt("x", new IntType()),
//                new AssignStmt("x", new ArithExp(ArithOp.DIV,
//                        new ValueExp(new IntValue(1)), new ValueExp(new IntValue(0)))));
//        assertThrows(ExpressionException.class, () -> run(program));
//    }
//}