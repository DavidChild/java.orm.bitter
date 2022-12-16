package io.github.davidchild.bitter.exception;

@SuppressWarnings("serial")
public class DbException extends RuntimeException {

    public DbException(String msg) {
        super(msg);
    }

}
