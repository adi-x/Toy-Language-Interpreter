package app.model.expression;

public class ExpressionException extends Exception {
    ExpressionException(String message) {
        super ("ExpressionException: " + message);
    }
}
