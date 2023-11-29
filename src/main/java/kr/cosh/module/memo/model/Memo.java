package kr.cosh.module.memo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String content;
    private int state;
    private LocalDate date;

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now(); // 현재 날짜로 설정
    }
}
