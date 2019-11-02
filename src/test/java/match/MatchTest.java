package match;

import org.junit.Test;

import static match.Match.*;
import static org.junit.Assert.assertEquals;

public class MatchTest {


    @Test
    public void matchCaze() {
        StringBuffer sb = new StringBuffer();
        match("s",
                kase(String.class, (p) -> p.contains("t"), (t) -> sb.append("I'm a string with letter t")),
                kase(String.class, (p) -> p.contains("s"), (t) -> sb.append("I'm a string with letter s")),
                kase(Integer.class, (t) -> sb.append("I'm an int")),
                kase(Double.class, (t) -> sb.append("I'm a double"))
        );
        assertEquals("I'm a string with letter s", sb.toString());
    }

    @Test
    public void matchMap() {
        int i = match(2,
                        map(String.class, (t) -> 1),
                        map(Double.class, (p) -> p < 1, (t) -> 2),
                        map(Double.class, (t) -> 2),
                        map(Integer.class, (p) -> p > 4, (t) -> 6),
                        map(Integer.class, (t) -> 3)
                );

        assertEquals(3, i);
    }


}
