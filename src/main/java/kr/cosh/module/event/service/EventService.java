package kr.cosh.module.event.service;

import kr.cosh.module.event.dto.EventDTO;
import kr.cosh.module.event.model.Event;
import kr.cosh.module.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class EventService {

    private final EventRepository repository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.repository = eventRepository;
    }

    public Map<String, Object> getCalendar(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        List<Event> monthlyEvents = repository.findByMonth(year, month);
        Map<LocalDate, List<EventDTO>> eventsByDate = new HashMap<>();
        monthlyEvents.forEach(event ->
                eventsByDate.computeIfAbsent(event.getDate(), k -> new ArrayList<>()).add(new EventDTO(event)));

        List<Map<String, Object>> days = new ArrayList<>();
        for (LocalDate day = startOfMonth; !day.isAfter(endOfMonth); day = day.plusDays(1)) {
            List<EventDTO> eventsForDay = eventsByDate.getOrDefault(day, new ArrayList<>());

            Map<String, Object> dayData = new HashMap<>();
            dayData.put("date", day);
            dayData.put("dayOfWeek", day.getDayOfWeek().toString());
            dayData.put("count", eventsForDay.size());
            dayData.put("list", eventsForDay);
            days.add(dayData);
        }

        Map<String, Object> calendarData = new HashMap<>();
        calendarData.put("year", year);
        calendarData.put("month", month);
        calendarData.put("days", days);

        return calendarData;
    }

    public Optional<Event> getEvent(Long id) {
        return repository.findById(id);
    }

    public Event createEvent(Event event) {
        return repository.save(event);
    }

    public Event updateEvent(Event event) {
        return repository.save(event);
    }

    public void deleteEvent(Long id) {
        repository.deleteById(id);
    }
}