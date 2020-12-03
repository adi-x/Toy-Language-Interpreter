package app.model.dictionary;

import app.model.adt_exception.ADTException;
import app.model.value.IValue;
import app.utilities.MyStringUtilities;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary <KeyType, ValueType> implements IDictionary <KeyType, ValueType> {
    HashMap<KeyType, ValueType> storage = new HashMap<>();
    public ValueType atKey(KeyType KeyElement) {
        return storage.get(KeyElement);
    }

    public ValueType removeKey(KeyType KeyElement) throws ADTException {
        if (keyExists(KeyElement))
            return storage.remove(KeyElement);
        else throw new ADTException("Could not remove key! DictionaryADT does not contain the key " + KeyElement + ".");
    }

    public void setKey(KeyType KeyElement, ValueType ValueElement) {
        storage.put(KeyElement, ValueElement);
    }

    public boolean keyExists(KeyType KeyElement) {
        return storage.get(KeyElement) != null;
    }

    public HashMap<KeyType, ValueType> getRaw() {
        return storage;
    }

    public String toString() {
        StringBuilder show = new StringBuilder("");
        for (Map.Entry <KeyType, ValueType> mapElement : storage.entrySet()) {
            String key = "" + mapElement.getKey();
            ValueType value = mapElement.getValue();
            show.append(key).append(" -> ").append(value).append("\n");
        }
        return MyStringUtilities.trimEnd(show.toString(), '\n');
    }
}
