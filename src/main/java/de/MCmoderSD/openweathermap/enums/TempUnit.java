package de.MCmoderSD.openweathermap.enums;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum TempUnit {

    // Constants
    CELSIUS(),
    FAHRENHEIT(),
    KELVIN();

    // Getters
    public String getUnit() {
        return switch (this) {
            case CELSIUS -> "°C";
            case FAHRENHEIT -> "°F";
            case KELVIN -> "K";
        };
    }

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

    private static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}