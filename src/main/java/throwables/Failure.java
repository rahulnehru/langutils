package throwables;

import java.util.function.Function;

public class Failure implements Try {

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

    public StackTraceElement[] getStackTraceElement() {
        return stackTraceElement;
    }

    public String getCause() {
        return cause;
    }

    @Override
    public Object get() {
        throw new FailureException(cause);
    }
}
