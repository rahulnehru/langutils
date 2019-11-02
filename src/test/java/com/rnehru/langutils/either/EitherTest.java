package com.rnehru.langutils.either;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class EitherTest {

    static Either<String, Boolean> either(boolean makeItGoLeft) {
        if(makeItGoLeft) {
            return new Left<String>("Hello");
        } else {
            return new Right<Boolean>(true);
        }
    }

    @Test
    public void isLeft() {
        assertTrue(either(true).isLeft());
        assertFalse(either(true).isRight());
    }

    @Test
    public void isRight() {
        assertFalse(either(false).isLeft());
        assertTrue(either(false).isRight());
    }

    @Test
    public void swap() {
        assertTrue(either(true).swap().isRight());
        assertTrue(either(false).swap().isLeft());
    }
}
