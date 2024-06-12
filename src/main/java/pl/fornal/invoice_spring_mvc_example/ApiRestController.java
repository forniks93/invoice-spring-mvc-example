package pl.fornal.invoice_spring_mvc_example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ApiRestController {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String city;

            while (true) {
                System.out.println("=================================");
                System.out.println("Enter City (type 'No' to quit): ");
                city = scanner.nextLine();

                if (city.equalsIgnoreCase("No")) break;

                JSONObject cityLocationData = getLocationData(city);
                if (cityLocationData == null) {
                    System.out.println("Error: Could not retrieve location data.");
                    continue;
                }

                // Sprawdzanie null przed rzutowaniem
                Double latitude = (Double) cityLocationData.get("latitude");
                Double longitude = (Double) cityLocationData.get("longitude");

                if (latitude == null || longitude == null) {
                    System.out.println("Error: Invalid location data received.");
                    continue;
                }

                displayWeatherData(latitude, longitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void displayWeatherData (double latitude, double longitude){
        try{
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" +latitude + "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";
            HttpURLConnection apiConnection = fetchApiResponse(url);

            if(apiConnection.getResponseCode() !=200){
                System.out.println("Error: Could not connect to API");
                return;
            }

            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse);
            JSONObject currentWeatherJson = (JSONObject) jsonObject.get("current");

            String time = (String) currentWeatherJson.get("time");
            System.out.println("Current Time: " + time);

            double temperature = (double) currentWeatherJson.get("temperature_2m");
            System.out.println("Current temperature: " + temperature);

            long relativeHumidity = (long) currentWeatherJson.get("relative_humidity_2m");
            System.out.println("Relative Humidity: " + relativeHumidity);

            double windSpeed = (double) currentWeatherJson.get("wind_speed_10m");
            System.out.println("Weather description: " + windSpeed) ;
        } catch (Exception e){
            e.printStackTrace();
        }
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
