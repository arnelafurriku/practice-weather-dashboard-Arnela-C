# practice-weather-dashboard-Arnela-C


# Morning Routine Weather Dashboard

A Java console application that fetches real-time weather data using the OpenWeatherMap API.

## Features
- View current weather for:
  - Chicago
  - New York
  - Los Angeles
- Displays:
  - Temperature (°F)
  - Weather description
  - Humidity
- Simple interactive console menu

## Technologies Used
- Java 17
- Maven
- Spring RestTemplate
- OpenWeatherMap API

## Setup Instructions

1. Create a free account at:
   https://openweathermap.org

2. Generate an API key.

3. Set your API key as an environment variable:

   Mac/Linux:
   ```bash
   export OPENWEATHER_API_KEY="your_api_key_here"
Navigate to the project folder:
cd weatherDashboard
run the project:
mvn -q exec:java -Dexec.mainClass="org.example.WeatherDashboardApp"
