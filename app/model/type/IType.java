package app.model.type;

import app.model.value.IValue;

public interface IType {
    boolean equals(Object someObject);
    IValue defaultValue();
    IType clone();
}
