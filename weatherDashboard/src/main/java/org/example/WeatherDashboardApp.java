package org.example;

import org.example.dto.OpenWeatherResponse;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class WeatherDashboardApp {

    public static void main(String[] args) {
        String apiKey = System.getenv("OPENWEATHER_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("Missing API key.");
            System.out.println("Set environment variable OPENWEATHER_API_KEY, then re-run.");
            return;
        }

        Map<Integer, String> cities = new LinkedHashMap<>();
        cities.put(1, "Chicago");
        cities.put(2, "New York");
        cities.put(3, "Los Angeles");

        WeatherService weatherService = new WeatherService(apiKey);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== Morning Routine Weather Dashboard ===");
                System.out.println("Choose a city:");
                for (Map.Entry<Integer, String> entry : cities.entrySet()) {
                    System.out.printf("%d) %s%n", entry.getKey(), entry.getValue());
                }
                System.out.println("0) Exit");
                System.out.print("Your choice: ");

                String input = scanner.nextLine().trim();
                if (input.equals("0")) {
                    System.out.println("Goodbye 👋");
                    break;
                }

                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a number from the menu.");
                    continue;
                }

                String city = cities.get(choice);
                if (city == null) {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }

                try {
                    OpenWeatherResponse data = weatherService.getCurrentWeather(city);
                    printDashboard(data);
                } catch (WeatherService.CityNotFoundException e) {
                    System.out.println("City not found. Try a different city name.");
                } catch (WeatherService.UnauthorizedException e) {
                    System.out.println("Unauthorized (401). Check your OPENWEATHER_API_KEY.");
                    System.out.println("Also note: new keys can take a little time to activate after signup.");
                } catch (Exception e) {
                    System.out.println("Something went wrong: " + e.getMessage());
                }
            }
        }
    }

    private static void printDashboard(OpenWeatherResponse data) {
        String cityName = data.getName();
        double tempF = data.getMain().getTemp();
        int humidity = data.getMain().getHumidity();
        String description = (data.getWeather() != null && !data.getWeather().isEmpty())
                ? data.getWeather().get(0).getDescription()
                : "N/A";

        System.out.println("\n--- Current Weather ---");
        System.out.println("City: " + cityName);
        System.out.printf("Temperature: %.1f°F%n", tempF);
        System.out.println("Description: " + capitalize(description));
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("-----------------------");
    }

    private static String capitalize(String s) {
        if (s == null || s.isBlank()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
