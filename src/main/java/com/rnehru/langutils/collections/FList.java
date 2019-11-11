package com.rnehru.langutils.collections;

import com.rnehru.langutils.match.ObjectMatch;
import com.rnehru.langutils.throwables.Try;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 * Wrapper class around the java.util.ArrayList class to allow for immutable instantiation and handling.
 *
 * @param <T> type of underlying object in ArrayList
 */
public final class FList<T> {

    private ArrayList<T> innerList;

    private FList() {
        this.innerList = new ArrayList<>();
    }

    /**
     * Static call to constructor to create empty List objects.
     *
     * @param <T> Type of underlying member object
     * @return new empty List
     */

    public static <T> FList<T> empty() {
        return new FList<>();
    }

    /**
     * Static call to constructor to create List with objects as varargs
     *
     * @param items items to be added to list
     * @param <T>   type of items, must be same type
     * @return new List with varargs added
     */

    @SafeVarargs
    public static <T> FList<T> of(T... items) {
        FList<T> newList = new FList<>();
        newList.innerList.addAll(Arrays.asList(items));
        return newList;
    }

    /**
     * Static call to constructor to create List object from existing collection
     *
     * @param items Collection to be used
     * @param <T>   type of items in collection
     * @return new List with items from collection
     */

    public static <T> FList<T> of(Collection<T> items) {
        FList<T> newList = new FList<>();
        newList.innerList.addAll(items);
        return newList;
    }

    public FList<T> merge(FList<T> otherList) {
        FList<T> l = this;
        l.innerList.addAll(otherList.innerList);
        return l;
    }


    /** Creates a new list with filtered items that match a predicate
     * @param filterCondition condition to apply to each item in list which must be met
     * @return new FList with only items that match predicate
     */
    public FList<T> filter(Predicate<T> filterCondition) {
        return of(this.innerList.stream().filter(filterCondition).collect(toList()));
    }

    /**
     * Static call to constructor to create List object from existing collection and additional items to be added
     *
     * @param items Collection to be used
     * @param t     items to be added to list
     * @param <T>   type of items, must be same type
     * @return new List with vararg items appended to end of collection
     */

    @SafeVarargs
    public static <T> FList<T> of(Collection<T> items, T... t) {
        FList<T> newList = of(items);
        newList.innerList.addAll(Arrays.asList(t));
        return newList;
    }

    /**
     * maps values inside the List with the mapper function
     *
     * @param mapper Function to take incumbent items of type T and transforms to type R
     * @param <R>    new type of member objects
     * @return new List with items of type R
     */

    public final <R> FList map(Function<T, R> mapper) {
        return of(innerList.stream().map(mapper).collect(toList()));
    }

    /**
     * Gets the underlying java.util.ArrayList
     *
     * @return the underlying collection
     */

    public final ArrayList<T> toArrayList() {
        return innerList;
    }

    /**
     * Gets the item from the index specified from the underlying collection
     *
     * @param i index of the items in the collection
     * @return item at that index
     */

    public final T get(int i) {
        return innerList.get(i);
    }

    /**
     * Produces a HashMap of the list with the key being the index of the item in the collection
     *
     * @return HashMap with the key being the index, and the value being the object in the list
     */

    public final Map<Integer, T> zipWithIndex() {
        Map<Integer, T> map = new HashMap<>();
        for (T t : innerList) {
            map.put(innerList.indexOf(t), t);
        }
        return map;
    }

    /**
     * Produces a map with the key being the result of a mapping of the value e.g.
     *
     * <pre>
     * {@code List t = List.of("101", "102", "103");}
     * {@code t.zipWith((v, idx) -> Integer.parseInt(v));}
     * </pre>
     *
     * @param mapperFunction BiFunction that operates on the value and index of the item in the list
     * @param <R>            type of Key
     * @return new HashMap containing the mapped keys and original values
     */

