package kr.cosh.module.anniversary.dto;

import kr.cosh.module.anniversary.model.Anniversary;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AnniversaryDTO {
    private Long id;
    private String name;
    private LocalDate date;
    private String description;
    private long days;

    public AnniversaryDTO(Anniversary anniversary, long days) {
        this.id = anniversary.getId();
        this.name = anniversary.getName();
        this.date = anniversary.getDate();
        this.description = anniversary.getDescription();
        this.days = days;
    }
}