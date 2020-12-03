package app.model.stack;

import app.model.adt_exception.ADTException;
import app.utilities.MyStringUtilities;

import java.util.Stack;

public class MyStack <TType> implements IStack <TType> {
    Stack <TType> storage = new Stack<>();
    public TType pop() throws ADTException {
        if (!storage.empty())
            return storage.pop();
        else throw new ADTException("Could not pop! ADTStack is empty!");
    }

    public void push(TType Element) {
        storage.push(Element);
    }

    public TType peek() throws ADTException {
        if (!storage.empty())
            return storage.peek();
        else throw new ADTException("Could not peek! ADTStack is empty!");
    }

    public String toString() {
        StringBuilder show = new StringBuilder();
        for(TType storedObject : storage)
            show.insert(0, storedObject.toString() + "\n");
        return MyStringUtilities.trimEnd(show.toString(), '\n');
    }

    public boolean isEmpty() {
        return storage.isEmpty();
    }
}
