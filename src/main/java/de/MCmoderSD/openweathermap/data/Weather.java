package de.MCmoderSD.openweathermap.data;

import com.fasterxml.jackson.databind.JsonNode;
import de.MCmoderSD.openweathermap.enums.SpeedUnit;
import de.MCmoderSD.openweathermap.enums.TempUnit;
import de.MCmoderSD.openweathermap.enums.TimeFormat;

import java.text.SimpleDateFormat;

/**
 * The Weather class represents weather data retrieved from the OpenWeatherMap API.
 */
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

    /**
     * Constructs a Weather instance with the specified parameters.
     *
     * @param longitude   the longitude of the location
     * @param latitude    the latitude of the location
     * @param city        the name of the city
     * @param country     the country code
     * @param weather     the main weather condition
     * @param description the weather description
     * @param temperature the temperature in Kelvin
     * @param feelsLike   the perceived temperature in Kelvin
     * @param pressure    the atmospheric pressure in hPa
     * @param humidity    the humidity percentage
     * @param windSpeed   the wind speed in meters per second
     * @param cloudiness  the cloudiness percentage
     * @param sunrise     the sunrise time in Unix time
     * @param sunset      the sunset time in Unix time
     */
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

    /**
     * Constructs a Weather instance from a JsonNode.
     *
     * @param data the JsonNode containing weather data
     */
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

    /**
     * Returns the longitude of the location.
     *
     * @return the longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Returns the latitude of the location.
     *
     * @return the latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Returns the name of the city.
     *
     * @return the city name
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the country code.
     *
     * @return the country code
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the main weather condition.
     *
     * @return the main weather condition
     */
    public String getWeather() {
        return weather;
    }

    /**
     * Returns the weather description.
     *
     * @return the weather description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the temperature in the specified unit.
     *
     * @param unit the temperature unit
     * @return the temperature in the specified unit
     */
    public float getTemperature(TempUnit unit) {
        return unit.convert(temperature, TempUnit.KELVIN);
    }

    /**
     * Returns the perceived temperature in the specified unit.
     *
     * @param unit the temperature unit
     * @return the perceived temperature in the specified unit
     */
    public float getFeelsLike(TempUnit unit) {
        return unit.convert(feelsLike, TempUnit.KELVIN);
    }

    /**
     * Returns the atmospheric pressure in hPa.
     *
     * @return the atmospheric pressure
     */
    public int getPressure() {
        return pressure;
    }

    /**
     * Returns the humidity percentage.
     *
     * @return the humidity percentage
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * Returns the wind speed in the specified unit.
     *
     * @param unit the speed unit
     * @return the wind speed in the specified unit
     */
    public float getWindSpeed(SpeedUnit unit) {
        return unit.convert(windSpeed, SpeedUnit.MPS);
    }

    /**
     * Returns the cloudiness percentage.
     *
     * @return the cloudiness percentage
     */
    public float getCloudiness() {
        return cloudiness;
    }

    /**
     * Returns the sunrise time in Unix time.
     *
     * @return the sunrise time
     */
    public long getSunrise() {
        return sunrise;
    }

    /**
     * Returns the sunset time in Unix time.
     *
     * @return the sunset time
     */
    public long getSunset() {
        return sunset;
    }

    /**
     * Returns the formatted sunrise time.
     *
     * @param format the time format
     * @return the formatted sunrise time
     */
    public String getSunrise(TimeFormat format) {
        return new SimpleDateFormat(format.getPattern()).format(sunrise * 1000L);
    }

    /**
     * Returns the formatted sunset time.
     *
     * @param format the time format
     * @return the formatted sunset time
     */
    public String getSunset(TimeFormat format) {
        return new SimpleDateFormat(format.getPattern()).format(sunset * 1000L);
    }
}