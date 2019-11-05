package com.rnehru.langutils.validated;

import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;

import static com.rnehru.langutils.validated.Validated.combine;
import static com.rnehru.langutils.validated.Validated.validate;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ValidatedTest {

    @Test
    public void validateReturnsValidWhenValidationPasses() throws ValidationProjectionError {
        Predicate<Integer> predicate = i -> i > 3;

        Validated i = validate(predicate, "Help").apply(4);

        assertTrue(i.isValid());
        assertEquals(4, i.projectValid().getInternal());
    }

    @Test
    public void validateReturnsInvalidWhenValidationFails() throws ValidationProjectionError {
        Predicate<Integer> predicate = i -> i > 3;

        Validated i = validate(predicate, "Help").apply(2);

        assertFalse(i.isValid());
        assertEquals("Help", i.projectInvalid().getInternal());
    }

    @Test(expected = ValidationProjectionError.class)
    public void projectValidThrowsValidationProjectionExceptionWhenInvalid() {
        Predicate<Integer> predicate = i -> i > 3;

        Validated i = validate(predicate, "Help").apply(2);

        i.projectValid().getInternal();
    }

    @Test(expected = ValidationProjectionError.class)
    public void projectInvalidThrowsValidationProjectionExceptionWhenInvalid() {
        Predicate<Integer> predicate = i -> i > 1;

        Validated i = validate(predicate, "Help").apply(2);

        i.projectInvalid().getInternal();
    }

    @Test
    public void testCombinedValidationsGetAggregated() throws ValidationProjectionError {
        Predicate<User> userIsWorkingAdult = u -> u.age > 16;
        Predicate<User> userIsNotPensionable = u -> u.age < 65;
        Predicate<User> userIsJohn = u -> u.name.contains("John");

        Validated validatedJohn = combine(
                validate(userIsJohn, "User is not John"),
                validate(userIsWorkingAdult, "User is not over 18"),
                validate(userIsNotPensionable, "User is not under 65")
        ).apply(new User("John", 34));

        Validated validatedTim = combine(
                validate(userIsJohn, "User is not John"),
                validate(userIsWorkingAdult, "User is not over 18"),
                validate(userIsNotPensionable, "User is not under 65")
        ).apply(new User("Tim", 67));


        assertTrue(validatedJohn.isValid());
        assertFalse(validatedTim.isValid());

        List<Invalid> reasons = (List<Invalid>) validatedTim.projectInvalid().getInternal();
        assertEquals(2, reasons.size());
        assertEquals("User is not John", reasons.get(0).getInternal());
        assertEquals("User is not under 65", reasons.get(1).getInternal());
    }

    private class User {
        private final int age;
        private final String name;

        User(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
