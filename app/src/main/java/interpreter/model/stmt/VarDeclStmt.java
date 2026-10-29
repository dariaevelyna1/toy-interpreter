package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.type.IType;
import interpreter.model.value.IValue;

public record VarDeclStmt(String name, IType type) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(state.getSymTable().isDefined(name)) {
            throw new StatementException("variable with this name already exists");
        }
        IValue val = type().defaultValue();
        state.getSymTable().put(name, val);
        return state;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }


}
