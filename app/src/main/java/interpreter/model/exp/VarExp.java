package interpreter.model.exp;

import interpreter.exceptions.MyException;
import interpreter.model.value.IValue;

public record VarExp(String var) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        return symTable[var];
    }
}
