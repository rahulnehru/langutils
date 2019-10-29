package throwables;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ThrowableFunctionTest {

    @Test
    public void acceptDoesNotThrowExceptionWhenFunctionReturns() throws Exception {

        ThrowableFunction<String, Boolean> someFunction = o -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        assertTrue(someFunction.accept("hello"));
    }

    @Test(expected = Exception.class)
    public void acceptThrowsExceptionWhenFunctionThrows() throws Exception {

        ThrowableFunction<String, Boolean> someFunction = o -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        someFunction.accept("foo");
    }

    @Test
    public void acceptOrElseDoesNotThrowExceptionWhenFunctionThrowsButRecoveryReturns() throws Exception {

        ThrowableFunction<String, Boolean> someFunction = o -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        ThrowableFunction<String, Boolean> recovery = o -> {
            if (o.equals("foo")) {
                return true;
            } else throw new Exception();
        };

        assertTrue(someFunction.acceptOrElse("foo", recovery));
    }

    @Test(expected = Exception.class)
    public void acceptOrElseThrowsExceptionWhenFunctionThrowsAndRecoveryThrows() throws Exception {

        ThrowableFunction<String, Boolean> someFunction = o -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        ThrowableFunction<String, Boolean> recovery = o -> {
            if (o.equals("foo")) {
                return true;
            } else throw new Exception();
        };

        someFunction.acceptOrElse("bar", recovery);
    }

    @Test
    public void acceptOrDefaultReturnsWhenFunctionReturns() {

        ThrowableFunction<String, Boolean> someFunction = o -> {
            if (o.equals("hello")) {
                return true;
            } else throw new Exception();
        };

        Boolean recovery = false;

        assertTrue(someFunction.acceptOrDefault("hello", recovery));
    }

    @Test
    public void acceptOrDefaultReturnsDefaultWhenFunctionThrows() {

        ThrowableFunction<String, Boolean> someFunction = o -> {
            if (o.equals("hello")) {
                return false;
            } else throw new Exception();
        };

        Boolean recovery = true;

        assertTrue(someFunction.acceptOrDefault("foo", recovery));
    }

}
