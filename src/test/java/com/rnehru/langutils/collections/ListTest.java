package com.rnehru.langutils.collections;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import static junit.framework.TestCase.*;

public class ListTest {

    @Test
    public void ofReturnsNewListWhenSuppliedParams() {
        FList l = FList.of("hello", "world");
        assertNotNull(l);
    }

    @Test
    public void ofReturnsNewListWhenParamsEmpty() {
        FList l = FList.empty();
        assertNotNull(l);
    }

    @Test
    public void getReturnsInternalArrayListWhenInstantiatedWithParam() {
        FList l = FList.of("hello", "world");
        assertEquals(ArrayList.class, l.toArrayList().getClass());
        assertEquals("hello", l.toArrayList().get(0));
        assertEquals("world", l.toArrayList().get(1));
    }

    @Test
    public void getReturnsInternalArrayListWhenInstantiatedWithNoParam() {
        FList l = FList.empty();
        assertEquals(ArrayList.class, l.toArrayList().getClass());
        assertEquals(0, l.toArrayList().size());
    }

    @Test
    public void getWithIndexReturnsValueFromList() {
        FList l = FList.of("hello", "world");
        assertEquals("world", l.get(1));
    }

    @Test
    public void mapReturnsMappedListWhenNotEmpty() {
        FList l = FList.of("foo", "bar", "bat");
        FList<String> r = l.map(t -> "wow");
        assertEquals(3, r.toArrayList().size());
        assertEquals("wow", r.toArrayList().get(0));
        assertEquals("wow", r.toArrayList().get(1));
        assertEquals("wow", r.toArrayList().get(2));
    }

    @Test
    public void mapReturnEmptyListWhenEmpty() {
        FList l = FList.empty();
        FList r = l.map(t -> "wow");
        assertEquals(0, r.toArrayList().size());
    }

    @Test
    public void zipWithIndexReturnsAMapOfIndexAndItemWhenNotEmpty() {
        FList l = FList.of("foo", "bar", "bat");
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
        FList l = FList.empty();
        Map r = l.zipWithIndex();
        assertEquals(0, r.size());
    }

    @Test
    public void zipWithReturnsAMapOfMappedValuesAndItemWhenNotEmpty() {
        FList l = FList.of("foo", "bar", "bat");
        BiFunction<String, Integer, String> f = (t, i) -> i.toString();
        Map<String, String> r = l.zipWith(f);
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
        FList<String> l = FList.empty();
        Map<String, String> r = l.zipWith((s,i) -> "");
        assertEquals(0, r.size());
    }

    @Test
    public void sizeReturnsSizeOfContainedList() {
        FList l = FList.of("hello");
        FList m = FList.empty();

        assertEquals(1, l.size());
        assertEquals(0, m.size());
    }

    @Test
    public void isEmpty() {
        FList l = FList.of("hello");
        FList m = FList.empty();

        assertTrue(m.isEmpty());
        assertFalse(l.isEmpty());
    }

    @Test
    public void addReturnsNewListWithAppendedItemWhenOriginalIsNotEmpty() {
        FList<String> l = FList.of("hello");
        FList<String> r = l.add("world");

        assertEquals(2, r.size());
        assertEquals("hello", r.toArrayList().get(0));
        assertEquals("world", r.toArrayList().get(1));
    }

    @Test
    public void addReturnsNewListWithAppendedItemWhenOriginalIsEmpty() {
        FList<String> l = FList.empty();
        FList<String> r = l.add("world");

        assertEquals(1, r.size());
        assertEquals("world", r.toArrayList().get(0));
    }

    @Test
    public void splitWillSplitAListCorrectlyOnPredicate() {
        FList l = FList.of("redgreen", "bluegreen", "redblue", "greenblue");
        Predicate<String> getReds = s -> s.contains("red");

        FList<FList<String>> splits = l.split(getReds);
        assertEquals(2, splits.size());
        assertEquals(2, splits.get(0).size());
        assertEquals(2, splits.get(1).size());
        assertTrue(splits.get(0).toArrayList().stream().allMatch(getReds));
        assertTrue(splits.get(1).toArrayList().stream().allMatch(getReds.negate()));
    }

