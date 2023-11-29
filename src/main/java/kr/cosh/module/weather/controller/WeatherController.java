package kr.cosh.module.weather.controller;

import kr.cosh.module.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Value("${external.api.key}")
    private String apiKey;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/forecast")
    public String getForecast() throws URISyntaxException {
        // 여기에 API 키, 날짜, 시간, 위치 좌표를 적절하게 설정하세요.
        String apiKey = "your_api_key_here";
        String baseDate = "20230101"; // 예: '20230101'
        String baseTime = "0800"; // 예: '0800'
        String nx = "60";
        String ny = "127";
        return weatherService.getWeatherData(apiKey, baseDate, baseTime, nx, ny);
    }
}