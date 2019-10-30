package either;

import org.junit.Test;

import java.util.function.Function;

import static either.EitherTest.either;
import static org.junit.Assert.assertEquals;

public class LeftTest {

    @Test
    public void mapTakesItemInALeftAndTransformsIt() {
        Function<String, String> stringifyMyString = s -> "thisismapped";
        assertEquals("thisismapped", either(true).map(stringifyMyString).value());
    }
}
