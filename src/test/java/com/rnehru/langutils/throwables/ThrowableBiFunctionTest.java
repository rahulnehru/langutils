package com.rnehru.langutils.throwables;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ThrowableBiFunctionTest {
    
    @Test
    public void acceptDoesNotThrowExceptionWhenFunctionReturns() throws Exception {

        ThrowableBiFunction<String, String, Boolean> someFunction = (o, v) -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        assertTrue(someFunction.accept("hello", "bar"));
    }

    @Test(expected = Exception.class)
    public void acceptThrowsExceptionWhenFunctionThrows() throws Exception {

        ThrowableBiFunction<String, String, Boolean> someFunction = (o, v) -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        someFunction.accept("foo", "bar");
    }

    @Test
    public void acceptOrElseDoesNotThrowExceptionWhenFunctionThrowsButRecoveryReturns() throws Exception {

        ThrowableBiFunction<String, String, Boolean> someFunction = (o, v) -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        ThrowableBiFunction<String, String, Boolean> recovery = (o, v) -> {
            if (o.equals("foo")) {
                return true;
            } else throw new Exception();
        };

        assertTrue(someFunction.acceptOrElse("foo", "bar", recovery));
    }

    @Test(expected = Exception.class)
    public void acceptOrElseThrowsExceptionWhenFunctionThrowsAndRecoveryThrows() throws Exception {

        ThrowableBiFunction<String, String, Boolean> someFunction = (o, v) -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        ThrowableBiFunction<String, String, Boolean> recovery = (o, v) -> {
            if (o.equals("foo")) {
                return true;
            } else throw new Exception();
        };

        someFunction.acceptOrElse("bar", "bar", recovery);
    }

    @Test
    public void acceptOrDefaultReturnsWhenFunctionReturns() {

        ThrowableBiFunction<String, String, Boolean> someFunction = (o, v) -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        Boolean recovery = false;

        assertTrue(someFunction.acceptOrDefault("hello", "bar", recovery));
    }

    @Test
    public void acceptOrDefaultReturnsDefaultWhenFunctionThrows() {

        ThrowableBiFunction<String, String, Boolean> someFunction = (o, v) -> {
            if (o.equals("hello")) {
                return false;
            } else throw new Exception();
        };

        Boolean recovery = true;

        assertTrue(someFunction.acceptOrDefault("foo", "bar", recovery));
    }

}
