package io.github.davidchild.bitter.exception;

public class RefException extends RuntimeException {
    RefException() {}

    public RefException(String msg) {
        super(msg);
    }

    public RefException(String msg, Throwable cause) {
        super(msg, cause);
    }

    RefException(Throwable cause) {
        super(cause);
    }
}
