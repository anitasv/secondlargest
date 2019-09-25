package com.amazon.interview.secondlargest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AlgoImpl implements Algo {

    static class CandidateEntry<T> {
        private final T val;
        private final List<T> lesser;

        CandidateEntry(T val) {
            this.val = val;
            this.lesser = new ArrayList<>();
        }
    }

    /**
     * Takes O(n) additional space.
     */
    public <T extends Comparable<T>> T second(List<T> list) throws IllegalArgumentException {

        int n = list.size();
        if (n < 2) {
            throw new IllegalArgumentException("Size: " + n + " < 2");
        }

        List<CandidateEntry<T>> candidates = list.stream()
                .map(CandidateEntry::new)
                .collect(Collectors.toList());

        List<CandidateEntry<T>> newCandidates = new ArrayList<>();

        while (candidates.size() > 1) {
            for (int i = 0; i + 1 < candidates.size(); i+= 2) {
                CandidateEntry<T> left = candidates.get(i);
                CandidateEntry<T> right = candidates.get(i + 1);
                if (left.val.compareTo(right.val) < 0) {
                    right.lesser.add(left.val);
                    newCandidates.add(right);
                } else {
                    left.lesser.add(right.val);
                    newCandidates.add(left);
                }
            }
            if (candidates.size() % 2 == 1) {
                newCandidates.add(candidates.get(candidates.size() - 1));
            }

            // Identical to
            // candidates = newCandidates
            // newCandidates = []
            // but hopefully generates less garbage.
            candidates.clear();
            List<CandidateEntry<T>> temp = candidates;
            candidates = newCandidates;
            newCandidates = temp;
        }

        List<T> secondCandidates = candidates.get(0).lesser;
        T secondLargest = secondCandidates.get(0);

        for (T val : secondCandidates) {
            if (val == secondLargest) {
                continue;
            }
            if (val.compareTo(secondLargest) > 0) {
                secondLargest = val;
            }
        }

        return secondLargest;
    }
}
