package de.MCmoderSD.openweathermap.data;

import de.MCmoderSD.openweathermap.enums.SpeedUnit;
import de.MCmoderSD.openweathermap.enums.TempUnit;
import tools.jackson.databind.JsonNode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.TimeZone;

import static de.MCmoderSD.openweathermap.enums.SpeedUnit.MPS;
import static de.MCmoderSD.openweathermap.enums.TempUnit.KELVIN;

@SuppressWarnings("unused")
public class Weather implements Serializable {

    // Raw Data
    private final JsonNode data;

    // Coordinates
    private final BigDecimal longitude;
    private final BigDecimal latitude;

    // Location
    private final String city;
    private final String country;

    // Timezone
    private final TimeZone timezone;

    // Sunrise and Sunset
    private final long sunrise; // UTC
    private final long sunset;  // UTC

    // Weather
    private final String title;
    private final String description;

    // Weather Data
    private final BigDecimal temperature;
    private final BigDecimal feelsLike;
    private final BigDecimal tempMin;
    private final BigDecimal tempMax;
    private final int pressure;
    private final int humidity;

    // Visibility
    private final int visibility;

    // Wind Data
    private final BigDecimal windSpeed;
    private final int windDirection;
    private final BigDecimal windGust;

    // Other Data
    private final int cloudiness;
    private final BigDecimal rain;
    private final BigDecimal snow;

    // Constructor
    public Weather(JsonNode data) {

        // Check Data
        if (data == null) throw new IllegalArgumentException("Data cannot be null.");

        // Set Raw Data
        this.data = data;

        // Parse Cords
        var coord = data.get("coord");
        this.longitude = coord.get("lon").asDecimal();
        this.latitude = coord.get("lat").asDecimal();

        // Parse Location
        var sys = data.get("sys");
        this.city = data.get("name").asString();
        this.country = sys.get("country").asString();

        // Parse Timezone
        var offset = data.get("timezone").asInt();
        var zoneId = ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(offset));
        this.timezone = TimeZone.getTimeZone(zoneId);

        // Parse Sunrise and Sunset
        this.sunrise = sys.get("sunrise").asLong();
        this.sunset = sys.get("sunset").asLong();

        // Extract Weather
        var weather = data.get("weather").get(0);
        this.title = weather.get("main").asString();
        this.description = weather.get("description").asString();

        // Extract Weather Data
        var main = data.get("main");
        this.temperature = main.get("temp").asDecimal();
        this.feelsLike = main.get("feels_like").asDecimal();
        this.pressure = main.get("pressure").asInt();
        this.humidity = main.get("humidity").asInt();
        this.tempMin = main.get("temp_min").asDecimal();
        this.tempMax = main.get("temp_max").asDecimal();

        // Parse Visibility
        this.visibility =   data.get("visibility").asInt();

        // Parse Wind Data
        var wind = data.get("wind");
        this.windSpeed = wind.get("speed").asDecimal();
        this.windDirection = wind.get("deg").asInt();
        this.windGust = wind.has("gust") ? wind.get("gust").asDecimal() : null;

        // Parse Cloud Data
        this.cloudiness = data.get("clouds").get("all").asInt();

        // Parse Rain (optional)
        this.rain = data.has("rain") ? data.get("rain").get("1h").asDecimal() : null;

        // Parse Snow (optional)
        this.snow = data.has("snow") ? data.get("snow").get("1h").asDecimal() : null;
    }

    // Getters
    public JsonNode getData() {
        return data;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public Instant getSunrise() {
        return Instant.ofEpochSecond(sunrise).atZone(timezone.toZoneId()).toInstant();
    }

    public Instant getSunset() {
        return Instant.ofEpochSecond(sunset).atZone(timezone.toZoneId()).toInstant();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getTemperature(TempUnit unit) {
        return unit.convert(temperature, KELVIN);
    }

    public BigDecimal getFeelsLike(TempUnit unit) {
        return unit.convert(feelsLike, KELVIN);
    }

    public BigDecimal getTempMin(TempUnit unit) {
        return unit.convert(tempMin, KELVIN);
    }

    public BigDecimal getTempMax(TempUnit unit) {
        return unit.convert(tempMax, KELVIN);
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getVisibility() {
        return visibility;
    }

    public BigDecimal getWindSpeed(SpeedUnit unit) {
        return unit.convert(windSpeed, MPS);
    }

    public int getWindDirection() {
        return windDirection;
    }

    public Optional<BigDecimal> getWindGust() {
        return Optional.ofNullable(windGust);
    }

    public int getCloudiness() {
        return cloudiness;
    }

    public Optional<BigDecimal> getRain() {
        return Optional.ofNullable(rain);
    }

    public Optional<BigDecimal> getSnow() {
        return Optional.ofNullable(snow);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == getClass() && hashCode() == obj.hashCode();
    }
}