package com.kimiega.math;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class Function {
    public abstract BigDecimal count(BigDecimal x);

    public final double accuracy = 0.00001;

    public final SortedMap<BigDecimal, BigDecimal> countRange(BigDecimal start, BigDecimal finish, BigDecimal step){
        SortedMap<BigDecimal, BigDecimal> result = new TreeMap<>();
        for(BigDecimal i = start; i.compareTo(finish) <= 0; i = i.add(step))
            result.put(i, count(i));
        return result;
    }

    public final void toCsv(String path, SortedMap<BigDecimal, BigDecimal> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (Map.Entry<BigDecimal, BigDecimal> entry : data.entrySet()) {
                if(entry.getValue() == null) writer.println(entry.getKey() + ",");
                else writer.println(entry.getKey() + "," + entry.getValue().setScale(10, RoundingMode.HALF_EVEN));
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }
}
