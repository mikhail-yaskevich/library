package by.it.training.library.dao.connection;

public class ConnectionPoolException extends Exception {

    private static final long serialVersionUID = 7677664534157853317L;

    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
