package kr.cosh.module.holiday.service;

import kr.cosh.module.holiday.model.Holiday;
import kr.cosh.module.holiday.repository.HolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HolidayService {

    private static final Logger logger = LoggerFactory.getLogger(HolidayService.class);

    private final HolidayRepository holidayRepository;

    @Value("${external.api.key}")
    private String apiKey;

    public HolidayService(HolidayRepository holidayRepository) {
        this.holidayRepository = holidayRepository;
    }

    @Scheduled(cron = "0 0 0 1 * ?") // 매월 1일 00:00:00에 실행
    public void scheduledSearchHolidayData() throws URISyntaxException {
        searchHolidayData();
    }

    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    public void searchHolidayData() throws URISyntaxException {
        int currentYear = Year.now().getValue();
        String url = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getHoliDeInfo";
        url += "?serviceKey=" + apiKey + "&solYear=" + currentYear;
        url += "&numOfRows=100" + "&_type=json";

        URI uri = new URI(url);
        System.out.println(uri);
        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> response = restTemplate.getForObject(uri, Map.class);
            logger.info("API Response: {}", response); // API 응답 로그
            List<Map<String, Object>> holidaysData = extractHolidaysFromResponse(response);
            logger.info("Extracted Holidays Data: {}", holidaysData); // 추출된 데이터 로그
            if (holidaysData != null) {
                for (Map<String, Object> holidayData : holidaysData) {
                    logger.info("Processing Holiday Data: {}", holidayData); // 처리 중인 데이터 로그
                    Holiday holiday = parseHolidayData(holidayData);
                    logger.info("Parsed Holiday: {}", holiday); // 파싱된 데이터 로그
                    saveHolidayIfNotExists(holiday);
                }
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching and storing holiday data", e);
        }
    }

    private List<Map<String, Object>> extractHolidaysFromResponse(Map<String, Object> response) {
        Map<String, Object> responseBody = (Map<String, Object>) ((Map<String, Object>)response.get("response")).get("body");
        if (responseBody != null) {
            Map<String, Object> items = (Map<String, Object>) responseBody.get("items");
            if (items != null) {
                return (List<Map<String, Object>>) items.get("item");
            }
        }
        return new ArrayList<>();
    }

    private Holiday parseHolidayData(Map<String, Object> holidayData) {
        Holiday holiday = new Holiday();
        holiday.setDateName((String) holidayData.get("dateName"));
        holiday.setDate(convertIntToDate((Integer) holidayData.get("locdate")));
        holiday.setDesc("공휴일");
        return holiday;
    }

    private LocalDate convertIntToDate(int date) {
        String dateString = String.valueOf(date);
        return LocalDate.parse(dateString, DateTimeFormatter.BASIC_ISO_DATE);
    }

    private void saveHolidayIfNotExists(Holiday holiday) {
        logger.info("Checking for existing holiday: {}", holiday); // 중복 체크 전 로그
        List<Holiday> existingHolidays = holidayRepository.findByDateAndDateName(holiday.getDate(), holiday.getDateName());
        logger.info("Existing Holidays: {}", existingHolidays); // 중복 체크 로그
        if (existingHolidays.isEmpty()) {
            holidayRepository.save(holiday);
            logger.info("Saved Holiday: {}", holiday); // 저장 로그
        } else {
            logger.info("Holiday already exists: {}", holiday); // 중복된 경우 로그
        }
    }
}