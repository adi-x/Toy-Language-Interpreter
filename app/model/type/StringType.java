package app.model.type;

import app.model.value.IValue;
import app.model.value.StringValue;

public class StringType implements IType {
    public boolean equals(Object someObject) {
        return someObject instanceof StringType;
    }

    @Override
    public IValue defaultValue() {
        return new StringValue("");
    }

    @Override
    public IType clone() {
        return new StringType();
    }

    public String toString() {
        return "String";
    }
}
