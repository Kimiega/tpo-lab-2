package com.kimiega.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LogN extends Function {
    public BigDecimal base;
    public Ln ln;

    public LogN(BigDecimal base, Ln ln){
        if(base.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("основание логарифма не может быть не положительно");
        if(base.compareTo(BigDecimal.ONE) == 0) throw new IllegalArgumentException("основание логарифма не может быть равно 1");
        this.base = base;
        this.ln = ln;

    }
    @Override
    public BigDecimal count(BigDecimal x) {
        try {
            return ln.count(x).divide(ln.count(this.base.multiply(BigDecimal.valueOf(1.0))), RoundingMode.HALF_EVEN);
        } catch (NullPointerException | ArithmeticException e) {
            return null;
        }
    }
}
