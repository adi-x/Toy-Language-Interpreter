package app.model.statement;

import app.model.expression.IExpression;
import app.model.dictionary.IDictionary;
import app.model.program_state.ProgramState;
import app.model.type.IType;
import app.model.value.IValue;

public class AssignmentStatement implements IStatement {
    String variableId;
    IExpression expression;
    public AssignmentStatement(String variableId, IExpression expression) {
        this.variableId = variableId;
        this.expression = expression;
    }

    public String toString() {
        return variableId + " = " + expression.toString() + ";";
    }
    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary <String, IValue> symbolTable = state.getSymbolTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();
        if (symbolTable.keyExists(variableId)) {
            IValue newValue = expression.eval(symbolTable, heapTable);
            IValue oldValue = symbolTable.atKey(variableId);
            if (oldValue.getType().equals(newValue.getType()))
                symbolTable.setKey(variableId, newValue);
            else throw new StatementException("The variable " + variableId + " does not have the same type as the new value!");
        } else throw new StatementException("The variable " + variableId + " does not exist!");
        return null;
    }

    public IStatement clone() {
        return new AssignmentStatement(variableId, expression);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType variableType = typeEnvironment.atKey(variableId);
        IType expressionType = expression.typeCheck(typeEnvironment);
        if (variableType.equals(expressionType)) {
            return typeEnvironment;
        } else {
            throw new StatementException("Could not assign values! The variable and the expression do not match.");
        }
    }
}
