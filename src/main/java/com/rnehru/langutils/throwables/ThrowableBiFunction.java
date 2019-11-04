package com.rnehru.langutils.throwables;

/** ThrowableBiFunction is similar to the {@code BiFunction<T,R>} interface
 * The purpose of this functional interface is to accept that the function being passed in can throw an exception
 * and for it to be handled by the caller.
 *
 * <pre>
 *     {@code ThrowableBiFunction<Integer, String, User> getByIdAndNameFromDb = (id, name) -> selectFromDb(id)
 *          .whereNameIs(name).map(User::new); }
 * </pre>
 *
 * @param <T> type on which the function will be called
 * @param <U> second type on which the function will be called
 * @param <R> type returned by the function in successful scenarios
 */
@FunctionalInterface
public interface ThrowableBiFunction<T, U, R> {

    /** Apply the function on object t
     * @param t object on which the function is being called
     * @param u second object on which the function is applied
     * @return the result of the function being applied
     * @throws Exception when the function fails and throws an exception
     */

    R accept(T t, U u) throws Exception;

    /** Applies the function, or applies another function on object t to recover
     * @param t object on which the ThrowableFunction and recoveryFunction should be applied
     * @param u second object on which the function is applied
     * @param recoveryFunction function to be used to recover in case if this ThrowableFunction indeed throws
     * @return value of successful application of the original, or recoveryFunction
     * @throws Exception when the function and recover function both faile
     */

    default R acceptOrElse(T t, U u, ThrowableBiFunction<T, U, R > recoveryFunction) throws Exception {
        try {
            return accept(t, u);
        } catch (Exception e) {
            return recoveryFunction.accept(t, u);
        }
    }

    /** Applies the function, or returns a default value when the function throws an exception.
     * @param t object on which the function is applied
     * @param u second object on which the function is applied
     * @param r recovery default value to be returned if the function throws
     * @return either the value of applying the function on t, or the default value r
     */

    default R acceptOrDefault(T t, U u, R r) {
        try {
            return accept(t, u);
        } catch (Exception e) {
            return r;
        }
    }

}
