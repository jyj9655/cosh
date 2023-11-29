package kr.cosh.module.event.controller;

import kr.cosh.module.event.model.Event;
import kr.cosh.module.event.service.EventService;
import kr.cosh.module.memo.model.Memo;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService service;

    @Autowired
    public EventController(EventService eventService) {
        this.service = eventService;
    }

    @GetMapping
    public ResponseEntity<?> getCalendar(@RequestParam(required = false) Integer year,
                                         @RequestParam(required = false) Integer month) {
        LocalDate date = (year != null && month != null) ? LocalDate.of(year, month, 1) : LocalDate.now();
        Map<String, Object> calendarData = service.getCalendar(date);
        return ResponseEntity.ok(calendarData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id) {
        return service.getEvent(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        return ResponseEntity.ok(service.createEvent(event));
    }

    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        return ResponseEntity.ok(service.updateEvent(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id) {
        service.deleteEvent(id);
        return ResponseEntity.ok().build();
    }
}