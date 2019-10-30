package either;

import java.util.function.Function;

public class Right<R> implements Either {

    private R inner;

    public Right(R inner) {
        this.inner = inner;
    }

    @Override
    public Either map(Function mapper) {
        return new Right<>(mapper.apply(inner));
    }

    @Override
    public R value() {
        return inner;
    }
}
