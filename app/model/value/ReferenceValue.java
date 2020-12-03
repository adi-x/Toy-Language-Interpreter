package app.model.value;

import app.model.type.IType;
import app.model.type.ReferenceType;

public class ReferenceValue implements IValue {
    int heapAddress;
    IType locationType;

    public ReferenceValue(int heapAddress, IType locationType) {
        this.heapAddress = heapAddress;
        this.locationType = locationType;
    }

    public int getAddress() {
        return this.heapAddress;
    }

    public IType getLocationType() { return this.locationType; }
    public String toString() {
        return "(" + heapAddress + ", " + locationType+")";
    }

    public IType getType() {
        return new ReferenceType(locationType);
    }

    @Override
    public IValue clone() {
        return new ReferenceValue(heapAddress, locationType.clone());
    }

    public boolean equals(Object someObject) {
        return someObject instanceof ReferenceValue && ((ReferenceValue) someObject).getAddress() == heapAddress;
    }
}
