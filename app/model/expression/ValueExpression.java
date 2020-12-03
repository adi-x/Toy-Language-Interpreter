package app.model.expression;

import app.model.dictionary.IDictionary;
import app.model.type.IType;
import app.model.value.IValue;

public class ValueExpression implements IExpression {
    IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    public String toString() {
        return value.toString();
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IDictionary<Integer, IValue> heapTable) {
        return value;
    }

    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws ExpressionException {
        return value.getType();
    }


}
