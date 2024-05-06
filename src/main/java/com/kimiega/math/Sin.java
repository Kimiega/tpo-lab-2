package com.kimiega.math;

import java.math.BigDecimal;
import java.math.MathContext;

public class Sin extends Function {

    @Override
    public BigDecimal count(BigDecimal x) {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal prev;
        int i = 0;

        do {
            prev = sum;
            sum = sum.add(BigDecimal.valueOf(minusOnePow(i)).multiply(prod(x, 2 * i + 1)));
            ++i;
        } while (BigDecimal.valueOf(accuracy).compareTo(prev.subtract(sum).abs()) <= 0);

        return sum;
    }

    private static int minusOnePow(int n) {
        return (int) Math.pow(-1, n);
    }

    private static BigDecimal prod(BigDecimal x, int n) {
        BigDecimal accum = BigDecimal.ONE;

        for (int i = 1; i <= n; i++) {
            accum = accum.multiply(x.divide(BigDecimal.valueOf(i), MathContext.DECIMAL128));
        }

        return accum;
    }
}
