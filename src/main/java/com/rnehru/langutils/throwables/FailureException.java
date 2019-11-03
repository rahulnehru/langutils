package com.rnehru.langutils.throwables;

public final class FailureException extends RuntimeException {

    public FailureException(String cause) {
        super(cause);
    }

}
