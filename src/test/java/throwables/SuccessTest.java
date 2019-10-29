package throwables;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SuccessTest {

    private ThrowableFunction<String, String> f = s -> "bar";
    private Success<String> success = (Success<String>) Try.apply(f, "");

    @Test
    public void getReturnsSuccessValue() {
        assertEquals("bar" ,success.get());
    }

    @Test
    public void ofReturnsSuccessContainer() {
        Success<String> success = Success.of("bat");
        assertEquals("bat" ,success.get());
    }
}
