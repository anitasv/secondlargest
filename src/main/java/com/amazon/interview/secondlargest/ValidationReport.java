package com.amazon.interview.secondlargest;

import com.amazon.interview.common.Dag;

public class ValidationReport {
    private final boolean isOptimal;
    private final boolean isValid;
    private final int numComparisons;
    private final int optimalComparisons;
    private final Dag dag;

    public ValidationReport(boolean isOptimal, boolean isValid, int numComparisons,
                            int optimalComparisons, Dag dag) {
        this.isOptimal = isOptimal;
        this.isValid = isValid;
        this.numComparisons = numComparisons;
        this.optimalComparisons = optimalComparisons;
        this.dag = dag;
    }

    public boolean isOptimal() {
        return isOptimal;
    }

    public int getNumComparisons() {
        return numComparisons;
    }

    public Dag getDag() {
        return dag;
    }

    public boolean isValid() {
        return isValid;
    }

    public int getOptimalComparisons() {
        return optimalComparisons;
    }
}
