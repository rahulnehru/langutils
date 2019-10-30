package either;

import java.util.function.Function;

public class Left<L> implements Either {

    private L inner;

    public Left(L inner) {
        this.inner = inner;
    }

    @Override
    public Either map(Function mapper) {
        return new Left<>(mapper.apply(inner));
    }

    @Override
    public L value() {
        return inner;
    }
}
