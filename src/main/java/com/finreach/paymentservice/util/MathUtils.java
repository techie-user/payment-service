package com.finreach.paymentservice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class helps in utility functions for the application
 * Marked final, so as to avoid sub-class extension
 *
 * @author Mohamed.Shabeer
 */
public final class MathUtils {

    private MathUtils() {
        //Ensuring static class
    }

    public static Double round(Double d) {
        try {
            BigDecimal bd = new BigDecimal(Double.toString(d));
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue();
        } catch (NumberFormatException e) {
            return 0d;
        }
    }

    public static String getCurrentTimeInNanoSeconds() {
        return String.valueOf(System.nanoTime());
    }
}
