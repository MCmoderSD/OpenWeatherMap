package de.MCmoderSD.openweathermap.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@SuppressWarnings("unused")
public class ConversionHelper {

    // Constants
    public static final MathContext MC = MathContext.DECIMAL128;
    public static final BigDecimal BD_3_6 = BigDecimal.valueOf(3.6);
    public static final BigDecimal BD_1_609344 = BigDecimal.valueOf(1.609344);
    public static final BigDecimal BD_2_2369362920544 = new BigDecimal("2.2369362920544");
    public static final BigDecimal BD_5 = BigDecimal.valueOf(5);
    public static final BigDecimal BD_9 = BigDecimal.valueOf(9);
    public static final BigDecimal BD_32 = BigDecimal.valueOf(32);
    public static final BigDecimal BD_273_15 = BigDecimal.valueOf(273.15);

    // Rounding Method
    public static BigDecimal round(BigDecimal value, int places) {
        if (value == null) throw new IllegalArgumentException("Value cannot be null.");
        if (places < 0) throw new IllegalArgumentException("Places cannot be negative.");
        return value.setScale(places, RoundingMode.HALF_UP);
    }
}
