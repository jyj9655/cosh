package kr.cosh.module.event.dto;

import kr.cosh.module.event.model.Event;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class EventDTO implements Serializable {

    private Long id;
    private String title;
    private String content;
    private String name;
    private LocalDate date;

    public EventDTO(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.content = event.getContent();
        this.name = event.getName();
        this.date = event.getDate();
    }
}