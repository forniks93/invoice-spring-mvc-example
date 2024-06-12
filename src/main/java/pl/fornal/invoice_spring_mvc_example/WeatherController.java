package pl.fornal.invoice_spring_mvc_example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@RestController
public class WeatherController {
    @GetMapping("/weather")
    public JSONObject getWeather(@RequestParam String city){
        JSONObject weatherData = new JSONObject();

        JSONObject cityLocationData = getLocationData(city);
        if (cityLocationData == null) {
            weatherData.put("error", "Could not retrieve location data");
            return weatherData;
        }

        Double latitude = (Double) cityLocationData.get("latitude");
        Double longitude = (Double) cityLocationData.get("longitude");

        if (latitude == null || longitude == null) {
            weatherData.put("error", "Invalid location data received");
            return weatherData;
        }

        // Tutaj wywołaj metodę displayWeatherData z podanymi współrzędnymi
        JSONObject weatherDetails = displayWeatherData(latitude, longitude);
        weatherData.put("weather", weatherDetails);

        return weatherData;
    }
    private static JSONObject getLocationData(String city) {
        city = city.replace(" ", "+");

        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";
        try {
            HttpURLConnection apiConnection = fetchApiResponse(urlString);

            if (apiConnection == null || apiConnection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            }

            String jsonResponse = readApiResponse(apiConnection);
            if (jsonResponse == null) {
                System.out.println("Error: Could not read API response");
                return null;
            }

            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObj = (JSONObject) parser.parse(jsonResponse);

            JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
            return locationData != null && !locationData.isEmpty() ? (JSONObject) locationData.get(0) : null;

        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONObject displayWeatherData (double latitude, double longitude) {
        JSONObject currentWeatherJson = null;
        try {
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";
            HttpURLConnection apiConnection = fetchApiResponse(url);

            if (apiConnection.getResponseCode() != 200) {
                System.out.println("Error: Could not connect to API");
                return null;
            }

            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            currentWeatherJson = (JSONObject) jsonObject.get("current");

            String time = (String) currentWeatherJson.get("time");
            System.out.println("Current Time: " + time);

            double temperature = (double) currentWeatherJson.get("temperature_2m");
            System.out.println("Current temperature: " + temperature);

            long relativeHumidity = (long) currentWeatherJson.get("relative_humidity_2m");
            System.out.println("Relative Humidity: " + relativeHumidity);

            double windSpeed = (double) currentWeatherJson.get("wind_speed_10m");
            System.out.println("Weather description: " + windSpeed);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentWeatherJson;
    }

    private static HttpURLConnection fetchApiResponse(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String readApiResponse(HttpURLConnection apiConnection) {
        try (Scanner scanner = new Scanner(apiConnection.getInputStream())) {
            StringBuilder resultJson = new StringBuilder();
            while (scanner.hasNext()) {
                resultJson.append(scanner.nextLine());
            }
            return resultJson.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
