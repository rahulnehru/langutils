package throwables;

public class FailureException extends RuntimeException {

    public FailureException(String cause) {
        super(cause);
    }

}
