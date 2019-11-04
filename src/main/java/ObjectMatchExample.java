import static com.rnehru.langutils.match.ObjectMatch.*;

class ObjectMatchExample {

    static StringBuffer sb = new StringBuffer();

    static void appendIfString(Object s) {
        match(s,
                kase(String.class, p -> p.equals("hello"), t -> sb.append("foo")),
                kase(Integer.class, p -> p > 3, t -> sb.append("bar"))
        );
    }
}