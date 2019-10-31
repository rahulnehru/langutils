package collections;

import throwables.ThrowableFunction;
import throwables.Try;

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

    public static <T> List<T> empty() {
        return new List<>();
    }

    public static <T> List<T> of(T... items) {
        List<T> newList = new List<>();
        newList.innerList.addAll(Arrays.asList(items));
        return newList;
    }

    public static <T> List<T> of(Collection<T> items) {
        List<T> newList = new List<>();
        newList.innerList.addAll(items);
        return newList;
    }

    public static <T> List<T> of(Collection<T> items, T... t) {
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

    public List<T> add(T... t) {
        return of(this.innerList, t);
    }

    public List<List<T>> split(Predicate<T> predicate) {
        List<T> matches = of(this.innerList.stream().filter(predicate).collect(toList()));
        List<T> nonMatches = of(this.innerList.stream().filter(predicate.negate()).collect(toList()));
        return of(matches, nonMatches);
    }

    public List<List<T>> split(int sizes) {
        List<List<T>> l = new List<>();
        for (int min = 0; min < this.innerList.size(); min += sizes) {
            int max = this.innerList.size() > min + sizes ? min + sizes : this.innerList.size();
            l.innerList.add(of(this.innerList.subList(min, max)));
        }
        return l;
    }


    public List<T> tail() {
        return size() > 0 ? of(innerList.subList(1, size())) : of();
    }

    public T head() {
        return innerList.get(0);
    }

    public Optional<T> headOption() {
        return Try.apply(l -> l.head(), this).getOption();
    }

    public <U> Function<BiFunction<U, T, U>, U> foldLeft(U seed) {
        return function -> {
            U acc = this.headOption().isPresent() ? function.apply(seed, this.head()) : seed;
            List<T> tail = this.tail();
            while(tail.size()>0) {
                acc = function.apply(acc, tail.head());
                tail = tail.tail();
            }
            return acc;
        };
    }

}
