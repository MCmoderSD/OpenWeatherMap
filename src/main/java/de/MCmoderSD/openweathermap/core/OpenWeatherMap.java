package de.MCmoderSD.openweathermap.core;

import com.fasterxml.jackson.databind.json.JsonMapper;
import de.MCmoderSD.openweathermap.data.Weather;

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
    private final JsonMapper jsonMapper;


    public OpenWeatherMap(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
        this.jsonMapper = new JsonMapper();
    }

    // Query weather data for a city
    public static Weather query(String cityName, String apiKey) throws IOException, InterruptedException {
        return new OpenWeatherMap(apiKey).query(cityName);
    }

    // Query weather data for a city
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

    public static Weather query(float latitude, float longitude, String apiKey) throws IOException, InterruptedException {
        return new OpenWeatherMap(apiKey).query(latitude, longitude);
    }

    // Query weather data for a location
    public Weather query(float latitude, float longitude) throws IOException, InterruptedException {

        // Construct the request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ENDPOINT + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey))
                .GET()
                .build();

        // Send the request
        return sendRequest(request);
    }

    // Send the HTTP request
    private Weather sendRequest(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) throw new IOException("Failed to retrieve weather data.");
        return new Weather(jsonMapper.readTree(response.body()));
    }

    // Getters
    public String getApiKey() {
        return apiKey;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public JsonMapper getJsonMapper() {
        return jsonMapper;
    }

    public String getEndpoint() {
        return ENDPOINT;
    }
}