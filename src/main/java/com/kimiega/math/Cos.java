package com.kimiega.math;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Cos extends Function {

    private final Sin sin;

    public Cos(Sin sin){
        this.sin = sin;
    }

    @Override
    public BigDecimal count(BigDecimal x) {
        try {
            return sin.count(x.add(new BigDecimal(Math.PI / 2)));
        } catch (NullPointerException | ArithmeticException e) {
            return null;
        }
    }
}
