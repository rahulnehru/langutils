package com.rnehru.langutils.match;

import org.junit.Test;

import static com.rnehru.langutils.match.StringMatch.*;
import static org.junit.Assert.assertEquals;

public class StringMatchTest {

    @Test
    public void matchMapReturnsValueWhenRegexMatches() {
        String s = "hello";

        String regex1 = ("[0-9]*");
        String regex2 = ("[a-z]*");
        String regex3 = ("[a-zA-Z]\\{0-1\\}");

        int v = match(s,
                map(regex1, t -> 1),
                map(regex2, t -> 2),
                map(regex3, t -> 3)
        );


        assertEquals(2, v);
    }

    @Test
    public void matchKaseActionsWhenRegexMatches() {
        String s = "hello";

        StringBuffer sb = new StringBuffer();
        String regex1 = ("[0-9]*");
        String regex2 = ("[a-z]*");
        String regex3 = ("[a-zA-Z]\\{0-1\\}");

        match(s,
                kase(regex1, t -> sb.append("a")),
                kase(regex2, t -> sb.append("b")),
                kase(regex3, t -> sb.append("c"))
        );


        assertEquals("b", sb.toString());
    }

    @Test(expected = MatchException.class)
    public void matchMapThrowsExceptionWhenNoRegexMatches() {
        String s = "hello";

        String regex1 = ("[0-9]*");

        match(s,
                map(regex1, t -> 1)
        );
    }
}
