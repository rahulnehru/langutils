package com.rnehru.langutils.throwables;

@FunctionalInterface
public interface ThrowableConsumer<T> {

    void apply(T t) throws Exception;

    default void applyOrElse(T t, ThrowableConsumer<T> recoveryConsumer) throws Exception {
        try {
           apply(t);
        } catch (Exception e) {
            recoveryConsumer.apply(t);
        }
    }

}
