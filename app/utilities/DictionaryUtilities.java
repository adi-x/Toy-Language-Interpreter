package app.utilities;

import app.model.dictionary.IDictionary;
import app.model.dictionary.MyDictionary;
import app.model.type.IType;

import java.util.Map;

public class DictionaryUtilities {
    public static MyDictionary<String, IType> cloneTypeEnvironment(IDictionary<String, IType> typeEnvironment) {
        MyDictionary<String, IType> clonedEnvironment = new MyDictionary<>();
        Map<String, IType> rawEnvironment = typeEnvironment.getRaw();
        for (Map.Entry <String, IType> mapElement : rawEnvironment.entrySet()) {
            String key =  mapElement.getKey();
            IType value = mapElement.getValue();
            clonedEnvironment.setKey(key, value.clone());
        }
        return clonedEnvironment;
    }
}
