package com.kimiega.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tan extends Function {

    private final Cos cos;
    private final Sin sin;

    public Tan(Sin sin, Cos cos){
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public BigDecimal count(BigDecimal x) {
        try {
            return  sin.count(x).divide(cos.count(x), RoundingMode.HALF_EVEN);
        } catch (NullPointerException | ArithmeticException e) {
            return null;
        }
    }
}
