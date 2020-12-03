package app.model.value;

import app.model.type.BooleanType;
import app.model.type.IType;

public class BooleanValue implements IValue {
    boolean value;

    public BooleanValue() {
        this.value = false;
    }

    public BooleanValue(boolean actualValue) {
        this.value = actualValue;
    }

    public boolean getValue() {
        return this.value;
    }

    public String toString() {
        return Boolean.toString(this.value);
    }
    public IType getType() {
        return new BooleanType();
    }
    public boolean equals(Object someObject) {
        return someObject instanceof BooleanValue && ((BooleanValue) someObject).getValue() == value;
    }
    public IValue clone() {
        return new BooleanValue(value);
    }
}
