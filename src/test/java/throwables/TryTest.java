package throwables;

import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;

import static org.junit.Assert.*;

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

    @Test
    public void orGetReturnsFunctionReturnValueWhenSuccess() {
        ThrowableFunction<String, String> f = s -> "result";

        Try<String> tried = Try.apply(f, "foo");

        assertEquals("result", tried.orGet("default"));
    }

    @Test
    public void orGetReturnsDefaultValueWhenFunctionFails() {
        ThrowableFunction<String, String> f = s -> {throw new Exception("");};

        Try<String> tried = Try.apply(f, "foo");

        assertEquals("default", tried.orGet("default"));
    }

    @Test
    public void getOptionalReturnsOptionOfWhenSuccessful() {
        ThrowableFunction<String, String> f = s -> "result";

        Try<String> tried = Try.apply(f, "foo");

        assertEquals(Optional.of("result"), tried.getOption());
    }

    @Test
    public void getOptionalReturnsEmptyWhenFailure() {
        ThrowableFunction<String, String> f = s -> {throw new Exception("");};

        Try<String> tried = Try.apply(f, "foo");

        assertEquals(Optional.empty(), tried.getOption());
    }

}
