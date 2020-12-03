package app.model.expression;

import app.model.dictionary.IDictionary;
import app.model.type.BooleanType;
import app.model.type.IType;
import app.model.type.IntegerType;
import app.model.value.BooleanValue;
import app.model.value.IValue;
import app.model.value.IntegerValue;

public class RelationalExpression implements IExpression {
    IExpression firstOperand;
    IExpression secondOperand;
    RelationalOperationType operation;

    public RelationalExpression(IExpression firstOperand, IExpression secondOperand, RelationalOperationType operation) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.operation = operation;
    }

    public String toString() {
        String middleSign = "";
        switch (operation)
        {
            case Smaller:
                middleSign = "<";
                break;
            case SmallerOrEqual:
                middleSign = "<=";
                break;
            case Equal:
                middleSign = "==";
                break;
            case NotEqual:
                middleSign = "!=";
                break;
            case Bigger:
                middleSign = ">";
                break;
            case BiggerOrEqual:
                middleSign = ">=";
                break;
        }
        return firstOperand.toString() + " " + middleSign + " " + secondOperand.toString();
    }

    public IValue eval(IDictionary<String, IValue> symbolTable, IDictionary<Integer, IValue> heapTable) throws ExpressionException {
        IValue firstOperandValue = firstOperand.eval(symbolTable, heapTable);
        if (firstOperandValue.getType().equals(new IntegerType())) {
            IValue secondOperandValue = secondOperand.eval(symbolTable, heapTable);
            if (secondOperandValue.getType().equals(new IntegerType())) {
                int firstValue = ((IntegerValue)firstOperandValue).getValue();
                int secondValue = ((IntegerValue)secondOperandValue).getValue();
                switch (operation)
                {
                    case Smaller:
                        return new BooleanValue(firstValue < secondValue);
                    case SmallerOrEqual:
                        return new BooleanValue(firstValue <= secondValue);
                    case Equal:
                        return new BooleanValue(firstValue == secondValue);
                    case NotEqual:
                        return new BooleanValue(firstValue != secondValue);
                    case Bigger:
                        return new BooleanValue(firstValue > secondValue);
                    case BiggerOrEqual:
                        return new BooleanValue(firstValue >= secondValue);
                }
            }
            else throw new ExpressionException("Cannot evaluate relational expression! Second operand must be an integer!");
        }
        else throw new ExpressionException("Cannot evaluate relational expression! First operand must be an integer!");
        return null;
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnvironment) throws ExpressionException {
        IType firstOperandType, secondOperandType;
        firstOperandType = firstOperand.typeCheck(typeEnvironment);
        secondOperandType = secondOperand.typeCheck(typeEnvironment);
        if (firstOperandType.equals(new IntegerType())) {
            if (secondOperandType.equals(new IntegerType())) {
                return new BooleanType();
            } else {
                throw new ExpressionException("Cannot evaluate relational expression! Second operand must be an integer!");
            }
        } else {
            throw new ExpressionException("Cannot evaluate relational expression! First operand must be an integer!");
        }
    }
}
