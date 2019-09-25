package com.amazon.interview.secondlargest;

import java.util.List;

public interface Algo {

    /**
     * Returns the second largest item in the list given.
     * Must not modify the given list.
     *
     * @returns second largest element. More precisely, if sorted
     * with the comparator, the element at index n - 2.
     * collections.sort(list)
     * return list.get(list.size() - 2);
     *
     * @throws IllegalArgumentException if size of the list
     * is less than 2.
     *
     * Assumes comparator follows compare(a,a) = 0, and transitive
     * rules.
     */
    <T extends Comparable<T>> T second(List<T> list)
    throws IllegalArgumentException;

}
