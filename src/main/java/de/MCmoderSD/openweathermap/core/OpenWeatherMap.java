package de.MCmoderSD.openweathermap.core;

import com.fasterxml.jackson.databind.json.JsonMapper;
import de.MCmoderSD.openweathermap.data.Weather;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * The OpenWeatherMap class provides methods to query weather data from the OpenWeatherMap API.
 */
@SuppressWarnings("unused")
public class OpenWeatherMap {

    // Constants
    private static final String ENDPOINT = "https://api.openweathermap.org/data/2.5/weather";

    // Attributes
    private final String apiKey;
    private final HttpClient httpClient;
    private final JsonMapper jsonMapper;

    /**
     * Constructs an OpenWeatherMap instance with the specified API key.
     *
     * @param apiKey the API key for accessing the OpenWeatherMap API
     */
    public OpenWeatherMap(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.jsonMapper = new JsonMapper();
    }

    /**
     * Queries weather data for a city.
     *
     * @param cityName the name of the city
     * @param apiKey   the API key for accessing the OpenWeatherMap API
     * @return the weather data for the specified city
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public static Weather query(String cityName, String apiKey) throws IOException, InterruptedException {
        return new OpenWeatherMap(apiKey).query(cityName);
    }

    /**
     * Queries weather data for a city.
     *
     * @param cityName the name of the city
     * @return the weather data for the specified city
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public Weather query(String cityName) throws IOException, InterruptedException {

        // Check Parameters
        if (cityName == null) throw new InvalidParameterException("City name cannot be null.");

        // Encode city name
        String encodedCityName = cityName.replace(" ", "+");

        // Construct the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT + "?q=" + encodedCityName + "&appid=" + apiKey))
                .GET()
                .build();

        // Send the request
        return sendRequest(request);
    }

    /**
     * Queries weather data for a location specified by latitude and longitude.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @param apiKey    the API key for accessing the OpenWeatherMap API
     * @return the weather data for the specified location
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public static Weather query(float latitude, float longitude, String apiKey) throws IOException, InterruptedException {
        return new OpenWeatherMap(apiKey).query(latitude, longitude);
    }

    /**
     * Queries weather data for a location specified by latitude and longitude.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     * @return the weather data for the specified location
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    public Weather query(float latitude, float longitude) throws IOException, InterruptedException {

        // Construct the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey))
                .GET()
                .build();

        // Send the request
        return sendRequest(request);
    }

    /**
     * Sends the HTTP request and returns the weather data.
     *
     * @param request the HTTP request to send
     * @return the weather data
     * @throws IOException          if an I/O error occurs
     * @throws InterruptedException if the operation is interrupted
     */
    private Weather sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IOException("Failed to retrieve weather data.");
        return new Weather(jsonMapper.readTree(response.body()));
    }

    /**
     * Returns the API key.
     *
     * @return the API key
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * Returns the HTTP client.
     *
     * @return the HTTP client
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Returns the JSON mapper.
     *
     * @return the JSON mapper
     */
    public JsonMapper getJsonMapper() {
        return jsonMapper;
    }

    /**
     * Returns the API endpoint.
     *
     * @return the API endpoint
     */
    public String getEndpoint() {
        return ENDPOINT;
    }
}