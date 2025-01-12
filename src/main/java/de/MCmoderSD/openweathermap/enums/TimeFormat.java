package de.MCmoderSD.openweathermap.enums;

public enum TimeFormat {

    // Constants
    HH_MM_SS("HH:mm:ss");

    // Attributes
    private final String pattern;

    // Constructor
    TimeFormat(String pattern) {
        this.pattern = pattern;
    }

    // Methods
    public String getPattern() {
        return pattern;
    }
}
