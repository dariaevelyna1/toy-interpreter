package interpreter.model.exp;

import interpreter.exceptions.ExpressionException;
import interpreter.exceptions.MyException;
import interpreter.model.adt.IDictionary;
import interpreter.model.value.IValue;

public record VarExp(String var) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if (!symTable.isDefined(var)) {
            throw new ExpressionException("variable " + var + " is not defined");
        }
        return symTable.lookup(var);
    }
    @Override
    public String toString() {
        return var;
    }
}
