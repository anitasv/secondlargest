package com.amazon.interview.secondlargest;

public class Main {

    public static void main(String[] args) {

        Algo algo = new AlgoImpl();

        for (int n = 2; n <= 1000; n++) {
            ValidationReport report = Validator.check(algo, n);
            if (!report.isValid()) {
                System.out.println("Failed for n=" + n);
            } else if (!report.isOptimal()) {
                System.out.println("Not optimal for n = " + n +
                        " took " + report.getNumComparisons() +
                        " instead of " + report.getOptimalComparisons());
            }
        }
    }
}
