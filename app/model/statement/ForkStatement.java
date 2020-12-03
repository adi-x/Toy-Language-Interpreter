package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.dictionary.MyDictionary;
import app.model.program_state.ProgramState;
import app.model.stack.IStack;
import app.model.stack.MyStack;
import app.model.type.BooleanType;
import app.model.type.IType;
import app.model.value.IValue;
import app.utilities.DictionaryUtilities;

import java.util.Map;

public class ForkStatement implements IStatement{
    IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    public String toString() {
        return "fork(" + statement.toString() + ")";
    }

    public ProgramState execute(ProgramState state) {
        IStack<IStatement> executionStack = state.getExecutionStack();
        MyStack <IStatement> forkedExecutionStack = new MyStack<>();
        forkedExecutionStack.push(statement);
        MyDictionary <String, IValue> forkedSymbolTable = new MyDictionary<>();
        for (Map.Entry <String, IValue> mapElement : state.getSymbolTable().getRaw().entrySet()) {
            forkedSymbolTable.setKey(mapElement.getKey(), mapElement.getValue().clone());
        }
        return new ProgramState(forkedExecutionStack, forkedSymbolTable, state.getOutput(), state.getFileTable(), state.getHeapTable());
    }

    public IStatement clone() {
        return new ForkStatement(statement.clone());
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        statement.typeCheck(DictionaryUtilities.cloneTypeEnvironment(typeEnvironment));
        return typeEnvironment;
    }
}
