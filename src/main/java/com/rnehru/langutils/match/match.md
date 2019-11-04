# Match

Scala's match-case is one of the language's most powerful features. It allows developers to produce effects and side
effects on an object based on:

* Types
* Values
* Predicates
* Regex matching (for Strings)

This however has been a missing feature in Java for a long time. Enter LangUtils.

## Match types

For the purposes of maintaining referential transparency, match statements which produce side effects have been 
separated out from those which produce effects, or values.

### Match-Kase
Match-Kase statements in LangUtils are a way in which a object can be matched to produce some side effect.

### Match-Map
Match-Map statements in LangUtils are a way in which a object can be matched to produce some effect and value.


## ObjectMatch
The ObjectMatch class allows you to achieve type, value and predicate matching for Kase and Map style statements as per:

### Match-Kase

```java
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
```

### Match-Map

```java
import static com.rnehru.langutils.match.ObjectMatch.*;

class ObjectMatchExample {

    static StringBuffer sb = new StringBuffer();

    static int appendIfString(Object s) {
        return match(s, 
                map(String.class, p -> p.equals("hello"), t -> t.indexOf("h")), 
                map(Integer.class, t -> 4)
        );
    }
}
```
