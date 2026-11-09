package interpreter.model;

import interpreter.model.adt.*;
import interpreter.model.stmt.IStmt;
import interpreter.model.value.IValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;


    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IDictionary<StringValue, BufferedReader> fileTable, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.fileTable = fileTable;
        this.originalProgram = program;
        exeStack.push(program);
    }


    public static PrgState of(IStmt program) {
        return new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(),
                new MyDictionary<>(), program);
    }

    public IStack<IStmt> getExeStack() {
        return this.exeStack;
    }

    public IDictionary<String, IValue> getSymTable(){
        return this.symTable;
    }

    public IList<IValue> getOut() {
        return this.out;
    }

    @Override
    public String toString() {
        return "ExeStack: " + exeStack + "\n"
                + "SymTable: " + symTable + "\n"
                + "FileTable " + fileTable + "\n"
                + "Out: " + out + "\n";
    }

    public String toLogString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ExeStack: ").append(exeStack.toString()).append("\n");
        sb.append("SymTable: ").append(symTable.toString()).append("\n");
        sb.append("Out: ").append(out.toString()).append("\n");
        sb.append("FileTable: ").append(fileTable.toString()).append("\n");
        return sb.toString();
    }
}
