package app.model.adt_exception;

public class ADTException extends Exception {
    public ADTException(String message) {
        super ("ADTException: " + message);
    }
}
