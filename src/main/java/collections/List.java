package collections;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * An immutable wrapper around ArrayList<T> to provide utilities for
 * better functional programming. This is safer to use than ArrayList in
 * multi-threaded functional processing of collections.
 *
 * @param <T>
 */

public class List<T> {

    private ArrayList<T> innerList;

    private List() {
        this.innerList = new ArrayList<>();
    }

    public static <T extends Object> List of(T... items) {
        List<T> newList = new List<>();
        newList.innerList.addAll(Arrays.asList(items));
        return newList;
    }

    public static <T extends Object> List of(Collection<T> items) {
        List<T> newList = new List<>();
        newList.innerList.addAll(items);
        return newList;
    }

    public static <T extends Object> List of(Collection<T> items, T... t) {
        List<T> newList = of(items);
        newList.innerList.addAll(Arrays.asList(t));
        return newList;
    }

    public <R> List map(Function<T, R> mapper) {
        return of(innerList.stream().map(mapper).collect(Collectors.toList()));
    }

    public ArrayList<T> get() {
        return innerList;
    }

    public T get(int i) {
        return innerList.get(i);
    }

    public Map<Integer, T> zipWithIndex() {
        Map<Integer, T> map = new HashMap<>();
        for (T t : innerList) {
            map.put(innerList.indexOf(t), t);
        }
        return map;
    }

    public <R> Map<R, T> zipWith(BiFunction<T, Integer, R> mapperFunction) {
        Map<R, T> map = new HashMap<>();
        for (T t : innerList) {
            map.put(mapperFunction.apply(t, innerList.indexOf(t)), t);
        }
        return map;
    }

    public int size() {
        return innerList.size();
    }

    public boolean isEmpty() {
        return innerList.isEmpty();
    }

    public List add(T... t) {
        return of(this.innerList, t);
    }

}
