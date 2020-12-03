package app.model.list;

import app.model.adt_exception.ADTException;

public interface IList <TType> {
    void add(TType Element);
    void add(TType Element, int Index) throws ADTException;
    TType remove() throws ADTException;
    TType remove(int Index) throws ADTException;
    int size();
    TType at(int Index) throws ADTException;
}
