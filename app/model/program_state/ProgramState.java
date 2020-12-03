package app.model.program_state;

import app.model.dictionary.IDictionary;
import app.model.list.IList;
import app.model.stack.IStack;
import app.model.statement.IStatement;
import app.model.value.IValue;

import java.io.BufferedReader;

public class ProgramState {
    IStack <IStatement> executionStack;
    IDictionary <String, IValue> symbolTable;
    IList<IValue> output;
    IDictionary <String, BufferedReader> fileTable;
    IDictionary <Integer, IValue>  heapTable;
    IStatement originalProgram;
    int id;
    static int nextId = 1;

    public ProgramState(IStack <IStatement> executionStack, IDictionary <String, IValue> symbolTable, IList <IValue> output, IDictionary <String, BufferedReader> fileTable, IDictionary <Integer, IValue>  heapTable) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        id = getId();
    }

    public ProgramState(IStack <IStatement> executionStack, IDictionary <String, IValue> symbolTable, IList <IValue> output, IDictionary <String, BufferedReader> fileTable, IDictionary <Integer, IValue>  heapTable, IStatement program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.originalProgram = program.clone();
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        executionStack.push(program);
        id = getId();
    }

    public String toString() {
        return "StateId: " + id + "\n" +
                "Execution Stack: " + executionStack.toString() + "\n" +
                "Symbol Table: " + symbolTable.toString() + "\n" +
                "Output: " + output.toString() + "\n";
    }

    public void setExecutionStack(IStack <IStatement> executionStack) {
        this.executionStack = executionStack;
    }

    public void setSymbolTable(IDictionary <String, IValue> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void setFileTable(IDictionary <String, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setHeapTable(IDictionary <Integer, IValue> heapTable) {
        this.heapTable = heapTable;
    }

    public void setOutput(IList <IValue> output) {
        this.output = output;
    }

    public void setOriginalProgram(IStatement program) {
        this.originalProgram = program;
    }

    public IStack <IStatement> getExecutionStack() {
        return this.executionStack;
    }

    public IDictionary <String, IValue> getSymbolTable() {
        return this.symbolTable;
    }

    public IDictionary <String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public IDictionary <Integer, IValue> getHeapTable() {
        return this.heapTable;
    }

    public IList <IValue> getOutput() {
        return this.output;
    }

    public IStatement getOriginalProgram() {
        return this.originalProgram;
    }

    public boolean isNotFinished() {
        return !executionStack.isEmpty();
    }

    public ProgramState runOneStep() throws Exception{
        IStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    private static int getId() {
        nextId++;
        return nextId - 1;
    }

    public int getStateId() {
        return id;
    }
}
