package com.amazon.interview.secondlargest;

import com.amazon.interview.common.Dag;

import java.util.concurrent.ThreadLocalRandom;

class ValidatorItem implements Comparable<ValidatorItem> {

    private final int index;
    private final Dag<ValidatorItem> dag;
    private final Runnable counter;

    private int wins;
    private int loss;

    ValidatorItem(int index, Dag<ValidatorItem> dag, Runnable counter) {
        this.index = index;
        this.dag = dag;
        this.counter = counter;
    }

    private boolean undefeated() {
        return this.loss == 0;
    }

    @Override
    public String toString() {
        return String.valueOf(index);
    }

    private int beats(ValidatorItem another) {
        this.wins++;
        another.loss++;
        dag.addPartialOrder(this, another);
        return 1;
    }

    private int loses(ValidatorItem another) {
        return -another.beats(this);
    }

    private int chooseAnyOrder(ValidatorItem another) {
        // Doesn't matter any order is fine!
        // Even randomness is not needed, just for fun!
        if (ThreadLocalRandom.current().nextBoolean()) {
            return this.beats(another);
        } else {
            return this.loses(another);
        }
    }

    @Override
    public int compareTo(ValidatorItem another) {
        counter.run();
        if (another == this) {
            return 0;
        }
        if (undefeated()) {
            if (!another.undefeated()) {
                return this.beats(another);
            } else {
                // both undefeated.
                if (this.wins > another.wins) {
                    return this.beats(another);
                } else if (this.wins < another.wins) {
                    return this.loses(another);
                } else {
                    return chooseAnyOrder(another);
                }
            }
        } else {
            if (dag.findPath(this, another)) {
                return this.beats(another);
            }
            if (dag.findPath(another, this)) {
                return this.loses(another);
            }
            return chooseAnyOrder(another);
        }
    }
}
