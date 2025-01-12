package de.MCmoderSD.openweathermap.data;

import com.fasterxml.jackson.databind.JsonNode;
import de.MCmoderSD.openweathermap.enums.SpeedUnit;
import de.MCmoderSD.openweathermap.enums.TempUnit;
import de.MCmoderSD.openweathermap.enums.TimeFormat;

import java.text.SimpleDateFormat;

@SuppressWarnings("unused")
public class Weather {

    // Attributes
    private final float longitude;
    private final float latitude;
    private final String city;
    private final String country;
    private final String weather;
    private final String description;
    private final float temperature;
    private final float feelsLike;
    private final int pressure;
    private final int humidity;
    private final float windSpeed;
    private final float cloudiness;
    private final long sunrise;
    private final long sunset;

    // Constructor
    public Weather(float longitude, float latitude, String city, String country, String weather, String description, float temperature, float feelsLike, int pressure, int humidity, float windSpeed, float cloudiness, int sunrise, int sunset) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.city = city;
        this.country = country;
        this.weather = weather;
        this.description = description;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.cloudiness = cloudiness;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public Weather(JsonNode data) {

        // Extract data
        JsonNode coord = data.get("coord");
        JsonNode sys = data.get("sys");
        JsonNode weather = data.get("weather").get(0);
        JsonNode main = data.get("main");
        JsonNode wind = data.get("wind");
        JsonNode cloudiness = data.get("clouds");

        // Get coordinates
        longitude = coord.get("lon").floatValue();
        latitude = coord.get("lat").floatValue();

        // Get city and country
        city = data.get("name").asText();
        country = sys.get("country").asText();

        // Get weather
        this.weather = weather.get("main").asText();
        description = weather.get("description").asText();

        // Get main data
        temperature = main.get("temp").floatValue();
        feelsLike = main.get("feels_like").floatValue();
        pressure = main.get("pressure").intValue();
        humidity = main.get("humidity").intValue();

        // Get wind data
        windSpeed = wind.get("speed").floatValue();

        // Get cloudiness
        this.cloudiness = cloudiness.get("all").floatValue();

        // Get sunrise and sunset
        sunrise = sys.get("sunrise").asLong();
        sunset = sys.get("sunset").asLong();
    }

    // Getters
    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getWeather() {
        return weather;
    }

    public String getDescription() {
        return description;
    }

    public float getTemperature(TempUnit unit) {
        return unit.convert(temperature, TempUnit.KELVIN);
    }

    public float getFeelsLike(TempUnit unit) {
        return unit.convert(feelsLike, TempUnit.KELVIN);
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public float getWindSpeed(SpeedUnit unit) {
        return unit.convert(windSpeed, SpeedUnit.MPS);
    }

    public float getCloudiness() {
        return cloudiness;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public String getSunrise(TimeFormat format) {
        return new SimpleDateFormat(format.getPattern()).format(sunrise * 1000L);
    }

    public String getSunset(TimeFormat format) {
        return new SimpleDateFormat(format.getPattern()).format(sunset * 1000L);
    }
}