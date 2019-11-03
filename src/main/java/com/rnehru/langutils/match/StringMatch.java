package com.rnehru.langutils.match;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * String is used to provide Match-Kase and Match-Map functionality on String objects for regex matching
 *
 */
public class StringMatch {

    @SafeVarargs
    public static <T> T match(String s, Function<String, Optional<T>>... f) {
        for (Function<String, Optional<T>> fn : f) {
            Optional<T> opF = fn.apply(s);
            if(opF.isPresent()) {
                return opF.get();
            }
        }
        throw new MatchException("Could pattern match String  " + s);
    }

    @SafeVarargs
    public static void match(String s, Consumer<String>... f) {
        for (Consumer<String> fn : f)
            fn.accept(s);
    }

    public static Consumer<String> kase(String regex, Consumer<String> t) {
        return s -> { if(s.matches(regex)) t.accept(s); };
    }

    public static <T> Function<String, Optional<T>> map(String regex, Function<String, T> t) {
        return s -> Optional.of(s).filter(str -> str.matches(regex)).map(t);
    }



}
