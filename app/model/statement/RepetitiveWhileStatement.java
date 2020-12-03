package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.expression.IExpression;
import app.model.program_state.ProgramState;
import app.model.stack.IStack;
import app.model.type.BooleanType;
import app.model.type.IType;
import app.model.value.BooleanValue;
import app.model.value.IValue;
import app.utilities.DictionaryUtilities;

public class RepetitiveWhileStatement implements IStatement {
    IExpression condition;
    IStatement body;

    public RepetitiveWhileStatement(IExpression condition, IStatement body) {
        this.condition = condition;
        this.body = body;
    }
    public String toString() {
        return "while (" + condition.toString() + ") then { " + body.toString() + " }";
    }

    public ProgramState execute(ProgramState state) throws Exception {
        IStack <IStatement> stack = state.getExecutionStack();
        IDictionary <String, IValue> symbolTable = state.getSymbolTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();
        IValue conditionValue = condition.eval(symbolTable, heapTable);
        if (conditionValue.getType().equals(new BooleanType())) {
            if (((BooleanValue) conditionValue).getValue()) {
                stack.push(this);
                stack.push(body);
            }
        }
        else throw new StatementException("The condition does not evaluate to a boolean value!");
        return null;
    }

    public IStatement clone() {
        return new RepetitiveWhileStatement(condition, body.clone());
    }

    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType conditionType = condition.typeCheck(typeEnvironment);
        if (conditionType.equals(new BooleanType())) {
            body.typeCheck(DictionaryUtilities.cloneTypeEnvironment(typeEnvironment));
            return typeEnvironment;
        } else {
            throw new StatementException("The condition does not evaluate to a boolean value!");
        }
    }
}
