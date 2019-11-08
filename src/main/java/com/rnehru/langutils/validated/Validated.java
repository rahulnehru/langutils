package com.rnehru.langutils.validated;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Validated provides a facade for performing concise and reusable validation of objects.
 * There are two implementations of Validated - the Valid and Invalid classes.
 *
 * Consider the following code:
 * <pre>
 * {@code
 *      class User {
 *          private String name;
 *          private int age;
 *          public User(String name, int age) {
 *              this.name = name;
 *              this.age = age;
 *          }
 *      }
 *
 *      Predicate<User> isAdult = u -> u.age > 18;
 *      Predicate<User> isCalledTom = u -> u.name.equals("Tom");
 *
 *      combine(
 *          validate(isAdult, "validation failed, user is not an adult"),
 *          validate(isCalledTom, "validation failed, user is called Tom")
 *      ).row(new User("Tom", 15));
 * }
 * </pre>
 *
 *
 */
public interface Validated {

    /** validate returns a function which applies a predicate condition to an object, and returns either a Valid or
     * Invalid when predicates are met.
     *
     * @param p predicate function operating on the applied object
     * @param e validation failed object to encapsulate in the Invalid
     * @param <T> type of object on which the predicate is being applied
     * @param <E> type of the validation failed object to be encapsulated in the Invalid
     * @return returns a function that can be applied with a an object of type T
     */

    static <T, E> Function<T, Validated> validate(Predicate<T> p, E e) {
        return t -> p.test(t) ? Valid.of(t) : Invalid.of(e);
    }

    @SafeVarargs
    static <T> Function<T, Validated> combine(Function<T, Validated>... vs) throws ValidationProjectionError {
        return t -> {
            List<Invalid> invalids = new ArrayList<>();
            for (Function<T, Validated> v : vs) {
                Validated vt = v.apply(t);
                if (!vt.isValid()) {
                    invalids.add(vt.projectInvalid());
                }
            }
            return invalids.isEmpty() ? Valid.of(t) : Invalid.of(invalids);
        };
    }

    default boolean isValid() {
        return this.getClass().equals(Valid.class);
    }

    default <T> Valid<T> projectValid() throws ValidationProjectionError {
        if(isValid())
            return (Valid<T>) this;
        else throw new ValidationProjectionError("Cannot project invalid condition to a valid");
    }

    default Invalid projectInvalid() throws ValidationProjectionError {
        if(!isValid())
            return (Invalid) this;
        else throw new ValidationProjectionError("Cannot project valid condition to an invalid");
    }

}
