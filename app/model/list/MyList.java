package app.model.list;

import app.model.adt_exception.ADTException;
import app.utilities.MyStringUtilities;

import java.util.LinkedList;

public class MyList <TType> implements IList <TType> {
    LinkedList <TType> storage = new LinkedList<>();

    public void add(TType Element) {
        storage.add(Element);
    }

    public void add(TType Element, int Index) throws ADTException {
        if (Index >= 0 && Index <= storage.size())
            storage.add(Index, Element);
        else throw new ADTException("Could not add! ADTList index out of bounds!");
    }

    public TType remove() throws ADTException {
        if (!storage.isEmpty()) {
            return storage.remove();
        }
        else throw new ADTException("Could not remove! ADTList does not contain any elements!");
    }

    public TType remove(int Index) throws ADTException {
        int Size = size();
        if (Index >= 0 && Index < Size)
            return storage.remove(Index);
        else throw new ADTException("Could not remove! ADTList does not contain any elements!");
    }

    public int size() {
        return storage.size();
    }

    public TType at(int Index) throws ADTException {
        int Size = size();
        if (Index >= 0 && Index < Size)
            return storage.get(Index);
        else throw new ADTException("Could not retrieve! ADTList index out of bounds!");
    }

    public String toString() {
        StringBuilder show = new StringBuilder();
        for (TType storedObject : storage) {
            show.append(storedObject).append("\n");
        }
        return MyStringUtilities.trimEnd(show.toString(), '\n');
    }
}
