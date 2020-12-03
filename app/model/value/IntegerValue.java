package app.model.value;

import app.model.type.IType;
import app.model.type.IntegerType;

public class IntegerValue implements IValue {
    int value;

    public IntegerValue() {
        this.value = 0;
    }

    public IntegerValue(int actualValue) {
        this.value = actualValue;
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return Integer.toString(value);
    }
    public IType getType() {
        return new IntegerType();
    }

    @Override
    public IValue clone() {
        return new IntegerValue(value);
    }

    public boolean equals(Object someObject) {
        return someObject instanceof IntegerValue && ((IntegerValue) someObject).getValue() == value;
    }
}
