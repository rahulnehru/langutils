package collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.BiFunction;

import static junit.framework.TestCase.*;

public class ListTest {

    @Test
    public void ofReturnsNewListWhenSuppliedParams() {
        List l = List.of("hello", "world");
        assertNotNull(l);
    }

    @Test
    public void ofReturnsNewListWhenParamsEmpty() {
        List l = List.of();
        assertNotNull(l);
    }

    @Test
    public void getReturnsInternalArrayListWhenInstantiatedWithParam() {
        List l = List.of("hello", "world");
        assertEquals(ArrayList.class, l.get().getClass());
        assertEquals("hello", l.get().get(0));
        assertEquals("world", l.get().get(1));
    }

    @Test
    public void getReturnsInternalArrayListWhenInstantiatedWithNoParam() {
        List l = List.of();
        assertEquals(ArrayList.class, l.get().getClass());
        assertEquals(0, l.get().size());
    }

    @Test
    public void getWithIndexReturnsValueFromList() {
        List l = List.of("hello", "world");
        assertEquals("world", l.get(1));
    }

    @Test
    public void mapReturnsMappedListWhenNotEmpty() {
        List l = List.of("foo", "bar", "bat");
        List r = l.map(t -> "wow");
        assertEquals(3, r.get().size());
        assertEquals("wow", r.get().get(0));
        assertEquals("wow", r.get().get(1));
        assertEquals("wow", r.get().get(2));
    }

    @Test
    public void mapReturnEmptyListWhenEmpty() {
        List l = List.of();
        List r = l.map(t -> "wow");
        assertEquals(0, r.get().size());
    }

    @Test
    public void zipWithIndexReturnsAMapOfIndexAndItemWhenNotEmpty() {
        List l = List.of("foo", "bar", "bat");
        Map r = l.zipWithIndex();
        assertEquals(3, r.size());
        assertTrue(r.containsKey(0));
        assertEquals("foo", r.get(0));
        assertTrue(r.containsKey(1));
        assertEquals("bar", r.get(1));
        assertTrue(r.containsKey(2));
        assertEquals("bat", r.get(2));
    }

    @Test
    public void zipWithIndexReturnsEmptyMapWhenEmpty() {
        List l = List.of();
        Map r = l.zipWithIndex();
        assertEquals(0, r.size());
    }

    @Test
    public void zipWithReturnsAMapOfMappedValuesAndItemWhenNotEmpty() {
        List l = List.of("foo", "bar", "bat");
        BiFunction<String, Integer, String> f = (t, i) -> i.toString();
        Map r = l.zipWith(f);
        assertEquals(3, r.size());
        assertTrue(r.containsKey("0"));
        assertEquals("foo", r.get("0"));
        assertTrue(r.containsKey("1"));
        assertEquals("bar", r.get("1"));
        assertTrue(r.containsKey("2"));
        assertEquals("bat", r.get("2"));
    }

    @Test
    public void zipWithReturnsEmptyMap() {
        List l = List.of();
        Map r = l.zipWith((s,i) -> "");
        assertEquals(0, r.size());
    }

    @Test
    public void sizeReturnsSizeOfContainedList() {
        List l = List.of("hello");
        List m = List.of();

        assertEquals(1, l.size());
        assertEquals(0, m.size());
    }

    @Test
    public void isEmpty() {
        List l = List.of("hello");
        List m = List.of();

        assertTrue(m.isEmpty());
        assertFalse(l.isEmpty());
    }

    @Test
    public void addReturnsNewListWithAppendedItemWhenOriginalIsNotEmpty() {
        List l = List.of("hello");
        List r = l.add("world");

        assertEquals(2, r.size());
        assertEquals("hello", r.get().get(0));
        assertEquals("world", r.get().get(1));
    }

    @Test
    public void addReturnsNewListWithAppendedItemWhenOriginalIsEmpty() {
        List l = List.of();
        List r = l.add("world");

        assertEquals(1, r.size());
        assertEquals("world", r.get().get(0));
    }
}
