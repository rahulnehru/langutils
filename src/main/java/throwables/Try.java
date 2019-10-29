package throwables;

import java.util.function.Function;

public interface Try<T> {

    static <T, R> Try apply(ThrowableFunction<T, R> o, T p) {
        try {
            return new Success<R>(o.accept(p));
        } catch (Throwable e) {
            return new Failure(e.getStackTrace(), e.getMessage());
        }
    }

    static <T, U, R> Try apply(ThrowableBiFunction<T, U, R> o, T t, U u) {
        try {
            return new Success<R>(o.accept(t, u));
        } catch (Throwable e) {
            return new Failure(e.getStackTrace(), e.getMessage());
        }
    }

    default public boolean isSuccess() {
        return this.getClass().equals(Success.class);
    }

    default public boolean isFailure() {
        return this.getClass().equals(Failure.class);
    }

    abstract <R> Try<R> map(Function<? super T, ? extends R> mapper);

}
