package app.model.type;

import app.model.value.IValue;
import app.model.value.ReferenceValue;

public class ReferenceType implements IType {
    IType referencedType;
    public ReferenceType(IType referencedType) {
        this.referencedType = referencedType;
    }
    public boolean equals(Object someObject) {
        if (someObject instanceof ReferenceType) {
            return referencedType.equals(((ReferenceType) someObject).getReferencedType());
        } else return false;
    }

    public IType getReferencedType() {
        return referencedType;
    }

    @Override
    public IValue defaultValue() {
        return new ReferenceValue(0, referencedType);
    }

    @Override
    public IType clone() {
        return new ReferenceType(referencedType.clone());
    }

    public String toString() {
        return "Reference " + referencedType.toString();
    }
}
