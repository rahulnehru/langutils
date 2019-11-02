package com.rnehru.langutils.throwables;

@FunctionalInterface
public interface ThrowableBiFunction<T, U, R> {

    R accept(T t, U u) throws Exception;

    default R acceptOrElse(T t, U u, ThrowableBiFunction<T, U, R > recoveryFunction) throws Exception {
        try {
            return accept(t, u);
        } catch (Exception e) {
            return recoveryFunction.accept(t, u);
        }
    }

    default R acceptOrDefault(T t, U u, R r) {
        try {
            return accept(t, u);
        } catch (Exception e) {
            return r;
        }
    }

}
