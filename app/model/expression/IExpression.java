package app.model.expression;

import app.model.dictionary.IDictionary;
import app.model.type.IType;
import app.model.value.IValue;

public interface IExpression {
    IValue eval(IDictionary <String, IValue> symbolTable, IDictionary<Integer, IValue> heapTable) throws ExpressionException;
    IType typeCheck(IDictionary <String, IType> typeEnvironment) throws ExpressionException;
}
