package com.rnehru.langutils.either;

import java.util.function.Function;

/** Either can be used for controlling paths in your code where two return types, or consumers need to be returned.
 * It has two implementations, the Left (happy path), or Right (unhappy path) and can be used as such:
 *
 * <pre>
 *  {@code public Either<String, Integer> doSomething(int x) {
 *    return x > 0 ? new Left("success") : new Right(0);
 *    }
 *   }
 *
 * </pre>
 *
 * @param <L> type of Left
 * @param <R> type of Right
 */
public interface Either<L, R> {


    /**
     * Returns if Either is of type Left.class
     * @return true if Left
     */

    default boolean isLeft() {
        return this.getClass().equals(Left.class);
    }

    /**
     * Returns if Either is of type Right.class
     * @return true if Right
     */

    default boolean isRight() {
        return this.getClass().equals(Right.class);
    }


    /**
     * Interface method that maps the left and right values with a mapper function
     * @param mapper function used to map the left and right values
     * @param <T> type of value to be mapped, one of the original types in the Left or Right
     * @param <A> type of Left to be returned
     * @param <B> type of Right to be returnes
     * @return an either (Left or Right) containing the mapped value
     */

    <T, A, B> Either<A, B> map(Function<T, A> mapper);

    /**
     * Interface to obtain the value in the Left or Right
     * @param <T> type of value contained in the Left or Right
     * @return the value in the Either implementation
     */
    <T> T value();


    /** Swaps the value from the right to the left and vice versa
     * @return if the value was a Left containing type L, returns a Right containing type L and vice versa
     */
    default Either<R, L> swap() {
        return isLeft() ? new Right<L>(value()) : new Left<R>(value());
    }
}
