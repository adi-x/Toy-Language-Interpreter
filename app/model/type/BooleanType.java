package app.model.type;

import app.model.value.BooleanValue;
import app.model.value.IValue;

public class BooleanType implements IType {
    public boolean equals(Object someObject) {
        return someObject instanceof BooleanType;
    }

    @Override
    public IValue defaultValue() {
        return new BooleanValue(false);
    }

    @Override
    public IType clone() {
        return new BooleanType();
    }

    public String toString() {
        return "Boolean";
    }
}