    public final <R> Map<R, T> zipWith(BiFunction<T, Integer, R> mapperFunction) {
        Map<R, T> map = new HashMap<>();
        for (T t : innerList) {
            map.put(mapperFunction.apply(t, innerList.indexOf(t)), t);
        }
        return map;
    }

    /**
     * Returns size of the underlying list
     *
     * @return size of the list
     */

    public final int size() {
        return innerList.size();
    }

    /**
     * Used to identify whether List is empty
     *
     * @return boolean of whether list is empty
     */

    public final boolean isEmpty() {
        return innerList.isEmpty();
    }

    /**
     * Returns new List with items added
     *
     * @param t varargs of Type T to be added to existing list
     * @return new List with new items appended to the end
     */

    @SafeVarargs
    public final FList<T> add(T... t) {
        return of(this.innerList, t);
    }

    /**
     * Splits the list in two pieces where a value in the list matches a condition e.g.
     * <pre>
     * {@code List l = List.of(1,2,3,4,5);}
     * {@code l.split(p -> p > 2);}
     * </pre>
     * Note, this does not sort the list first!
     *
     * @param predicate condition to determine where the list should be split
     * @return a List containing two lists, split at the point where the
     */

    public final FList<FList<T>> split(Predicate<T> predicate) {
        FList<T> matches = filter(predicate);
        FList<T> nonMatches = filter(predicate.negate());
        return of(matches, nonMatches);
    }

    /**
     * Split function to split the List into multiple lists of a given size
     *
     * @param size Target size for each sublist
     * @return List containing sublists of max size
     */

    public final FList<FList<T>> split(int size) {
        FList<FList<T>> l = new FList<>();
        for (int min = 0; min < this.innerList.size(); min += size) {
            int max = this.innerList.size() > min + size ? min + size : this.innerList.size();
            l.innerList.add(of(this.innerList.subList(min, max)));
        }
        return l;
    }


    /**
     * Used to get the tail of the list, or empty if the List has one or less elements
     *
     * @return new list, which is the sublist from index=1
     */

    public final FList<T> tail() {
        return size() > 0 ? of(innerList.subList(1, size())) : of();
    }

    /**
     * First element in a list, will throw an exception if the list is empty
     *
     * @return first element in the list
     */

    public final T head() {
        return innerList.get(0);
    }

    /**
     * Similar to head(), but wrapped in an option
     *
     * @return Returns Optional.empty() if the list is empty, or Optional.of(x) where x is the first element in the list
     */

    public final Optional<T> headOption() {
        return Try.apply(l -> l.head(), this).getOption();
    }

    /**
     * Probably the most powerful function, used for folding elements of the list into some final value
     * <pre>
     *     {@code List l = new List.of(1,2,3,4,5);}
     *     {@code BiFunction foldFunction = (acc, t) -> acc + t;}
     *     {@code l.foldLeft(0).row(foldFunction);}
     * </pre>
     *
     * @param seed the value into which the items should be folded - this can be a collection too
     * @param <U>  the type to be returned
     * @return the value of folded items
     */

    public final <U> Function<BiFunction<U, T, U>, U> foldLeft(U seed) {
        return function -> {
            U acc = this.headOption().isPresent() ? function.apply(seed, this.head()) : seed;
            FList<T> tail = this.tail();
            while (tail.size() > 0) {
                acc = function.apply(acc, tail.head());
                tail = tail.tail();
            }
            return acc;
        };
    }

    /**
     * Reverses the order of the list
     *
     * @return a new list with the order reversed
     */

    public final FList<T> reverse() {
        java.util.List<T> al = new ArrayList<>();
        for (int i = this.innerList.size() - 1; i >= 0; i--) {
            al.add(this.innerList.get(i));
        }
        return FList.of(al);
    }

    public final <U> FList<U> flatten() {
        return this.foldLeft(FList.<U>empty()).apply((acc, l) ->
                ObjectMatch.match(l,
                        ObjectMatch.map(Collection.class, c -> acc.merge(FList.<U>of(c))),
                        ObjectMatch.map(FList.class, (Function<FList, FList>) acc::merge)
                ));
    }

}
