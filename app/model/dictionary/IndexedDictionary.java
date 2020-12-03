package app.model.dictionary;

import app.model.adt_exception.ADTException;
import app.utilities.MyStringUtilities;

import java.util.HashMap;
import java.util.Map;

public class IndexedDictionary<ValueType> extends MyDictionary <Integer, ValueType> {
    Integer lastPosition = 1;
    public IndexedDictionary() {
        lastPosition = 1;
    }
    public IndexedDictionary(IndexedDictionary<ValueType> extended) {
        lastPosition = extended.lastPosition;
    }
    public int setKey(ValueType ValueElement) {
        super.setKey(lastPosition, ValueElement);
        lastPosition++;
        return lastPosition - 1;
    }
}
