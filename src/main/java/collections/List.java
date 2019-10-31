package collections;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;


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
        return of(innerList.stream().map(mapper).collect(toList()));
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

    public List<List<T>> split(Predicate<T> predicate) {
        List<T> matches = of(this.innerList.stream().filter(predicate).collect(toList()));
        List<T> nonMatches = of(this.innerList.stream().filter(predicate.negate()).collect(toList()));
        return of(matches, nonMatches);
    }

    public List<List<T>> split(int sizes) {
        List l = new List();
        for (int min = 0; min < this.innerList.size(); min += sizes) {
            int max = this.innerList.size() > min + sizes ? min + sizes : this.innerList.size();
            l.innerList.add(of(this.innerList.subList(min, max)));
        }
        return l;
    }


}
