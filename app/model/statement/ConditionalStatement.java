package app.model.statement;

import app.model.expression.IExpression;
import app.model.dictionary.IDictionary;
import app.model.program_state.ProgramState;
import app.model.stack.IStack;
import app.model.type.BooleanType;
import app.model.type.IType;
import app.model.value.BooleanValue;
import app.model.value.IValue;
import app.utilities.DictionaryUtilities;

public class ConditionalStatement implements IStatement {
    IExpression condition;
    IStatement main;
    IStatement alternative;

    public ConditionalStatement(IExpression condition, IStatement main, IStatement alternative) {
        this.condition = condition;
        this.main = main;
        this.alternative = alternative;
    }
    public String toString() {
        return "if (" + condition.toString() + ") then { " + main.toString() + " } else { " + alternative.toString() + " }";
    }

    public ProgramState execute(ProgramState state) throws Exception {
        IStack <IStatement> stack = state.getExecutionStack();
        IDictionary <String, IValue> symbolTable = state.getSymbolTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();
        IValue conditionValue = condition.eval(symbolTable, heapTable);
        if (conditionValue.getType().equals(new BooleanType())) {
            if (((BooleanValue) conditionValue).getValue()) {
                stack.push(main);
            } else {
                stack.push(alternative);
            }
        }
        else throw new StatementException("The condition does not evaluate to a boolean value!");
        return null;
    }

    public IStatement clone() {
        return new ConditionalStatement(condition, main.clone(), alternative.clone());
    }

    @Override
    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType conditionType = condition.typeCheck(typeEnvironment);
        if (conditionType.equals(new BooleanType())) {
            main.typeCheck(DictionaryUtilities.cloneTypeEnvironment(typeEnvironment));
            alternative.typeCheck(DictionaryUtilities.cloneTypeEnvironment(typeEnvironment));
            return typeEnvironment;
        } else {
            throw new StatementException("The condition does not evaluate to a boolean value!");
        }
    }
}
