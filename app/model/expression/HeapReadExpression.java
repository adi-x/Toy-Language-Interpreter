package app.model.expression;

import app.model.dictionary.IDictionary;
import app.model.type.IType;
import app.model.type.IntegerType;
import app.model.type.ReferenceType;
import app.model.value.IValue;
import app.model.value.ReferenceValue;

public class HeapReadExpression implements IExpression {
    IExpression expression;

    public HeapReadExpression(IExpression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "*" + expression.toString();
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IDictionary<Integer, IValue> heapTable) throws ExpressionException {
        IValue value = expression.eval(symbolTable, heapTable);
        if (value.getType() instanceof ReferenceType) {
            ReferenceValue referenceValue = (ReferenceValue) value;
            Integer targetAddress = referenceValue.getAddress();
            if (heapTable.keyExists(targetAddress)) {
                return heapTable.atKey(targetAddress);
            } else throw new ExpressionException("Invalid heap address!");
        } else throw new ExpressionException(expression + "[1] The value is not a ReferenceValue");
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws ExpressionException {
        IType expressionType;
        expressionType = expression.typeCheck(typeEnvironment);
        if (expressionType instanceof ReferenceType) {
            return ((ReferenceType) expressionType).getReferencedType();
        } else {
            throw new ExpressionException("Cannot read from the heap! The expression does not evaluate to a ReferenceType value!");
        }
    }


}
