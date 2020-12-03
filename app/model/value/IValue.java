package app.model.value;

import app.model.type.IType;

public interface IValue {
    boolean equals(Object someObject);
    IType getType();
    IValue clone();
}
