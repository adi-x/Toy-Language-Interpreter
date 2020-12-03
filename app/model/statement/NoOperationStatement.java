package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.program_state.ProgramState;
import app.model.type.IType;

public class NoOperationStatement implements IStatement {
    public String toString() {
        return "";
    }

    public ProgramState execute(ProgramState state) {
        return null;
    }

    public IStatement clone() {
        return new NoOperationStatement();
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        return typeEnvironment;
    }
}
