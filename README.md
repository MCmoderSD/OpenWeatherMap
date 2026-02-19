# OpenWeatherMap

## Description
OpenWeatherMap is a Java library that provides an easy way to query weather data from the [OpenWeatherMap](https://openweathermap.org/) API. <br>
You need an API key from OpenWeatherMap to use this library. You can get one [here](https://home.openweathermap.org/users/sign_up).

## Features
Currently, it only uses the [Current Weather](https://openweathermap.org/current) Data API from OpenWeatherMap. <br>
You can query weather data by city name or by latitude and longitude.

It provides the following weather data: 
- Longitude
- Latitude
- City name
- Country code
- Timezone
- Sunrise
- Sunset
- Weather Title
- Weather description
- Temperature
- Temperature feels like
- Temperature min
- Temperature max
- Pressure
- Humidity
- Visibility
- Wind speed
- Wind direction
- Wind gust (if available)
- Cloudiness
- Rain volume for the last 1 hour (if available)
- Snow volume for the last 1 hour (if available)

## Usage

### Maven
Make sure you have my Sonatype Nexus OSS repository added to your `pom.xml` file:
```xml
<repositories>
    <repository>
        <id>Nexus</id>
        <name>Sonatype Nexus</name>
        <url>https://mcmodersd.de/nexus/repository/maven-releases/</url>
    </repository>
</repositories>
```
Add the dependency to your `pom.xml` file:
```xml
<dependency>
    <groupId>de.MCmoderSD</groupId>
    <artifactId>OpenWeatherMap</artifactId>
    <version>1.2.1</version>
</dependency>
```

### Usage Example
```java
import de.MCmoderSD.openweathermap.core.OpenWeatherMap;
import de.MCmoderSD.openweathermap.data.Weather;
import de.MCmoderSD.openweathermap.enums.SpeedUnit;
import de.MCmoderSD.openweathermap.enums.TempUnit;

void main() {

    // Variables
    String cityName = "Berlin";     // City name
    String apiKey = "YOUR_API_KEY"; // OpenWeatherMap API key

    double latitude = 52.5244f;     // Latitude
    double longitude = 13.4105f;    // Longitude

    // Initialize OpenWeatherMap
    OpenWeatherMap openWeatherMap = new OpenWeatherMap(apiKey);

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
    IO.println(formatWeatherData(weather));
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
```