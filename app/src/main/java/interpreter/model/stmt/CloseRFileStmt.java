package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;



public record CloseRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!(exp.eval(state.getSymTable()) instanceof StringValue filename)) {
            throw new StatementException("the parameter is not a string");
        }
        if(state.getFileTable().isDefined(filename)) {
            throw new StatementException("the file is not opened");
        }
        BufferedReader reader = state.getFileTable().lookup(filename);
        try {
            reader.close();
        } catch (IOException e) {
            throw new StatementException(e.getMessage());
        }
        state.getFileTable().remove(filename);
        return state;
    }
}
