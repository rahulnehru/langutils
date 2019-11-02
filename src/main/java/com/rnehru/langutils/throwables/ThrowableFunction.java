package com.rnehru.langutils.throwables;

@FunctionalInterface
public interface ThrowableFunction<T, R> {

    R accept(T t) throws Exception;

    default R acceptOrElse(T t, ThrowableFunction<T,R > recoveryFunction) throws Exception {
        try {
           return accept(t);
        } catch (Exception e) {
            return recoveryFunction.accept(t);
        }
    }

    default R acceptOrDefault(T t, R r) {
        try {
            return accept(t);
        } catch (Exception e) {
            return r;
        }
    }

}
