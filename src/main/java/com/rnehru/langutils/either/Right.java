package com.rnehru.langutils.either;

import java.util.function.Function;

public final class Right<R> implements Either {

    private final R inner;
    
    /** Constructor for the Right implementation of Either
     * @param inner value to be captured in Right
     */

    public Right(R inner) {
        this.inner = inner;
    }
    
    /** Maps the Right value
     * @param mapper function used to map the left and right values
     * @return a Right containing the mapped value
     */
    
    @Override
    public final Either map(Function mapper) {
        return new Right<>(mapper.apply(inner));
    }

    /** Getter for the value inside the Right
     * @return the underlying member of type R
     */
    
    @Override
    public final R value() {
        return inner;
    }
}
