package com.rnehru.langutils.validated;

public class Invalid<E> implements Validated {

    private E e;

    private Invalid(E e) {
        this.e = e;
    }

    public static <E> Invalid of(E e) {
        return new Invalid<>(e);
    }

    public E getInternal() {
        return e;
    }


}
