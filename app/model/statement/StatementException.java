package app.model.statement;

public class StatementException extends Exception {
    StatementException(String message) {
        super ("StatementException: " + message);
    }
}
