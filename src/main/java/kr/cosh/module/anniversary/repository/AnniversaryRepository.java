package kr.cosh.module.anniversary.repository;

import kr.cosh.module.anniversary.model.Anniversary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnniversaryRepository extends JpaRepository<Anniversary, Long> {
    // 필요한 쿼리 메소드 추가
}