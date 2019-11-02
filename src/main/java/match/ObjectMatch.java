package match;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ObjectMatch {

    public static <T> void match(T o, Consumer... a) {
        for (Consumer consumer : a) {
            consumer.accept(o);
        }
    }

    public static <T> Consumer kase(Class<T> klass, Consumer<T> c) {
        return t -> Optional.of(t).filter(klass::isInstance).map(klass::cast).ifPresent(c);
    }

    public static <T> Consumer kase(Class<T> klass, Predicate<T> p, Consumer<T> c) {
        return t -> Optional.of(t).filter(klass::isInstance).map(klass::cast).filter(p).ifPresent(c);
    }


    public static <T,R> R match(T s, Function... collectors) {
        for (Function collector: collectors) {
            Optional<R> r = (Optional<R>) collector.apply(s);
            if(r.isPresent()) return r.get();
        }
        throw new MatchException("Could not match type of " + s.getClass());
    }


    public static <T, R> Function<T, Optional<R>> map(Class<T> klass, Function<T, R> c) {
        return t -> Optional.of(t).filter(klass::isInstance).map(klass::cast).map(c);
    }

    public static <T, R> Function<T, Optional<R>> map(Class<T> klass, Predicate<T> p, Function<T, R> c) {
        return t -> Optional.of(t).filter(klass::isInstance).map(klass::cast).filter(p).map(c);
    }


}
