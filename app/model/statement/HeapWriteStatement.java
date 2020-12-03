package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.expression.IExpression;
import app.model.program_state.ProgramState;
import app.model.type.IType;
import app.model.type.ReferenceType;
import app.model.value.IValue;
import app.model.value.ReferenceValue;

public class HeapWriteStatement implements IStatement {
    String variableId;
    IExpression expression;
    public HeapWriteStatement(String variableId, IExpression expression) {
        this.variableId = variableId;
        this.expression = expression;
    }

    public String toString() {
        return "*" + variableId + " = " + expression.toString() + ";";
    }
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary <String, IValue> symbolTable = state.getSymbolTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();
        if (symbolTable.keyExists(variableId)) {
            if (symbolTable.atKey(variableId).getType() instanceof ReferenceType) {
                IValue value = expression.eval(symbolTable, heapTable);
                ReferenceValue referenceValue = (ReferenceValue) symbolTable.atKey(variableId);
                if (value.getType().equals(referenceValue.getLocationType())) {
                    Integer targetAddress = ((ReferenceValue) symbolTable.atKey(variableId)).getAddress();
                    if (heapTable.keyExists(targetAddress)) {
                        heapTable.setKey(targetAddress, value);
                        symbolTable.setKey(variableId, new ReferenceValue(targetAddress, referenceValue.getLocationType()));
                    } else throw new StatementException("Invalid heap address!");
                }
                else throw new StatementException("The variable " + variableId + " does not reference the same type as the new value!");
            } else throw new StatementException("The variable " + variableId + " is not a reference!");

        } else throw new StatementException("The variable " + variableId + " does not exist!");
        return null;
    }

    public IStatement clone() {
        return new HeapWriteStatement(variableId, expression);
    }

    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType variableType = typeEnvironment.atKey(variableId);
        IType expressionType = expression.typeCheck(typeEnvironment);
        if (variableType.equals(new ReferenceType(expressionType))) {
            return typeEnvironment;
        } else {
            throw new StatementException("The variable " + variableId + " does not reference the same type as the new value!");
        }
    }
}
