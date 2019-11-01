package match;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Match<T> {

    static <T> void match(T o, Consumer... a) {
        for (Consumer consumer : a)
            consumer.accept(o);
    }

    static <T> Consumer caze(Class<T> cls, Consumer<T> c) {
        return obj -> Optional.of(obj).filter(cls::isInstance).map(cls::cast).ifPresent(c);
    }

    static <T> Consumer caze(Class<T> cls, Predicate<T> p, Consumer<T> c) {
        return obj -> Optional.of(obj).filter(cls::isInstance).map(cls::cast).filter(p).ifPresent(c);
    }


    static <T,R> R match(T s, Function... collectors) {
        for (Function collector: collectors) {
            Optional<R> r = (Optional<R>) collector.apply(s);
            if(r.isPresent()) return r.get();
        }
        throw new MatchException("Could not match type of " + s.getClass());
    }


    static <T, R> Function<T, Optional<R>> map(Class<T> cls, Function<T, R> c) {
        return obj -> Optional.of(obj).filter(cls::isInstance).map(cls::cast).map(c);
    }

    static <T, R> Function<T, Optional<R>> map(Class<T> cls, Predicate<T> p, Function<T, R> c) {
        return obj -> Optional.of(obj).filter(cls::isInstance).map(cls::cast).filter(p).map(c);
    }


}
