package app.repository;

import app.model.dictionary.MyDictionary;
import app.model.list.MyList;
import app.model.program_state.ProgramState;
import app.model.stack.MyStack;
import app.model.statement.IStatement;
import app.model.value.IValue;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository {
    List <ProgramState> states;
    int currentStateIndex = 0;
    String logFilePath;

    public Repository(ProgramState initialProgramState, String logFilePath) {
        states = new LinkedList<>();
        states.add(initialProgramState);
        this.logFilePath = logFilePath;
    }

    public void logProgramState(ProgramState state) throws RepositoryException{
        try {
            FileWriter file = new FileWriter(logFilePath, true);
            BufferedWriter fileBuffer = new BufferedWriter(file);
            PrintWriter filePrinter = new PrintWriter(fileBuffer);
            //
            filePrinter.println("= Program State with id " + state.getStateId() + " =");
            filePrinter.println("= Execution Stack =");
            MyStack<IStatement> executionStack = (MyStack <IStatement>) state.getExecutionStack();
            filePrinter.println(executionStack);
            filePrinter.println("= Symbol Table =");
            MyDictionary <String, IValue> symbolTable = (MyDictionary <String, IValue>) state.getSymbolTable();
            filePrinter.println(symbolTable);
            filePrinter.println("= Output =");
            MyList <IValue> output = (MyList <IValue>) state.getOutput();
            filePrinter.println(output);
            filePrinter.println("= File Table =");
            MyDictionary <String, BufferedReader> fileTable = (MyDictionary <String, BufferedReader>) state.getFileTable();
            filePrinter.println(fileTable);
            filePrinter.println("= Heap Table =");
            MyDictionary <Integer, IValue> heapTable = (MyDictionary <Integer, IValue>) state.getHeapTable();
            filePrinter.println(heapTable);
            filePrinter.println("---------------------\n"); // 2 new lines.
            //
            filePrinter.close();
            fileBuffer.close();
            file.close();
        } catch(Exception Error) {
            throw new RepositoryException(Error.getMessage());
        }

    }

    @Override
    public List<ProgramState> getProgramStates() {
        return states;
    }

    @Override
    public void setProgramStates(List<ProgramState> newStates) {
        states = newStates;
    }
}
