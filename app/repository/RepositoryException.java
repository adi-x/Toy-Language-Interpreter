package app.repository;

public class RepositoryException extends Exception {
    RepositoryException(String message) {
        super ("RepositoryException: " + message);
    }
}
