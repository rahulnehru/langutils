package com.rnehru.langutils.throwables;

import java.util.function.Function;

public final class Success<T> implements Try<T> {

    private T o;

    Success(T o) {
        this.o = o;
    }

    /** Static call to the constructor for a Success object
     * @param r item to be wrapped in the Success
     * @param <R> Type of object for r
     * @return new Success object containing member r
     */

    public static <R> Success<R> of(R r) {
        return new Success<>(r);
    }

    /** Maps the function inside the Success with the mapper function
     * @param mapper mapper function to transform object of type T to type R
     * @param <R> type of resulting value of applying the mapper function
     * @return a new Success object containing the result of the mapping
     */

    @Override
    public final <R> Try<R> map(Function<? super T, ? extends R> mapper) {
        return new Success<>(mapper.apply(o));
    }

    /** Returns internal value of success
     * @return object contained in success of type T
     */

    public final T get() {
        return o;
    }

}
