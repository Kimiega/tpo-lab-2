package com.kimiega.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Sec extends Function {

    private final Cos cos;
    private final int baseScale = 5;

    public Sec(Cos cos){
        this.cos = cos;
    }

    @Override
    public BigDecimal count(BigDecimal x) {
        try {
            return BigDecimal.ONE.setScale(Math.max(x.scale(), baseScale), RoundingMode.HALF_EVEN).divide(cos.count(x), RoundingMode.HALF_EVEN);
        } catch (NullPointerException | ArithmeticException e) {
            return null;
        }
    }
}
