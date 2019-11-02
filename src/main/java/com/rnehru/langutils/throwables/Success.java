package com.rnehru.langutils.throwables;

import java.util.function.Function;

public class Success<T> implements Try<T> {

    private T o;

    Success(T o) {
        this.o = o;
    }

    public T get() {
        return o;
    }

    public static <R> com.rnehru.langutils.throwables.Success<R> of(R r) {
        return new com.rnehru.langutils.throwables.Success<>(r);
    }

    @Override
    public <R> Try<R> map(Function<? super T, ? extends R> mapper) {
        return new com.rnehru.langutils.throwables.Success<>(mapper.apply(o));
    }
}
