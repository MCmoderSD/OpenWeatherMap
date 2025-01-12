import de.MCmoderSD.openweathermap.core.OpenWeatherMap;
import de.MCmoderSD.openweathermap.data.Weather;
import de.MCmoderSD.openweathermap.enums.SpeedUnit;
import de.MCmoderSD.openweathermap.enums.TempUnit;
import de.MCmoderSD.openweathermap.enums.TimeFormat;

public class Main {

    public static void main(String[] args) {

        // Variables
        String cityName = "Berlin"; // City name
        String apiKey = "YOUR_API_KEY"; // OpenWeatherMap API key

        float latitude = 52.5244f;  // Latitude
        float longitude = 13.4105f; // Longitude

        // Initialize OpenWeatherMap
        OpenWeatherMap openWeatherMap = new OpenWeatherMap(apiKey);

        // Query weather data
        Weather weather = null;
        try {

            // Query weather data using instance method
            weather = openWeatherMap.query(cityName);

            // Optional: Query weather data using static method and latitude/longitude
            weather = OpenWeatherMap.query(latitude, longitude, apiKey);

        } catch (Exception e) {
            System.err.println("Failed to retrieve weather data: " + e.getMessage());
        }

        // Print weather data
        assert weather != null;
        System.out.println(formatWeatherData(weather));
    }

    private static String formatWeatherData(Weather weather){
        return String.format(
                "Weather in %s: %s, %s°C, %s%% humidity, %s hPa pressure, %s km/h wind speed, %s%% cloudiness, sunrise at %s, sunset at %s",
                weather.getCity(),
                weather.getDescription(),
                weather.getTemperature(TempUnit.CELSIUS),
                weather.getHumidity(),
                weather.getPressure(),
                weather.getWindSpeed(SpeedUnit.KPH),
                weather.getCloudiness(),
                weather.getSunrise(TimeFormat.HH_MM_SS),
                weather.getSunset(TimeFormat.HH_MM_SS)
        );
    }
}