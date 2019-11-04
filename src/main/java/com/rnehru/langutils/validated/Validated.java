package com.rnehru.langutils.validated;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Validated {

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
