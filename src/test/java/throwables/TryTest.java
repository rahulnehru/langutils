package throwables;

import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TryTest {

    @Test
    public void applyReturnsSuccessWhenFunctionDoesNotThrow() {
        ThrowableFunction<String, Boolean> f = s -> true;

        assertEquals(Success.class, Try.apply(f, "foo").getClass());
    }

    @Test
    public void applyReturnsSuccessWhenBiFunctionDoesNotThrow() {
        ThrowableBiFunction<String, String, Boolean> f = (s, k) -> true;

        assertEquals(Success.class, Try.apply(f, "do", "foo").getClass());
    }

    @Test
    public void applyReturnsFailureWhenFunctionThrows() {
        ThrowableFunction<String, Boolean> f = s -> {throw new Exception("");};

        assertEquals(Failure.class, Try.apply(f, "foo").getClass());
    }

    @Test
    public void applyReturnsFailureWhenBiFunctionThrows() {
        ThrowableBiFunction<String, String, Boolean> f = (s, k) -> {throw new Exception("");};

        assertEquals(Failure.class, Try.apply(f, "do", "foo").getClass());
    }

    @Test
    public void mapReturnsSuccessWhenFunctionMaps() {
        ThrowableFunction<String, Boolean> f = s -> true;
        Function<Boolean, Boolean> g = s -> true;

        Try<Boolean> tried = Try.apply(f, "foo").map(g);
        assertEquals(Success.class, tried.getClass());
    }

    @Test
    public void mapReturnsFailureWhenFunctionThrows() {
        ThrowableFunction<String, Boolean> f = s -> {throw new Exception("");};
        Function<Boolean, Boolean> g = s -> true;

        Try<Boolean> tried = Try.apply(f, "foo").map(g);
        assertEquals(Failure.class, tried.getClass());
    }

    @Test
    public void isSuccessReturnsTrueWhenSuccess() {
        ThrowableFunction<String, Boolean> f = s -> true;

        Try<Boolean> tried = Try.apply(f, "foo");

        assertTrue(tried.isSuccess());
    }

    @Test
    public void isSuccessReturnsFalseWhenFailure() {
        ThrowableFunction<String, Boolean> f = s -> {throw new Exception("");};

        Try<Boolean> tried = Try.apply(f, "foo");

        assertFalse(tried.isSuccess());
    }

    @Test
    public void isFailureReturnsTrueWhenThrows() {
        ThrowableFunction<String, Boolean> f = s -> {throw new Exception("");};

        Try<Boolean> tried = Try.apply(f, "foo");

        assertTrue(tried.isFailure());
    }

    @Test
    public void isFailureReturnsFalseWhenSuccess() {
        ThrowableFunction<String, Boolean> f = s -> true;

        Try<Boolean> tried = Try.apply(f, "foo");

        assertFalse(tried.isFailure());
    }

}
