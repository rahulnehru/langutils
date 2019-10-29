package throwables;

import java.util.function.Function;

public class Failure extends Try {

    private StackTraceElement[] stackTraceElement;
    private String cause;

    Failure(StackTraceElement[] s, String cause) {
        this.stackTraceElement = s;
        this.cause = cause;
    }

    @Override
    Try map(Function mapper) {
        return this;
    }

    public StackTraceElement[] getStackTraceElement() {
        return stackTraceElement;
    }

    public String getCause() {
        return cause;
    }
}
