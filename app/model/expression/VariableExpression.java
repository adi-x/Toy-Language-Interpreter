package app.model.expression;

import app.model.dictionary.IDictionary;
import app.model.type.IType;
import app.model.value.IValue;

public class VariableExpression implements IExpression {
    String variableId;
    public VariableExpression(String variableId) {
        this.variableId = variableId;
    }

    public String toString() {
        return variableId;
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IDictionary<Integer, IValue> heapTable) throws ExpressionException {
        if (symbolTable.keyExists(variableId)) {
            return symbolTable.atKey(variableId);
        }
        else throw new ExpressionException("The variable " + variableId + " does not exist!");
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws ExpressionException {
        return typeEnvironment.atKey(variableId);
    }
}
