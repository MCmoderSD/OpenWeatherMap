package de.MCmoderSD.openweathermap.enums;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum SpeedUnit {

    // Constants
    MPS(),
    KPH(),
    MPH();

    // Methods
    public String getUnit() {
        return switch (this) {
            case MPS -> "m/s";
            case KPH -> "km/h";
            case MPH -> "mph";
        };
    }

    public float convert(float speed, SpeedUnit unit) {
        if (unit == this) return speed;
        switch (unit) {
            case MPS -> {
                if (this == KPH) return round(speed * 3.6f, 2);
                if (this == MPH) return round(speed * 2.23694f, 2);
            }
            case KPH -> {
                if (this == MPS) return round(speed / 3.6f, 2);
                if (this == MPH) return round(speed / 1.60934f, 2);
            }
            case MPH -> {
                if (this == MPS) return round(speed / 2.23694f, 2);
                if (this == KPH) return round(speed * 1.60934f, 2);
            }
        }
        throw new IllegalArgumentException("Invalid speed unit.");
    }

    private static float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
}