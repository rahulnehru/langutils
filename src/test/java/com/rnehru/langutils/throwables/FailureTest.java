package com.rnehru.langutils.throwables;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FailureTest {

    private ThrowableFunction<String, String> f = s -> {throw new Exception("foo");};
    private Failure failure = (Failure) Try.apply(f, "");

    @Test
    public void getStackTraceReturnsStackTrace() {
        assertNotNull(failure.getStackTraceElement());
    }

    @Test
    public void getCauseReturnsCause() {
        assertEquals("foo", failure.getCause());
    }

    @Test(expected = FailureException.class)
    public void getThrowsFailureException() {
        failure.get();
    }

}
