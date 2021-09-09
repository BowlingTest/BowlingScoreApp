package com.test.calculator;

import com.test.exceptions.InValidRollException;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;


/**
 * Created by svmen on 9/8/2021.
 */
public class BowlingScoreCalculatorTest {

    @org.junit.Test
    public void roll() throws Exception {

    }

    BowlingScoreCalculator calculator;
    private static final String gameNAme = "G1";

    @Before
    public void setUp() {
        calculator = new BowlingScoreCalculator();
    }

    @Test
    void testRollAllStrikes() {
        int total = 0;
        try {
            for (int i = 0; i < 12; i++) {
                total = calculator.roll(10, gameNAme);
            }
            assertEquals(300, total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRollAreZeros() {
        int total = 0;
        try {
            for (int i = 0; i < 21; i++) {
                total = calculator.roll(0, gameNAme);
            }
            assertEquals(0, total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testANegative() {
        int total = 0;
        assertEquals(0,calculator.roll(-1,gameNAme));


    }

    @Test
    void testRollAreOnes() {
        int total = 0;
        try {
            for (int i = 0; i < 21; i++) {
                total = calculator.roll(1, gameNAme);
            }
            assertEquals(20, total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRollAllOneWithSingleSpare() {
        int total = 0;
        try {
            for (int i = 0; i < 18; i++) {
                total = calculator.roll(1, gameNAme);
            }
            assertEquals(18, total);
            calculator.roll(1,gameNAme);
            calculator.roll(9,gameNAme);
            calculator.roll(1,gameNAme);
            assertEquals(29, total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testRamonRolls() {
        int total = 0;
        try {
            calculator.roll(10,gameNAme);
            calculator.roll(3,gameNAme);
            total = calculator.roll(2,gameNAme);
            assertEquals(20, total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