    @Test
    public void splitChunksBasedOnSize() {
        FList l = FList.of(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
        FList lists = l.split(3);

        assertEquals(5, lists.size());
        assertEquals(3, ((FList) lists.get(0)).size());
        assertEquals(3, ((FList) lists.get(1)).size());
        assertEquals(3, ((FList) lists.get(2)).size());
        assertEquals(3, ((FList) lists.get(3)).size());
        assertEquals(2, ((FList) lists.get(4)).size());
    }

    @Test
    public void splitReturnsEmptyListWhenEmpty() {
        FList l = FList.empty();
        FList lists = l.split(3);

        assertEquals(0, lists.size());
    }

    @Test
    public void tailReturnsTailOfListWhenNonEmpty() {
        FList l = FList.of("a", "b", "c");
        assertEquals(2, l.tail().size());
    }

    @Test
    public void tailReturnsTailOfListWhenSizeOne() {
        FList l = FList.of("a");
        assertEquals(0, l.tail().size());
    }

    @Test
    public void tailReturnsTailOfListWhenEmpty() {
        FList l = FList.empty();
        assertEquals(0, l.tail().size());
    }

    @Test
    public void headReturnsFirstElementWhenNonEmpty() {
        FList l = FList.of("a", "b", "c");
        assertEquals("a", l.head());
    }

    @Test
    public void headReturnsFirstElementWhenSizeOne() {
        FList l = FList.of("a");
        assertEquals("a", l.head());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void headThrowsExceptionWhenEmpty() {
        FList.empty().head();
    }

    @Test
    public void headOptionalReturnsSomeOfElementWhenNonEmpty() {
        FList l = FList.of("a", "b", "c");
        assertEquals(Optional.of("a"), l.headOption());
    }

    @Test
    public void headOptionalReturnsSomeOfElementWhenSizeOne() {
        FList l = FList.of("a");
        assertEquals(Optional.of("a"), l.headOption());
    }

    @Test
    public void headOptionalReturnsNoneWhenEmpty() {
        FList l = FList.empty();
        assertEquals(Optional.empty(), l.headOption());
    }

    @Test
    public void foldLeftReturnsSeedWhenEmpty() {
        FList l = FList.empty();
        BiFunction<Integer, String, Integer> foldFunction = (acc, t) -> (acc + Integer.parseInt(t));
        assertEquals(0, l.foldLeft(0).apply(foldFunction));
    }

    @Test
    public void foldLeftReturnsSeedWhenNonEmpty() {
        FList l = FList.of("1", "3", "11");
        BiFunction<Integer, String, Integer> foldFunction = (acc, t) -> (acc + Integer.parseInt(t));
        assertEquals(19, l.foldLeft(4).apply(foldFunction));
    }

    @Test
    public void foldLeftReturnsSeedWhenSizeOne() {
        FList l = FList.of("1");
        BiFunction<Integer, String, Integer> foldFunction = (acc, t) -> (acc + Integer.parseInt(t));
        assertEquals(5, l.foldLeft(4).apply(foldFunction));
    }

    @Test
    public void foldLeftRunsFromRightToLeft() {
        FList l = FList.of("a", "b", "c");
        BiFunction<String, String, String> foldFunction = (acc, t) -> acc + t;
        assertEquals("abc", l.foldLeft("").apply(foldFunction));
    }

    @Test
    public void foldLeftIsCompatibleWithCollections() {
        FList l = FList.of("a", "b", "c");
        BiFunction<FList<String>, String, FList<String>> foldFunction = FList::add;
        FList<String> u = (FList<String>) l.foldLeft(FList.empty()).apply(foldFunction);
        assertEquals(3, u.size());
        assertEquals("a", u.get(0));
        assertEquals("b", u.get(1));
        assertEquals("c", u.get(2));
    }

    @Test
    public void reverseReturnsEmptyListWhenEmpty() {
        FList l = FList.empty();
        assertTrue(l.reverse().isEmpty());
    }

    @Test
    public void reverseReturnsReversedListWhenNotEmpty() {
        FList l = FList.of("a", "b", "c");
        FList r = l.reverse();
        assertEquals("c", r.get(0));
        assertEquals("b", r.get(1));
        assertEquals("a", r.get(2));
    }

    @Test
    public void flattenReturnsEmptyListWhenListOfListsIsEmpty() {
        FList l = FList.empty();
        assertTrue(l.flatten().isEmpty());
    }

    @Test
    public void flattenReturnsEmptyListWhenListContainsEmptyLists() {
        Collection l1 = FList.empty().toArrayList();
        Collection l2 = FList.empty().toArrayList();
        Collection l3 = FList.empty().toArrayList();
        FList l = FList.of(l1, l2, l3);
        assertTrue(l.flatten().isEmpty());
    }

    @Test
    public void flattenReturnsFlattenedListWhenCollectionsNotEmpty() {
        Collection l1 = FList.of("a").toArrayList();
        Collection l2 = FList.of("b").toArrayList();
        Collection l3 = FList.of("c").toArrayList();
        FList l = FList.of(FList.empty().toArrayList(), l1, l2, l3).flatten();
        assertFalse(l.isEmpty());
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
        assertEquals("c", l.get(2));
    }

    @Test
    public void flattenReturnsFlattenedListWhenListsNotEmpty() {
        FList l1 = FList.of("a");
        FList l2 = FList.of("b");
        FList l3 = FList.of("c");
        FList l = FList.of(FList.empty(), l1, l2, l3).flatten();
        assertFalse(l.isEmpty());
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
        assertEquals("c", l.get(2));
    }
}
