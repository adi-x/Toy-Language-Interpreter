package app.model.expression;

import app.model.dictionary.IDictionary;
import app.model.type.BooleanType;
import app.model.type.IType;
import app.model.type.IntegerType;
import app.model.value.BooleanValue;
import app.model.value.IValue;

public class LogicExpression implements IExpression {
    IExpression firstOperand;
    IExpression secondOperand;
    LogicOperationType operation;
    public LogicExpression(IExpression firstOperand, IExpression secondOperand, LogicOperationType operation) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.operation = operation;
    }

    public String toString() {
        String middleSign = "";
        if (operation == LogicOperationType.And)
            middleSign = "&&";
        else if (operation == LogicOperationType.Or)
            middleSign = "||";
        return firstOperand.toString() + " " + middleSign + " " + secondOperand.toString();
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IDictionary<Integer, IValue> heapTable) throws ExpressionException {
        IValue firstOperandValue = firstOperand.eval(symbolTable, heapTable);
        if (firstOperandValue.getType().equals(new BooleanType())) {
            IValue secondOperandValue = secondOperand.eval(symbolTable, heapTable);
            if (secondOperandValue.getType().equals(new BooleanType())) {
                boolean firstValue = ((BooleanValue) firstOperandValue).getValue();
                boolean secondValue = ((BooleanValue) secondOperandValue).getValue();
                if (operation == LogicOperationType.And) {
                    return new BooleanValue(firstValue && secondValue);
                } else if (operation == LogicOperationType.Or) {
                    return new BooleanValue(firstValue || secondValue);
                }
            }
            else throw new ExpressionException("Cannot evaluate logical expression! Second operand must be a boolean!");
        }
        else throw new ExpressionException("Cannot evaluate logical expression! First operand must be a boolean!");
        return null;
    }

    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws ExpressionException {
        IType firstOperandType, secondOperandType;
        firstOperandType = firstOperand.typeCheck(typeEnvironment);
        secondOperandType = secondOperand.typeCheck(typeEnvironment);
        if (firstOperandType.equals(new BooleanType())) {
            if (secondOperandType.equals(new BooleanType())) {
                return new BooleanType();
            } else {
                throw new ExpressionException("Cannot evaluate logical expression! Second operand must be a boolean!");
            }
        } else {
            throw new ExpressionException("Cannot evaluate logical expression! First operand must be a boolean!");
        }
    }
}
