package com.rnehru.langutils.match;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * ObjectMatch is used to provide Match-Kase and Match-Map functionality on the type of the object in question.
 *
 */

public final class ObjectMatch {

    /** match is used in conjunction with kase statements to provide means to produce a side effect (a consumer function)
     * based on the (undetermined) type of an object e.g.
     *
     * <pre>
     *     {@code match("s",
     *         kase(String.class, s -> {System.out.println(s)};),
     *         kase(Double.class, s -> {System.out.println(s)};),
     *         kase(Integer.class, s -> {System.out.println(s)};)
     *     )}
     *
     * </pre>
     *
     * @param o object to be matched
     * @param a consumer function (a kase statement)
     * @param <T> undetermined type of object o
     */

    public static <T> void match(T o, Consumer... a) {
        for (Consumer consumer : a) {
            consumer.accept(o);
        }
    }

    /**
     * Kase is a case to be matched and the consumer to be functioned if the object matches the kase
     * @param klass Class to be matched against for this kase
     * @param c consumer function
     * @param <T> type of klass
     * @return returns the c
     */

    public static <T> Consumer kase(Class<T> klass, Consumer<T> c) {
        return t -> Optional.of(t).filter(klass::isInstance).map(klass::cast).ifPresent(c);
    }

    /**
     * Kase is a case to be matched and the consumer to be functioned if the object matches the kase and a predicate
     * function is correct
     *
     * <pre>
     * {@code kase(String.class, p -> {p.contains("foo");}, t -> {System.out.println(s);})}
     * </pre>
     *
     * @param klass Class to be matched against for this kase
     * @param c consumer function
     * @param <T> type of klass
     * @param p predicate function
     * @return returns the c
     */

    public static <T> Consumer kase(Class<T> klass, Predicate<T> p, Consumer<T> c) {
        return t -> Optional.of(t).filter(klass::isInstance).map(klass::cast).filter(p).ifPresent(c);
    }


    /** match is used in conjunction with map statements to provide means to return a value (a function)
     * based on the (undetermined) type of an object
     *
     * <pre>
     * {@code
     *  match("s",
     *      map(String.class, s -> 1);
     *      map(Double.class, s -> 2);
     *      map(Boolean.class, s -> 3);
     *  );
     * }
     * </pre>
     *
     * @param s object to be matched
     * @param collectors map function
     * @param <T> undetermined type of object s
     * @param <R> type of object to be returned
     * @return value of type R based on the matched map statement
     */

    public static <T,R> R match(T s, Function... collectors) {
        for (Function collector: collectors) {
            Optional<R> r = (Optional<R>) collector.apply(s);
            if(r.isPresent()) return r.get();
        }
        throw new MatchException("Could not match type of " + s.getClass());
    }


    /** Function to be used in conjunction with match where the expected result is a return of type T
     * @param klass class to be matched against
     * @param c function to be applied to matched object
     * @param <T> type to match and map against
     * @param <R> resulting type
     * @return mapped value of type R
     */

    public static <T, R> Function<T, Optional<R>> map(Class<T> klass, Function<T, R> c) {
        return t -> Optional.of(t).filter(klass::isInstance).map(klass::cast).map(c);
    }

    /** Function to be used in conjunction with match where the expected result is a return of type T with a predicate
     *
     * <pre>
     *     {@code map(String.class, p -> p.contains("foo"), r -> 1)}
     * </pre>
     *
     * @param klass class to be matched against
     * @param c function to be applied to matched object
     * @param <T> type to match and map against
     * @param <R> resulting type
     * @param p predicate function that object of type T must satisfy
     * @return mapped value of type R
     */

    public static <T, R> Function<T, Optional<R>> map(Class<T> klass, Predicate<T> p, Function<T, R> c) {
        return t -> Optional.of(t).filter(klass::isInstance).map(klass::cast).filter(p).map(c);
    }


}
