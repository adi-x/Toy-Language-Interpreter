package app.controller;

import app.model.dictionary.IDictionary;
import app.model.dictionary.IndexedDictionary;
import app.model.dictionary.MyDictionary;
import app.model.list.IList;
import app.model.list.MyList;
import app.model.program_state.ProgramState;
import app.model.stack.IStack;
import app.model.statement.IStatement;
import app.model.value.IValue;
import app.model.value.ReferenceValue;
import app.repository.IRepository;
import app.repository.Repository;
import app.repository.RepositoryException;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class Controller {
    IRepository repository;
    boolean display = true;
    ExecutorService executor;
    public Controller() {

    }
    static public List<ProgramState> removeCompletedPrograms(List<ProgramState> initialProgramList) {
        LinkedList<ProgramState> newProgramList = new LinkedList<>();
        for (ProgramState state : initialProgramList)
            if (state.isNotFinished())
                newProgramList.add(state);
        return newProgramList;
    }
    static private IDictionary<Integer, IValue> conservativeGarbageCollector(List<IDictionary<String, IValue>> symbolTables, IDictionary<Integer, IValue> heapTable){
        IDictionary <Integer, IValue> newHeapTable = new IndexedDictionary<>((IndexedDictionary<IValue>) heapTable);
        List<Integer> symbolTableValues = getAddressesFromAllTables(symbolTables, heapTable);
        for (Map.Entry <Integer, IValue> mapElement : heapTable.getRaw().entrySet()) {
            Integer key = mapElement.getKey();
            IValue value = mapElement.getValue();
            if (symbolTableValues.contains(key))
                newHeapTable.setKey(key, value);
        }
        return newHeapTable;
    }
    static private List<Integer> getAddressesFromAllTables(List<IDictionary<String, IValue>> symbolTables, IDictionary<Integer, IValue> heapTable) {
        List<Integer> addresses = new LinkedList<>();
        for (IDictionary <String, IValue> symbolTable : symbolTables) {
            addresses.addAll(getAddressesFromSymbolTableValues(symbolTable.getRaw().values()));
        }
        for (IValue value : heapTable.getRaw().values())
            if (value instanceof ReferenceValue)
                addresses.add(((ReferenceValue) value).getAddress());
        return addresses;
    }
    static private List<Integer> getAddressesFromSymbolTableValues(Collection<IValue> symbolTableValues) {
        List<Integer> addresses = new LinkedList<>();
        for (IValue value : symbolTableValues)
            if (value instanceof ReferenceValue)
                addresses.add(((ReferenceValue) value).getAddress());
        return addresses;
    }

    static private List<IDictionary<String, IValue>> collectSymbolTables(List<ProgramState> programStates) {
        List<IDictionary<String, IValue>> symbolTables = new LinkedList<>();
        for (ProgramState state : programStates)
            symbolTables.add(state.getSymbolTable());
        return symbolTables;
    }
    public Controller(IRepository repository) {
        this.repository = repository;
    }
    static private void setHeapTableEverywhere(List<ProgramState> programStates, IDictionary<Integer, IValue> newHeapTable) {
        for (ProgramState state : programStates)
            state.setHeapTable(newHeapTable);
    }
    public void run() throws Exception {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedPrograms(repository.getProgramStates());
        while (programStateList.size() > 0) {
            setHeapTableEverywhere(programStateList, conservativeGarbageCollector(collectSymbolTables(programStateList), programStateList.get(0).getHeapTable()));
            oneStepForAllStates(programStateList);
            programStateList = removeCompletedPrograms(repository.getProgramStates());
        }
        executor.shutdownNow();
        repository.setProgramStates(programStateList);
    }
    public void oneStepForAllStates(List<ProgramState> programStates) throws Exception {
        for (ProgramState state : programStates)
            displayCurrentState(state);
        List<Callable<ProgramState>> callList = new LinkedList<>();
        for (ProgramState state : programStates)
            callList.add(state::runOneStep);
        List<ProgramState> newProgramList = new LinkedList<>();
        for (Future<ProgramState> promise : executor.invokeAll(callList)) {
            try {
                ProgramState state = promise.get();
                if (state != null)
                    newProgramList.add(state);
            } catch (Exception error) {
                System.out.println("An error has occured! " + error.getMessage());
            }
        }
        programStates.addAll(newProgramList);
        for (ProgramState state : programStates)
            displayCurrentState(state);
        repository.setProgramStates(programStates);
    }
    public void setRepository(IRepository repository) {
        this.repository = repository;
    }

    public void setDisplay(boolean value) {
        display = value;
    }

    public void displayCurrentState(ProgramState state) throws Exception{
        if (display)
            repository.logProgramState(state);
            //System.out.println(state);
    }
}
