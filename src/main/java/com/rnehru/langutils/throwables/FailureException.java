package com.rnehru.langutils.throwables;

public class FailureException extends RuntimeException {

    public FailureException(String cause) {
        super(cause);
    }

}
