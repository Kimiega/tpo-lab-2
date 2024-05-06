package com.kimiega.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Cot extends Function {

    private final Cos cos;
    private final Sin sin;

    public Cot(Sin sin, Cos cos){
        this.sin = sin;
        this.cos = cos;
    }

    @Override
    public BigDecimal count(BigDecimal x) {
        try {
         return  cos.count(x).divide(sin.count(x), RoundingMode.HALF_EVEN);
        } catch (NullPointerException | ArithmeticException e) {
            return null;
        }
    }
}
