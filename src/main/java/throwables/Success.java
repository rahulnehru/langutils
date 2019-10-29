package throwables;

import java.util.function.Function;

public class Success<T> extends Try<T> {

    private T o;

    Success(T o) {
        this.o = o;
    }

    public T get() {
        return o;
    }

    public static <R> throwables.Success<R> of(R r) {
        return new throwables.Success<R>(r);
    }

    @Override
    <R> Try<R> map(Function<? super T, ? extends R> mapper) {
        return new throwables.Success<R>(mapper.apply(o));
    }
}
