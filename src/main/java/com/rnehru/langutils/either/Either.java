package com.rnehru.langutils.either;

import java.util.function.Function;

public interface Either<L, R> {

    default boolean isLeft() {
        return this.getClass().equals(Left.class);
    }

    default boolean isRight() {
        return this.getClass().equals(Right.class);
    }

    <T, A, B> Either<A, B> map(Function<T, A> mapper);

    <T> T value();

    default Either<R, L> swap() {
        return isLeft() ? new Right<L>(value()) : new Left<R>(value());
    }
}
