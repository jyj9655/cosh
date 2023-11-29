package kr.cosh.module.weather.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class WeatherService {

    @Value("${external.api.key}")
    private String apiKey;

    public String getWeatherData(String apiKey, String baseDate, String baseTime, String nx, String ny) throws URISyntaxException {
        String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
        url += "?serviceKey=" + apiKey + "&dataType=JSON&base_date=" + baseDate;
        url += "&base_time=" + baseTime + "&nx=" + nx + "&ny=" + ny;

        URI uri = new URI(url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(uri, String.class);
    }
}