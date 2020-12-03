package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.expression.IExpression;
import app.model.program_state.ProgramState;
import app.model.type.IType;
import app.model.type.IntegerType;
import app.model.type.StringType;
import app.model.value.IValue;
import app.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenReadFileStatement implements IStatement {
    IExpression expression;

    public OpenReadFileStatement(IExpression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "openReadingFile(" + expression.toString() + ");";
    }

    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, BufferedReader> fileTable = state.getFileTable();
        IDictionary<Integer, IValue> heapTable = state.getHeapTable();
        IValue evaluationResult = expression.eval(state.getSymbolTable(), heapTable);
        if (evaluationResult.getType().equals(new StringType())) {
            String path = ((StringValue)evaluationResult).getValue();
            if (!fileTable.keyExists(path)) {
                try {
                    FileReader file = new FileReader(path);
                    BufferedReader fileBuffer = new BufferedReader(file);
                    fileTable.setKey(path, fileBuffer);
                }
                catch(Exception e) {
                    throw new StatementException("Could not open file! " + e.getMessage());
                }
            }
            else {
                throw new StatementException("This file is already open!");
            }
        }
        else {
            throw new StatementException("The path to the file needs to be a string!");
        }
        return null;
    }

    public IStatement clone() {
        return new OpenReadFileStatement(expression);
    }

    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType expressionType = expression.typeCheck(typeEnvironment);
        if (expressionType.equals(new StringType())) {
            return typeEnvironment;
        } else {
            throw new StatementException("The path to the file needs to be a string!");
        }
    }
}
