package com.rnehru.langutils.validated;

public class Valid<T> implements Validated {

    private T t;

    private Valid(T t) {
        this.t = t;
    }

    public static <T> Valid of(T t) {
        return new Valid<>(t);
    }

    public T getInternal() {
        return t;
    }


}
