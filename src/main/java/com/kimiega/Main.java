package com.kimiega;

import com.kimiega.math.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {
    public static void main(String[] args) {
        Sin sin = new Sin();
        Cos cos = new Cos(new Sin());
        Ln ln = new Ln();
        LogN log2 = new LogN(BigDecimal.valueOf(2), new Ln());
        LogN log3 = new LogN(BigDecimal.valueOf(3), new Ln());
        LogN log5 = new LogN(BigDecimal.valueOf(5), new Ln());
        Sec sec = new Sec(new Cos(new Sin()));
        Tan tan = new Tan(new Sin(), new Cos(new Sin()));
        Cot cot = new Cot(new Sin(), new Cos(new Sin()));
        BigDecimal x = BigDecimal.valueOf(3);
        BigDecimal start = BigDecimal.valueOf(-4);
        BigDecimal end = BigDecimal.valueOf(4);
        BigDecimal step = BigDecimal.valueOf(0.1);
        Function eSys = new EquationSystem(cos, sin, tan, cot, sec, log2, log3, log5, ln);
        sin.toCsv("sinIn.csv", sin.countRange(start, end, step));
        cos.toCsv("cosIn.csv", cos.countRange(start, end, step));
        ln.toCsv("lnIn.csv", ln.countRange(start, end, step));
        log2.toCsv("log2In.csv", log2.countRange(start, end, step));
        log3.toCsv("log3In.csv", log3.countRange(start, end, step));
        log5.toCsv("log5In.csv", log5.countRange(start, end, step));
        sec.toCsv("secIn.csv", sec.countRange(start, end, step));
        tan.toCsv("tanIn.csv", tan.countRange(start, end, step));
        cot.toCsv("cotIn.csv", cot.countRange(start, end, step));
        eSys.toCsv("eSysOut.csv", eSys.countRange(start, end, step));
    }
}