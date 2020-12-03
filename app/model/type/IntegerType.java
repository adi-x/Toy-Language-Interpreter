package app.model.type;

import app.model.value.IValue;
import app.model.value.IntegerValue;

public class IntegerType implements IType {
    public boolean equals(Object someObject) {
        return someObject instanceof IntegerType;
    }

    @Override
    public IValue defaultValue() {
        return new IntegerValue(0);
    }

    @Override
    public IType clone() {
        return new IntegerType();
    }

    public String toString() {
        return "Integer";
    }
}
