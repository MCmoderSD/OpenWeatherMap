package de.MCmoderSD.openweathermap.enums;

/**
 * Enum representing different time formats.
 */
public enum TimeFormat {

    // Constants
    HH_MM_SS("HH:mm:ss");

    // Attributes
    private final String pattern;

    // Constructor
    TimeFormat(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Returns the pattern of the time format.
     *
     * @return the pattern of the time format
     */
    public String getPattern() {
        return pattern;
    }
}