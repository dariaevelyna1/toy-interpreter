package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.IntValue;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName)) {
            throw new StatementException(exp + "is not a string");
        }
        if (!(state.getSymTable().lookup(varName) instanceof IntValue)) {
            throw new StatementException(varName + "is not an integer type");
        }
        if (!state.getSymTable().isDefined(varName)) {
            throw new StatementException("the variable is not defined");
        }
        if (!state.getFileTable().isDefined(fileName)) {
            throw new StatementException("the file is not opened");
        }

        BufferedReader reader = state.getFileTable().lookup(fileName);
        try {
            String line = reader.readLine();
            int value;
            if (line == null) {
                value = 0;

            } else {
                value = Integer.parseInt(line.strip());
            }
            state.getSymTable().update(varName, new IntValue(value));
        } catch (IOException e) {
            throw new StatementException("readFile: cannot read " + fileName + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new StatementException("readFile: line is not an integer: " + e.getMessage());

        }
        return state;


    }
    @Override
    public String toString() {
        return "readFile(" + exp.toString() + "," + varName +")";
    }
}
