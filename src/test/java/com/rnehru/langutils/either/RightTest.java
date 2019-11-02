package com.rnehru.langutils.either;

import org.junit.Test;

import java.util.function.Function;

import static com.rnehru.langutils.either.EitherTest.either;
import static org.junit.Assert.assertEquals;

public class RightTest {

    @Test
    public void mapTakesItemInARightAndTransformsIt() {
        Function<Boolean, String> stringifyMyBoolean = String::valueOf;
        assertEquals("true", either(false).map(stringifyMyBoolean).value());
    }

}
