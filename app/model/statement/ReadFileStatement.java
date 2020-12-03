package app.model.statement;

import app.model.dictionary.IDictionary;
import app.model.expression.IExpression;
import app.model.program_state.ProgramState;
import app.model.type.IType;
import app.model.type.IntegerType;
import app.model.type.ReferenceType;
import app.model.type.StringType;
import app.model.value.IValue;
import app.model.value.IntegerValue;
import app.model.value.StringValue;

import java.io.BufferedReader;

public class ReadFileStatement implements IStatement {
    IExpression expression;
    String variableId;

    public ReadFileStatement(IExpression expression, String variableId) {
        this.expression = expression;
        this.variableId = variableId;
    }

    public String toString() {
        return variableId + " = readFile(\"" + expression.toString() + "\");";
    }

    public ProgramState execute(ProgramState state) throws Exception {
        IDictionary<String, IValue> symbolTable = state.getSymbolTable();
        if (symbolTable.keyExists(variableId)) {
            if (symbolTable.atKey(variableId).getType().equals(new IntegerType())) {
                IDictionary<String, BufferedReader> fileTable = state.getFileTable();
                IValue evaluationResult = expression.eval(state.getSymbolTable(), state.getHeapTable());
                if (evaluationResult.getType().equals(new StringType())) {
                    String path = ((StringValue)evaluationResult).getValue();
                    if (fileTable.keyExists(path)) {
                        try {
                            BufferedReader fileBuffer = fileTable.atKey(path);
                            String line = fileBuffer.readLine();
                            int value = 0;
                            if (line != null) {
                                value = Integer.parseInt(line);
                            }
                            else
                            symbolTable.setKey(variableId, new IntegerValue(value));
                        }
                        catch(Exception e) {
                            throw new StatementException("Could not read correctly from file! " + e.getMessage());
                        }
                    }
                    else {
                        throw new StatementException("This file isn't open!");
                    }
                }
                else {
                    throw new StatementException("The path to the file needs to be a string!");
                }
            }
            else {
                throw new StatementException("The variable " + variableId + " must be an integer!");
            }
        }
        else {
            throw new StatementException("Variable " + variableId + " doesn't exist.");
        }
        return null;
    }

    public IStatement clone() {
        return new ReadFileStatement(expression, variableId);
    }

    public IDictionary<String, IType> typeCheck(IDictionary<String, IType> typeEnvironment) throws Exception {
        IType variableType = typeEnvironment.atKey(variableId);
        if (variableType.equals(new IntegerType())) {
            IType expressionType = expression.typeCheck(typeEnvironment);
            if (expressionType.equals(new StringType())) {
                return typeEnvironment;
            } else {
                throw new StatementException("The path to the file needs to be a string!");
            }
        } else {
            throw new StatementException("The variable " + variableId + " must be an integer!");
        }
    }
}
