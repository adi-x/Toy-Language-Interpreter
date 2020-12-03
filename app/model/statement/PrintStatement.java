package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.expression.ExpressionException;
import app.model.expression.IExpression;
import app.model.list.IList;
import app.model.program_state.ProgramState;
import app.model.type.IType;
import app.model.value.IValue;

public class PrintStatement implements IStatement {
    IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "print(" + expression.toString() + ");";
    }

    public ProgramState execute(ProgramState state) throws Exception {
        IList <IValue> output = state.getOutput();
        output.add(expression.eval(state.getSymbolTable(), state.getHeapTable()));
        return null;
    }

    public IStatement clone() {
        return new PrintStatement(expression);
    }

    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws ExpressionException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }
}
