package io.github.davidchild.bitter.exception;

@SuppressWarnings("serial")
public class IdNullException extends RuntimeException {
    IdNullException() {}

    public IdNullException(String msg) {
        super(msg);
    }

    public IdNullException(String msg, Throwable cause) {
        super(msg, cause);
    }

    IdNullException(Throwable cause) {
        super(cause);
    }
}
