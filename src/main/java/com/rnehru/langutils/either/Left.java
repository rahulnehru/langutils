package com.rnehru.langutils.either;

import java.util.function.Function;

public final class Left<L> implements Either {

    private L inner;

    /** Constructor for the Left implementation of Either
     * @param inner value to be captured in Left
     */

    public Left(L inner) {
        this.inner = inner;
    }


    /** Maps the Left value
     * @param mapper function used to map the left and right values
     * @return a Left containing the mapped value
     */

    @Override
    public final Either map(Function mapper) {
        return new Left<>(mapper.apply(inner));
    }

    /** Getter for the value inside the Left
     * @return the underlying member of type L
     */

    @Override
    public final L value() {
        return inner;
    }
}
