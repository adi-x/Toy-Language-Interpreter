package app.model.expression;

import app.model.dictionary.IDictionary;
import app.model.type.IType;
import app.model.type.IntegerType;
import app.model.value.IValue;
import app.model.value.IntegerValue;

public class ArithmeticExpression implements IExpression {

    IExpression firstOperand;
    IExpression secondOperand;
    ArithmeticOperationType operation;

    public ArithmeticExpression(IExpression firstOperand, IExpression secondOperand, ArithmeticOperationType operation) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.operation = operation;
    }

    public String toString() {
        String middleSign = "";
        if (operation == ArithmeticOperationType.Addition)
            middleSign = "+";
        else if (operation == ArithmeticOperationType.Subtraction)
            middleSign = "-";
        else if (operation == ArithmeticOperationType.Multiplication)
            middleSign = "*";
        else if (operation == ArithmeticOperationType.Division)
            middleSign = "/";
        return firstOperand.toString() + " " + middleSign + " " + secondOperand.toString();
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IDictionary<Integer, IValue> heapTable) throws ExpressionException {
        IValue firstOperandValue = firstOperand.eval(symbolTable, heapTable);
        if (firstOperandValue.getType().equals(new IntegerType())) {
            IValue secondOperandValue = secondOperand.eval(symbolTable, heapTable);
            if (secondOperandValue.getType().equals(new IntegerType())) {
                int firstValue = ((IntegerValue) firstOperandValue).getValue();
                int secondValue = ((IntegerValue) secondOperandValue).getValue();
                if (operation == ArithmeticOperationType.Addition) {
                    return new IntegerValue(firstValue + secondValue);
                } else if (operation == ArithmeticOperationType.Subtraction) {
                    return new IntegerValue(firstValue - secondValue);
                } else if (operation == ArithmeticOperationType.Multiplication) {
                    return new IntegerValue(firstValue * secondValue);
                } else if (operation == ArithmeticOperationType.Division) {
                    if (secondValue != 0)
                        return new IntegerValue(firstValue / secondValue);
                    else throw new ExpressionException("Cannot evaluate arithmetic expression! Cannot divide by 0!");
                } else {
                    throw new ExpressionException("Cannot evaluate arithmetic expression! Invalid operation type.");
                }
            } else
                throw new ExpressionException("Cannot evaluate arithmetic expression! Second operand must be an integer!");
        } else
            throw new ExpressionException("Cannot evaluate arithmetic expression! First operand must be an integer!");
    }

    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws ExpressionException {
        IType firstOperandType, secondOperandType;
        firstOperandType = firstOperand.typeCheck(typeEnvironment);
        secondOperandType = secondOperand.typeCheck(typeEnvironment);
        if (firstOperandType.equals(new IntegerType())) {
            if (secondOperandType.equals(new IntegerType())) {
                return new IntegerType();
            } else {
                throw new ExpressionException("Cannot evaluate arithmetic expression! Second operand must be an integer!");
            }
        } else {
            throw new ExpressionException("Cannot evaluate arithmetic expression! First operand must be an integer!");
        }
    }
}
