package app.model.stack;

import app.model.adt_exception.ADTException;

public interface IStack <TType> {
    TType pop() throws ADTException;
    void push(TType Element);
    TType peek() throws ADTException;
    boolean isEmpty();
}
