package com.rnehru.langutils.throwables;

import java.util.function.Function;

public final class Failure implements Try {

    private final StackTraceElement[] stackTraceElement;
    private final String cause;

    Failure(StackTraceElement[] s, String cause) {
        this.stackTraceElement = s;
        this.cause = cause;
    }

    /** Inherited method from the Try interface, the result of this is itself
     * @param mapper mapper function to be applied
     * @return same Failure with no modifications
     */
    @Override
    public Try map(Function mapper) {
        return this;
    }

    /** Returns the stackTraceElement of the exception that was thrown
     * @return stackTraceElements from the original exception
     */
    public final StackTraceElement[] getStackTraceElement() {
        return stackTraceElement;
    }

    /** Returns the cause of the exception that was thrown
     * @return String value of the cause of the exception
     */
    public final String getCause() {
        return cause;
    }

    /** Gets the value contained in the Failure, note this is an invalid operation
     * @return Throws FailureException
     */
    @Override
    public final Object get() {
        throw new FailureException(cause);
    }
}
