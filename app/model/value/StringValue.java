package app.model.value;

import app.model.type.IType;
import app.model.type.StringType;

public class StringValue implements IValue {
    String value;

    public StringValue() {
        this.value = "";
    }

    public StringValue(String actualValue) {
        this.value = actualValue;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return "\"" + this.value + "\"";
    }
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue clone() {
        return new StringValue(value);
    }

    public boolean equals(Object someObject) {
        return someObject instanceof StringValue && ((StringValue) someObject).getValue() == value;
    }
}
