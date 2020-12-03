package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.program_state.ProgramState;
import app.model.stack.IStack;
import app.model.type.IType;

public class CompoundStatement implements IStatement{
    IStatement first;
    IStatement second;
    
    public CompoundStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    private static String recursivePrint(IStatement statement) {
        StringBuilder show = new StringBuilder();
        if (statement instanceof CompoundStatement) {
            CompoundStatement compound = (CompoundStatement) statement;
            show.append(recursivePrint(compound.getFirst()));
            show.append(recursivePrint(compound.getSecond()));
        } else {
            show.append(statement).append("\n");
        }
        return show.toString();
    }

    public String toString() {
        return recursivePrint(this);
    }

    public ProgramState execute(ProgramState state) {
        IStack<IStatement> executionStack = state.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        return null;
    }


    public IStatement getFirst() {
        return first;
    }

    public IStatement getSecond() {
        return second;
    }

    public IStatement clone() {
        return new CompoundStatement(first.clone(), second.clone());
    }

    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IDictionary <String, IType> firstTypeEnvironment = first.typeCheck(typeEnvironment);
        IDictionary <String, IType> secondTypeEnvironment = second.typeCheck(firstTypeEnvironment);
        return secondTypeEnvironment;
    }
}
