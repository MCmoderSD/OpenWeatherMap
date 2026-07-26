import de.MCmoderSD.openweathermap.core.OpenWeatherMap;
import de.MCmoderSD.openweathermap.data.Weather;
import de.MCmoderSD.openweathermap.enums.SpeedUnit;
import de.MCmoderSD.openweathermap.enums.TempUnit;

import static java.lang.IO.println;

void main() {

    // Variables
    var cityName = "Berlin";     // City name
    var apiKey = "YOUR_API_KEY"; // OpenWeatherMap API key

    var latitude = 52.5244d;     // Latitude
    var longitude = 13.4105d;    // Longitude

    // Initialize OpenWeatherMap
    var openWeatherMap = new OpenWeatherMap(apiKey);

    // Query weather data
    Weather weather = null;
    try {

        // Query weather data using instance method
        weather = openWeatherMap.query(cityName);

        // Optional: Query weather data using static method and latitude/longitude
        weather = OpenWeatherMap.query(latitude, longitude, apiKey);

    } catch (IllegalArgumentException e) {
        System.err.println("Failed to retrieve weather data: " + e.getMessage());
    }

    // Print weather data
    assert weather != null;
    println(formatWeatherData(weather));
}

private static String formatWeatherData(Weather weather) {
    return String.format(
            "Weather in %s: %s, %s°C, %s%% humidity, %s hPa pressure, %s km/h wind speed, %s%% cloudiness, sunrise at %s, sunset at %s",
            weather.getCity(),
            weather.getDescription(),
            weather.getTemperature(TempUnit.CELSIUS),
            weather.getHumidity(),
            weather.getPressure(),
            weather.getWindSpeed(SpeedUnit.KPH),
            weather.getCloudiness(),
            weather.getSunrise().toString(),
            weather.getSunset().toString()
    );
}