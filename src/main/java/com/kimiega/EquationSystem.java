package com.kimiega;

import com.kimiega.math.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EquationSystem extends Function {
    public Cos cos;
    public Sin sin;
    public Cot cot;
    public Tan tan;
    public Sec sec;
    public LogN log2;
    public LogN log5;
    public LogN log3;
    public Ln ln;

    private final Integer cS = 5; //compare Scale

    public EquationSystem(Cos cos, Sin sin, Tan tan, Cot cot, Sec sec, LogN log2, LogN log3, LogN log5, Ln ln){
        this.cos = cos;
        this.sin = sin;
        this.tan = tan;
        this.cot = cot;
        this.sec = sec;
        this.log2 = log2;
        this.log3 = log3;
        this.log5 = log5;
        this.ln = ln;
    }

    @Override
    public BigDecimal count(BigDecimal x) {
        try {
            if (x.compareTo(BigDecimal.ZERO) <= 0) {
                var v1 = sec.count(x).subtract(sin.count(x)).subtract(tan.count(x)).subtract(cos.count(x));
                var v2 = sec.count(x).subtract(cot.count(x));
                var v3 = sec.count(x).subtract(sec.count(x)).divide(cot.count(x), RoundingMode.HALF_EVEN).pow(3);

                return v1.multiply(v2).multiply(v3).setScale(cS, RoundingMode.HALF_EVEN);
            } else {
                var v1 = log3.count(x).subtract(log5.count(x)).multiply(log2.count(x).subtract(log5.count(x))).add(log3.count(x));
                var v2 = log3.count(x).multiply(log5.count(x)).divide(ln.count(x), RoundingMode.HALF_EVEN).pow(2);
                var v3 = log3.count(x).divide(log3.count(x).divide(log2.count(x), RoundingMode.HALF_EVEN), RoundingMode.HALF_EVEN);
                return v1.divide(v2, RoundingMode.HALF_EVEN).add(v3).setScale(cS, RoundingMode.HALF_EVEN);
            }
        } catch (ArithmeticException | NullPointerException e) {
            return null;
        }
    }
}