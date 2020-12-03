package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.program_state.ProgramState;
import app.model.type.IType;

public interface IStatement {
    ProgramState execute(ProgramState state) throws Exception;
    IStatement clone();
    IDictionary <String, IType> typeCheck(IDictionary <String, IType> typeEnvironment) throws Exception;
}
