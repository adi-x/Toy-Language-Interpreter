package app.model.dictionary;

import app.model.adt_exception.ADTException;

import java.util.HashMap;

public interface IDictionary <KeyType, ValueType> {
    ValueType atKey(KeyType KeyElement);
    ValueType removeKey(KeyType KeyElement) throws ADTException;
    void setKey(KeyType KeyElement, ValueType ValueElement);
    boolean keyExists(KeyType KeyElement);
    HashMap <KeyType, ValueType> getRaw();
}
