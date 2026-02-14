package de.MCmoderSD.openweathermap.core;

import de.MCmoderSD.openweathermap.data.Weather;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.security.InvalidParameterException;

@SuppressWarnings("unused")
public class OpenWeatherMap {

    // Constants
    private static final String ENDPOINT = "https://api.openweathermap.org/data/2.5/weather";

    // Attributes
    private final String apiKey;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    // Constructor
    public OpenWeatherMap(String apiKey) {

        // Check Parameters
        if (apiKey == null || apiKey.isBlank()) throw new InvalidParameterException("API key cannot be null or blank.");

        // Set API Key
        this.apiKey = apiKey;

        // Initialize HTTP Client and Object Mapper
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    // Helper Method to Send Request and Parse Response
    private JsonNode sendRequest(HttpRequest request) {
        try {

            // Send the request
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Check response status
            if (response.statusCode() == 404) throw new IllegalArgumentException("City not found.");
            if (response.statusCode() != 200) throw new IOException("Unexpected response status: " + response.statusCode());

            // Parse and return JSON response
            return objectMapper.readTree(response.body());

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to retrieve weather data: " + e.getMessage(), e);
        }
    }

    // Query weather data by city name
    public static Weather query(String cityName, String apiKey) {
        return new OpenWeatherMap(apiKey).query(cityName);
    }

    // Query weather data by city name
    public Weather query(String cityName) {

        // Check Parameters
        if (cityName == null || cityName.isBlank())  throw new InvalidParameterException("City name cannot be null or blank.");

        // Encode city name
        String encodedCityName = cityName.replace(" ", "+");

        // Construct the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT + "?q=" + encodedCityName + "&appid=" + apiKey))
                .GET()
                .build();

        // Send the request
        return new Weather(sendRequest(request));
    }

    // Query weather data by latitude and longitude
    public static Weather query(double latitude, double longitude, String apiKey) {
        return new OpenWeatherMap(apiKey).query(latitude, longitude);
    }

    // Query weather data by latitude and longitude
    public Weather query(double latitude, double longitude) {

        if (latitude < -90 || latitude > 90) throw new InvalidParameterException("Latitude must be between -90 and 90.");
        if (longitude < -180 || longitude > 180) throw new InvalidParameterException("Longitude must be between -180 and 180.");

        // Construct the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey))
                .GET()
                .build();

        // Send the request
        return new Weather(sendRequest(request));
    }
}