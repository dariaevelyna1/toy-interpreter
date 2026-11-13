package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.exp.ValueExp;
import interpreter.model.type.IntType;
import interpreter.model.type.StringType;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {

    @TempDir
    Path dir;

    private PrgState state;
    private ValueExp file; // the expression "<dir>/numbers.in"

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "7\n");
        file = new ValueExp(new StringValue(f.toString()));
        state = PrgState.of(new NopStmt());
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @Test
    void readGivesTheNextNumberThenZeroAtTheEnd() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(7), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void openingTheSameFileTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(file).execute(state));
    }

    @Test
    void openRFileWithNonStringExpressionThrows() {
        IExp notAString = new ValueExp(new IntValue(5));
        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(notAString).execute(state));
    }

    @Test
    void openRFileWithMissingFileThrows() {
        Path missing = dir.resolve("doesNotExist.in");
        IExp missingFile = new ValueExp(new StringValue(missing.toString()));
        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(missingFile).execute(state));
    }

    @Test
    void readFileWithUndeclaredVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "y").execute(state));
    }

    @Test
    void readFileWithNonIntVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new VarDeclStmt("s", new StringType()).execute(state);
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "s").execute(state)); // "s" is a string, not an int
    }

    @Test
    void readFileWhenFileNotOpenThrows() {
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(file, "x").execute(state)); // never opened
    }

    @Test
    void readFileWithNonIntegerLineThrows() throws Exception {
        Path badFile = dir.resolve("bad.in");
        Files.writeString(badFile, "notANumber\n");
        IExp badFileExp = new ValueExp(new StringValue(badFile.toString()));
        new OpenRFileStmt(badFileExp).execute(state);
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(badFileExp, "x").execute(state));
    }

    @Test
    void closeRFileRemovesTheFileFromFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new CloseRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().toMap().isEmpty());
    }

    @Test
    void closingAFileThatIsNotOpenThrows() {
        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(file).execute(state));
    }
}