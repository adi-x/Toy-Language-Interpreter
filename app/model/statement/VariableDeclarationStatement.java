package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.program_state.ProgramState;
import app.model.type.IType;
import app.model.value.IValue;

public class VariableDeclarationStatement implements IStatement {
    String variableId;
    IType type;

    public VariableDeclarationStatement(String variableId, IType type) {
        this.variableId = variableId;
        this.type = type;
    }

    public String toString() {
        return type.toString() + " " + variableId + ";";
    }

    public ProgramState execute(ProgramState state) throws StatementException {
        IDictionary <String, IValue> symbolTable = state.getSymbolTable();
        if (!symbolTable.keyExists(variableId)) {
            IValue initialValue;
            initialValue = type.defaultValue();
            symbolTable.setKey(variableId, initialValue);
        }
        else throw new StatementException("The variable " + variableId + " has already been declared!");
        return null;
    }

    public IStatement clone() {
        return new VariableDeclarationStatement(variableId, type);
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        typeEnvironment.setKey(variableId, type);
        return typeEnvironment;
    }
}
