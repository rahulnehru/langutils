package com.rnehru.langutils.throwables;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ThrowableConsumerTest {

    @Test
    public void applyDoesNotThrowExceptionWhenConsumerCompletes() throws Exception {
        StringBuffer sb = new StringBuffer();
        ThrowableConsumer<String> someFunction = o -> {
            if (o.equals("hello")) {
                sb.append(o);
            } else throw new Exception();
        };
        someFunction.apply("hello");

        assertEquals("hello", sb.toString());
    }

    @Test(expected = Exception.class)
    public void acceptThrowsExceptionWhenFunctionThrows() throws Exception {
        ThrowableConsumer<String> someFunction = o -> {
            throw new Exception();
        };
        someFunction.apply("hello");
    }

    @Test
    public void applyOrElseDoesNotThrowExceptionWhenConsumerCompletes() throws Exception {
        StringBuffer sb = new StringBuffer();
        ThrowableConsumer<String> someFunction = sb::append;

        someFunction.applyOrElse("hello", s -> sb.append("recovered"));

        assertEquals("hello", sb.toString());
    }

    @Test
    public void applyOrElseDoesNotThrowExceptionWhenConsumerFailsButRecoveryCompletes() throws Exception {
        StringBuffer sb = new StringBuffer();
        ThrowableConsumer<String> someFunction = s -> {throw new Exception();};

        someFunction.applyOrElse("hello", s -> sb.append("recovered"));

        assertEquals("recovered", sb.toString());
    }

    @Test(expected = Exception.class)
    public void applyOrElseThrowsExceptionWhenConsumerFailsAndRecoveryFails() throws Exception {
        ThrowableConsumer<String> someFunction = s -> {throw new Exception();};

        someFunction.applyOrElse("hello", someFunction);
    }
}
