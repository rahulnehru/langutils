package com.rnehru.langutils.validated;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class InvalidTest {

    @Test
    public void ofCreatesANewInvalidWithObject() {
        assertEquals(Invalid.class, Invalid.of("").getClass());
    }

    @Test
    public void getInternalReturnsValueInsideInvalid() {
        assertEquals("foo", Invalid.of("foo").getInternal());
    }

}
