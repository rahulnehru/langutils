package com.rnehru.langutils.validated;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ValidTest {

    @Test
    public void ofCreatesANewValidWithObject() {
        assertEquals(Valid.class, Valid.of("").getClass());
    }

    @Test
    public void getInternalReturnsValueInsideValid() {
        assertEquals("foo", Valid.of("foo").getInternal());
    }

}
