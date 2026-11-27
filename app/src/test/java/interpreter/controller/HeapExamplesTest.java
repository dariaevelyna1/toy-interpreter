package interpreter.controller;

import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.model.type.IntType;
import interpreter.model.type.RefType;
import interpreter.model.value.IntValue;
import interpreter.model.value.RefValue;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

// The A4 examples (Ex6-Ex10 of the lab = ex7-ex11 in Examples), run through the controller.
class HeapExamplesTest {

    @TempDir
    Path dir;

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        var repo = new Repository(state, dir.resolve("log.txt").toString());
        new Controller(repo, false).allStep();
        return state;
    }

    @Test
    void labEx6PrintsTheTwoReferences() throws MyException {
        PrgState state = run(Examples.ex7);
        assertEquals(Map.of(1, new IntValue(20), 2, new RefValue(1, new IntType())),
                state.getHeap().getContent());
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new RefValue(2, new RefType(new IntType())), state.getSymTable().lookup("a"));
        assertEquals(List.of(new RefValue(1, new IntType()), new RefValue(2, new RefType(new IntType()))),
                state.getOut().toList());
    }

    @Test
    void labEx7Prints20And25() throws MyException {
        PrgState state = run(Examples.ex8);
        assertEquals(List.of(new IntValue(20), new IntValue(25)), state.getOut().toList());
    }

    @Test
    void labEx8Prints20And35() throws MyException {
        PrgState state = run(Examples.ex9);
        assertEquals(Map.of(1, new IntValue(30)), state.getHeap().getContent());
        assertEquals(List.of(new IntValue(20), new IntValue(35)), state.getOut().toList());
    }

    @Test
    void labEx9Prints20WithTheSafeCollector() throws MyException {
        PrgState state = run(Examples.ex10);
        assertEquals(List.of(new IntValue(20)), state.getOut().toList());
    }

    @Test
    void labEx10Prints43210() throws MyException {
        PrgState state = run(Examples.ex11);
        assertEquals(List.of(new IntValue(4), new IntValue(3), new IntValue(2),
                new IntValue(1), new IntValue(0)), state.getOut().toList());
    }

    @Test
    void logFileContainsTheHeapAfterTheFileTable() throws Exception {
        run(Examples.ex7);
        String log = Files.readString(dir.resolve("log.txt"));
        assertTrue(log.indexOf("FileTable:") < log.indexOf("Heap:"));
        assertTrue(log.contains("1 --> 20"));
    }
}