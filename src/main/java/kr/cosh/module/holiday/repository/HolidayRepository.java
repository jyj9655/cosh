package kr.cosh.module.holiday.repository;

import kr.cosh.module.holiday.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {
    List<Holiday> findByDateAndDateName(LocalDate date, String dateName);
}