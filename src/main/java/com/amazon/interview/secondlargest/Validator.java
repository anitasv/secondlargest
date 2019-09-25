package com.amazon.interview.secondlargest;

import com.amazon.interview.common.Dag;
import com.amazon.interview.common.MathUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * Property based tester for Second largest element algorthm.
 *
 * Similar to QuickCheck, except deterministic adversarial check. QuickCheck will
 * have higher coverage, but doesn't ensure worst case behaviours often enough.
 * A combination of adversarial and randomized quickcheck might be recommended
 * for completeness.
 */
public class Validator {

    /**
     * Checks by running the algo wih n non equal items, by giving comparison
     * result as adverse as possible. The comparator is written in such a way as
     * to force the algorithm to use at least n + ceil(lg(n)) - 2 comparisons.
     *
     * Guarantees:
     *  1. Ensures algorithm takes >= n + ceil(lg(n)) - 2 comparisons if algo is valid.
     *  2. Makes sure algorithm actually did all comparisons required to generate a proof
     *    second largest is indeed the second largest, and didn't just get "lucky"
     *
     * Caveats:
     *  1. Even though comparator is adversary to optimal algorithms, it doesn't mean
     *     it will generate worst case scenario of given algorithm.
     *  2. Only uses distinct inputs, if some of them are equal algorithm may still
     *     fail.
     *  3. Even if using distinct inputs it is not necessary algo runs correctly when
     *     the check passes.
     *
     * @param algo Algorithm under test.
     * @param n Number of items to use for the test
     * @return
     *   isOptimal if the test took exactly the minimal number of comparisons.
     *   isValid if the algo returns second largest item after requesting enough comparisons
     *           required to prove it is second largest.
     *   numComparisons - number of comparisons done by the algorithm
     *   optimalComparisons - minimal comparisons by the optimal algorithm
     *   dag - the full list of comparisons requested by the algorithm.
     *         dag.toString() generates DOT language output which can be
     *         visualized using graphviz
     *
     */
    public static ValidationReport check(Algo algo, int n) {
        if (n < 2) {
            throw new IllegalArgumentException("Size must be at least 2, n = " + n);
        }

        AtomicInteger totalComparisons = new AtomicInteger();

        ArrayList<ValidatorItem> list = new ArrayList<>();
        Dag<ValidatorItem> dag = new Dag<>();
        for (int i = 0; i < n; i++) {
            list.add(new ValidatorItem(i, dag, totalComparisons::incrementAndGet));
        }

        ValidatorItem secondLargest = algo.second(Collections.unmodifiableList(list));
        int smallerThings = dag.dominatingCount(secondLargest);
        int largerThings = 0;
        for (ValidatorItem i : list) {
            if (dag.findPath(i, secondLargest)) {
                largerThings++;
            }
        }

        boolean isValid = (largerThings == 1) && (smallerThings == n - 2);

        int lowerBound = n + MathUtil.log2Ceil(n) - 2;
        boolean isOptimal = (lowerBound == totalComparisons.get());

        return new ValidationReport(isOptimal, isValid, totalComparisons.get(), lowerBound, dag);
    }
}

