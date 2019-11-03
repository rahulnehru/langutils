package com.rnehru.langutils.throwables;

import java.util.function.Function;

public final class Success<T> implements Try<T> {

    private T o;

    Success(T o) {
        this.o = o;
    }

    public final T get() {
        return o;
    }

    public static <R> Success<R> of(R r) {
        return new Success<>(r);
    }

    @Override
    public final <R> Try<R> map(Function<? super T, ? extends R> mapper) {
        return new Success<>(mapper.apply(o));
    }
}
