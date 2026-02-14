package de.MCmoderSD.openweathermap.enums;

import java.math.BigDecimal;

import static de.MCmoderSD.openweathermap.utils.ConversionHelper.*;

@SuppressWarnings("unused")
public enum SpeedUnit {

    // Constants
    MPS, KPH, MPH;

    // Get Unit Symbol
    public String getUnit() {
        return switch (this) {
            case MPS -> "m/s";
            case KPH -> "km/h";
            case MPH -> "mph";
        };
    }

    // Convert Speed
    public BigDecimal convert(BigDecimal speed, SpeedUnit unit) {

        // Check Parameters
        if (speed == null) throw new IllegalArgumentException("Speed cannot be null.");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null.");
        if (unit == this) return speed;

        // Convert Speed
        switch (unit) {
            case MPS -> {
                if (this == KPH) return speed.multiply(BD_3_6, MC);
                if (this == MPH) return speed.multiply(BD_2_2369362920544, MC);
            }
            case KPH -> {
                if (this == MPS) return speed.divide(BD_3_6, MC);
                if (this == MPH) return speed.divide(BD_1_609344, MC);
            }
            case MPH -> {
                if (this == MPS) return speed.multiply(BD_1_609344, MC).divide(BD_3_6, MC);
                if (this == KPH) return speed.multiply(BD_1_609344, MC);
            }
        }
        throw new IllegalArgumentException("Invalid speed unit.");
    }
}