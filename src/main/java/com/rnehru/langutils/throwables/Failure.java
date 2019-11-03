package com.rnehru.langutils.throwables;

import java.util.function.Function;

public final class Failure implements Try {

    private StackTraceElement[] stackTraceElement;
    private String cause;

    Failure(StackTraceElement[] s, String cause) {
        this.stackTraceElement = s;
        this.cause = cause;
    }

    @Override
    public Try map(Function mapper) {
        return this;
    }

    public final StackTraceElement[] getStackTraceElement() {
        return stackTraceElement;
    }

    public final String getCause() {
        return cause;
    }

    @Override
    public final Object get() {
        throw new FailureException(cause);
    }
}
