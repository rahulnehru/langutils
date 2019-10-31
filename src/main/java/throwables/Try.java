package throwables;

import java.util.Optional;
import java.util.function.Function;

public interface Try<T> {

    static <T, R> Try apply(ThrowableFunction<T, R> o, T p) {
        try {
            return new Success<>(o.accept(p));
        } catch (Throwable e) {
            return new Failure(e.getStackTrace(), e.getMessage());
        }
    }

    static <T, U, R> Try apply(ThrowableBiFunction<T, U, R> o, T t, U u) {
        try {
            return new Success<>(o.accept(t, u));
        } catch (Throwable e) {
            return new Failure(e.getStackTrace(), e.getMessage());
        }
    }

    default boolean isSuccess() {
        return this.getClass().equals(Success.class);
    }

    default boolean isFailure() {
        return this.getClass().equals(Failure.class);
    }

    <R> Try<R> map(Function<? super T, ? extends R> mapper);

    T get() throws FailureException;

    default T orGet(T defaultValue) {
        return isFailure() ? defaultValue : this.get();
    }

    default Optional<T> getOption() {
        return isSuccess() ? Optional.of(get()) : Optional.empty();
    }

}
