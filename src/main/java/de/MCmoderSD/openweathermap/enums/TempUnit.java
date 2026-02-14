package de.MCmoderSD.openweathermap.enums;

import java.math.BigDecimal;

import static de.MCmoderSD.openweathermap.utils.ConversionHelper.*;

@SuppressWarnings({"unused", "DuplicateExpressions"})
public enum TempUnit {

    // Constants
    CELSIUS, FAHRENHEIT, KELVIN;

    // Get Unit Symbol
    public String getUnit() {
        return switch (this) {
            case CELSIUS -> "°C";
            case FAHRENHEIT -> "°F";
            case KELVIN -> "K";
        };
    }

    // Convert Temperature
    public BigDecimal convert(BigDecimal temperature, TempUnit unit) {

        // Check Parameters
        if (temperature == null) throw new IllegalArgumentException("Temperature cannot be null.");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null.");
        if (unit == this) return temperature;

        // Convert Temperature
        switch (unit) {
            case CELSIUS -> {
                if (this == FAHRENHEIT) return temperature.multiply(BD_9, MC).divide(BD_5, MC).add(BD_32, MC);
                if (this == KELVIN) return temperature.add(BD_273_15, MC);
            }
            case FAHRENHEIT -> {
                if (this == CELSIUS) return temperature.subtract(BD_32, MC).multiply(BD_5, MC).divide(BD_9, MC);
                if (this == KELVIN) return temperature.subtract(BD_32, MC).multiply(BD_5, MC).divide(BD_9, MC).add(BD_273_15, MC);
            }
            case KELVIN -> {
                if (this == CELSIUS) return temperature.subtract(BD_273_15, MC);
                if (this == FAHRENHEIT) return temperature.subtract(BD_273_15, MC).multiply(BD_9, MC).divide(BD_5, MC).add(BD_32, MC);
            }
        }
        throw new IllegalArgumentException("Invalid temperature unit.");
    }
}