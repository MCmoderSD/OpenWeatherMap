# OpenWeatherMap
[![](https://jitpack.io/v/MCmoderSD/OpenWeatherMap.svg)](https://jitpack.io/#MCmoderSD/OpenWeatherMap)

## Description
OpenWeatherMap is a Java library that provides an easy way to query weather data from the [OpenWeatherMap](https://openweathermap.org/) API. <br>
You need an API key from OpenWeatherMap to use this library. You can get one [here](https://home.openweathermap.org/users/sign_up).

## Features
Currently, it only uses the [Current Weather](https://openweathermap.org/current) Data API from OpenWeatherMap. <br>
You can query weather data by city name or by latitude and longitude. <br>
It provides the following weather data: 
- City name
- Weather description
- Temperature
- Humidity
- Pressure
- Wind speed
- Cloudiness
- Sunrise
- Sunset
- Country code
- Longitude
- Latitude

## Usage

### Maven
Make sure you have the JitPack repository added to your `pom.xml` file:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add the dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>com.github.MCmoderSD</groupId>
    <artifactId>OpenWeatherMap</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Usage Example
```java
import de.MCmoderSD.openweathermap.core.OpenWeatherMap;
import de.MCmoderSD.openweathermap.data.Weather;
import de.MCmoderSD.openweathermap.enums.SpeedUnit;
import de.MCmoderSD.openweathermap.enums.TempUnit;
import de.MCmoderSD.openweathermap.enums.TimeFormat;

public class Main {

    public static void main(String[] args) {

        // Variables
        String cityName = "Berlin"; // City name
        String apiKey = "";

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
```