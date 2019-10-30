package collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

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

    @Test
    public void splitWillSplitAListCorrectlyOnPredicate() {
        List l = List.of("redgreen", "bluegreen", "redblue", "greenblue");
        Predicate<String> getReds = s -> s.contains("red");

        List<List<String>> splits = l.split(getReds);
        assertEquals(2, splits.size());
        assertEquals(2, splits.get(0).size());
        assertEquals(2, splits.get(1).size());
        assertTrue(splits.get(0).get().stream().allMatch(getReds));
        assertTrue(splits.get(1).get().stream().allMatch(getReds.negate()));
    }

    @Test
    public void splitChunksBasedOnSize() {
        List l = List.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
        List lists = l.split(3);

        assertEquals(5, lists.size());
        assertEquals(3, ((List) lists.get(0)).size());
        assertEquals(3, ((List) lists.get(1)).size());
        assertEquals(3, ((List) lists.get(2)).size());
        assertEquals(3, ((List) lists.get(3)).size());
        assertEquals(2, ((List) lists.get(4)).size());
    }

    @Test
    public void splitReturnsEmptyListWhenEmpty() {
        List l = List.of();
        List lists = l.split(3);

        assertEquals(0, lists.size());
    }
}
