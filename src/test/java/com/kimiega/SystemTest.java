package com.kimiega;

import com.kimiega.math.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class SystemTest {

    static final String inputPath = "/eSysOut.csv";
    static Cos cosMock;
    static Sin sinMock;
    static Ln lnMock;
    static LogN log2Mock;
    static LogN log3Mock;
    static LogN log5Mock;
    static Sec secMock;
    static Tan tanMock;
    static Cot cotMock;

    static <T extends Function>  T setMock(String path, Class<T> mockClass){
        T mockInstance = Mockito.mock(mockClass);
        try {
            Reader reader = new FileReader(path);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : records) {
                BigDecimal input = BigDecimal.valueOf(Double.parseDouble(record.get(0)));
                BigDecimal output;
                if(record.get(1).equals("")) output = null;
                else {
                    output = BigDecimal.valueOf(Double.parseDouble(record.get(1)));
                }
                Mockito.when(mockInstance.count(input)).thenReturn(output);
            }

        } catch (IOException e) {
            throw new RuntimeException("Не найден файл или не удалось его распарсить!");
        }
        return mockInstance;
    }



    @BeforeAll
    static void init(){
        cosMock = setMock("src/test/resources/CosIn.csv", Cos.class);
        sinMock = setMock("src/test/resources/SinIn.csv", Sin.class);
        lnMock = setMock("src/test/resources/LnIn.csv", Ln.class);
        log2Mock = setMock("src/test/resources/Log2In.csv", LogN.class);
        log3Mock = setMock("src/test/resources/Log3In.csv", LogN.class);
        log5Mock = setMock("src/test/resources/Log5In.csv", LogN.class);
        secMock = setMock("src/test/resources/secIn.csv", Sec.class);
        tanMock = setMock("src/test/resources/tanIn.csv", Tan.class);
        cotMock = setMock("src/test/resources/cotIn.csv", Cot.class);

    }
    @ParameterizedTest(name = "no mock test")
    @CsvFileSource(resources = {inputPath})
    void testNoMock(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(new Cos(new Sin()), new Sin(), new Tan(new Sin(), new Cos(new Sin())), new Cot(new Sin(), new Cos(new Sin())), new Sec(new Cos(new Sin())), new LogN(BigDecimal.valueOf(2), new Ln()), new LogN(BigDecimal.valueOf(3), new Ln()), new LogN(BigDecimal.valueOf(5), new Ln()), new Ln());
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else{
            BigDecimal Y = BigDecimal.valueOf(y);
            if(Math.abs(y) < 1) Assertions.assertEquals(Y.setScale(2, RoundingMode.HALF_EVEN), equationSystem.count(X).setScale(2, RoundingMode.HALF_EVEN));
            else Assertions.assertEquals(Y.setScale(1, RoundingMode.HALF_UP), equationSystem.count(X).setScale(1, RoundingMode.HALF_UP));
        }

    }
    @ParameterizedTest(name = "all mock test")
    @CsvFileSource(resources = inputPath)
    void testOnlyMock(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(cosMock, sinMock, tanMock, cotMock, secMock, log2Mock, log3Mock, log5Mock, lnMock);
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else{
            BigDecimal Y = BigDecimal.valueOf(y);
            Assertions.assertEquals(Y.setScale(5, RoundingMode.HALF_UP), equationSystem.count(X).setScale(5, RoundingMode.HALF_UP));
        }

    }

    @ParameterizedTest(name = "Ln mock test")
    @CsvFileSource(resources = inputPath)
    void testLnMock(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(new Cos(new Sin()), new Sin(), new Tan(new Sin(), new Cos(new Sin())), new Cot(new Sin(), new Cos(new Sin())), new Sec(new Cos(new Sin())), new LogN(BigDecimal.valueOf(2), lnMock), new LogN(BigDecimal.valueOf(3), lnMock), new LogN(BigDecimal.valueOf(5), lnMock), lnMock);
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else{
            BigDecimal Y = BigDecimal.valueOf(y);
            BigDecimal res = equationSystem.count(X);
            Assertions.assertEquals(Y.setScale(5, RoundingMode.HALF_UP), equationSystem.count(X).setScale(5, RoundingMode.HALF_UP));
        }
    }

    @ParameterizedTest(name = "Cos mock test")
    @CsvFileSource(resources = inputPath)
    void testCos(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(cosMock, new Sin(), new Tan(new Sin(), cosMock), new Cot(new Sin(), cosMock), new Sec(cosMock), new LogN(BigDecimal.valueOf(2), new Ln()), new LogN(BigDecimal.valueOf(3), new Ln()), new LogN(BigDecimal.valueOf(5), new Ln()), new Ln());
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else{
            BigDecimal Y = BigDecimal.valueOf(y);
            Assertions.assertEquals(Y.setScale(5, RoundingMode.HALF_UP), equationSystem.count(X).setScale(5, RoundingMode.HALF_UP));
        }
    }

    @ParameterizedTest(name = "Sin mock test")
    @CsvFileSource(resources = inputPath)
    void testSin(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(new Cos(sinMock), sinMock, new Tan(sinMock, new Cos(sinMock)), new Cot(sinMock, new Cos(sinMock)), new Sec(new Cos(sinMock)), new LogN(BigDecimal.valueOf(2), new Ln()), new LogN(BigDecimal.valueOf(3), new Ln()), new LogN(BigDecimal.valueOf(5), new Ln()), new Ln());
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else{
            BigDecimal Y = BigDecimal.valueOf(y);
            Assertions.assertEquals(Y.setScale(5, RoundingMode.HALF_UP), equationSystem.count(X).setScale(5, RoundingMode.HALF_UP));
        }
    }

    @ParameterizedTest(name = "Ln test")
    @CsvFileSource(resources = inputPath)
    void testLn(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(cosMock, sinMock, tanMock, cotMock, secMock, log2Mock, log3Mock, log5Mock, new Ln());
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else {
            BigDecimal Y = BigDecimal.valueOf(y);
            if(Math.abs(y) < 1) Assertions.assertEquals(Y.setScale(2, RoundingMode.HALF_EVEN), equationSystem.count(X).setScale(2, RoundingMode.HALF_EVEN));
            else Assertions.assertEquals(Y.setScale(1, RoundingMode.HALF_UP), equationSystem.count(X).setScale(1, RoundingMode.HALF_UP));        }


    }

    @ParameterizedTest(name = "Log2 test")
    @CsvFileSource(resources = inputPath)
    void testLog2(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(cosMock, sinMock, tanMock, cotMock, secMock, new LogN(BigDecimal.valueOf(2), new Ln()), log3Mock, log5Mock, lnMock);
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else{
            BigDecimal Y = BigDecimal.valueOf(y);
            if(Math.abs(y) < 1) Assertions.assertEquals(Y.setScale(2, RoundingMode.HALF_EVEN), equationSystem.count(X).setScale(2, RoundingMode.HALF_EVEN));
            else Assertions.assertEquals(Y.setScale(1, RoundingMode.HALF_UP), equationSystem.count(X).setScale(1, RoundingMode.HALF_UP));        }
    }

    @ParameterizedTest(name = "Log3 test")
    @CsvFileSource(resources = inputPath)
    void testLog3(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(cosMock, sinMock, tanMock, cotMock, secMock, log2Mock, new LogN(BigDecimal.valueOf(3), new Ln()), log5Mock, lnMock);
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else{
            BigDecimal Y = BigDecimal.valueOf(y);
            if(Math.abs(y) < 1) Assertions.assertEquals(Y.setScale(2, RoundingMode.HALF_EVEN), equationSystem.count(X).setScale(2, RoundingMode.HALF_EVEN));
            else Assertions.assertEquals(Y.setScale(1, RoundingMode.HALF_UP), equationSystem.count(X).setScale(1, RoundingMode.HALF_UP));
        }
    }

    @ParameterizedTest(name = "Log5 test")
    @CsvFileSource(resources = inputPath)
    void testLog10(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(cosMock, sinMock, tanMock, cotMock, secMock, log2Mock, log3Mock, new LogN(BigDecimal.valueOf(5), new Ln()), lnMock);
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else{
            BigDecimal Y = BigDecimal.valueOf(y);
            if(Math.abs(y) < 1) Assertions.assertEquals(Y.setScale(2, RoundingMode.HALF_EVEN), equationSystem.count(X).setScale(2, RoundingMode.HALF_EVEN));
            else Assertions.assertEquals(Y.setScale(1, RoundingMode.HALF_UP), equationSystem.count(X).setScale(1, RoundingMode.HALF_UP));
        }

    }

    @ParameterizedTest(name = "sin + ln mock test")
    @CsvFileSource(resources = inputPath)
    void testCosLn(Double x, Double y){
        BigDecimal X = BigDecimal.valueOf(x);
        EquationSystem equationSystem = new EquationSystem(new Cos(sinMock), sinMock, new Tan(sinMock, new Cos(sinMock)), new Cot(sinMock, new Cos(sinMock)), new Sec(new Cos(sinMock)), new LogN(BigDecimal.valueOf(2), lnMock), new LogN(BigDecimal.valueOf(3), lnMock), new LogN(BigDecimal.valueOf(5), lnMock), lnMock);
        if(Objects.isNull(y)) Assertions.assertNull(equationSystem.count(X));
        else{
            BigDecimal Y = BigDecimal.valueOf(y);

            if(Math.abs(y) < 1) Assertions.assertEquals(Y.setScale(2, RoundingMode.HALF_EVEN), equationSystem.count(X).setScale(2, RoundingMode.HALF_EVEN));
            else Assertions.assertEquals(Y.setScale(1, RoundingMode.HALF_UP), equationSystem.count(X).setScale(1, RoundingMode.HALF_UP));
        }

    }

}
