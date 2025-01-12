package de.MCmoderSD.openweathermap.enums;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Enum representing different temperature units and providing methods for conversion.
 */
@SuppressWarnings("ALL")
public enum TempUnit {

    // Constants
    CELSIUS(),
    FAHRENHEIT(),
    KELVIN();

    /**
     * Returns the unit as a string.
     *
     * @return the unit as a string
     */
    public String getUnit() {
        return switch (this) {
            case CELSIUS -> "°C";
            case FAHRENHEIT -> "°F";
            case KELVIN -> "K";
        };
    }

    /**
     * Converts the given temperature to the specified unit.
     *
     * @param temperature the temperature to convert
     * @param unit        the unit to convert to
     * @return the converted temperature
     */
    public float convert(float temperature, TempUnit unit) {
        if (unit == this) return temperature;
        switch (unit) {
            case CELSIUS -> {
                if (this == FAHRENHEIT) return round((temperature * 9 / 5) + 32, 2);
                if (this == KELVIN) return round(temperature + 273.15f, 2);
            }
            case FAHRENHEIT -> {
                if (this == CELSIUS) return round((temperature - 32) * 5 / 9, 2);
                if (this == KELVIN) return round((temperature - 32) * 5 / 9 + 273.15f, 2);
            }
            case KELVIN -> {
                if (this == CELSIUS) return round(temperature - 273.15f, 2);
                if (this == FAHRENHEIT) return round((temperature - 273.15f) * 9 / 5 + 32, 2);
            }
        }
        throw new IllegalArgumentException("Invalid temperature unit.");
    }

    /**
     * Rounds the given value to the specified number of decimal places.
     *
     * @param value  the value to round
     * @param places the number of decimal places
     * @return the rounded value
     */
    private static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}