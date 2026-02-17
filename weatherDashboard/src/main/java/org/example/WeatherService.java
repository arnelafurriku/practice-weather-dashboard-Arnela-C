package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.example.dto.OpenWeatherResponse;

public class WeatherService {

    private final String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    public OpenWeatherResponse getCurrentWeather(String city) {

        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", city)
                .queryParam("appid", apiKey)     // API key as appid :contentReference[oaicite:6]{index=6}
                .queryParam("units", "imperial") // Fahrenheit
                .toUriString();

        try {
            return restTemplate.getForObject(url, OpenWeatherResponse.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new UnauthorizedException();
            }
            throw e;
        }

    }

    public static class CityNotFoundException extends RuntimeException {}
    public static class UnauthorizedException extends RuntimeException {}
}
