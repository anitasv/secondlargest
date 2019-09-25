package com.amazon.interview.common;

public class MathUtil {

    /**
     * @param val positive integer
     * @return ceil(log_2(val))
     * @throws IllegalArgumentException if non-positive value is passed.
     */
    public static int log2Ceil(int val)
    throws IllegalArgumentException {

        if (val <= 0) {
            throw new IllegalArgumentException();
        }
        int ret = 0;
        boolean truncated = false;
        while (val > 1) {
            if (!truncated && val % 2 == 1) {
                truncated = true;
            }
            val /= 2;
            ++ret;
        }
        return ret + (truncated ? 1 : 0);
    }
}
