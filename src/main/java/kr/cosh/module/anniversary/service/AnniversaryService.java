package kr.cosh.module.anniversary.service;

import kr.cosh.module.anniversary.dto.AnniversaryDTO;
import kr.cosh.module.anniversary.model.Anniversary;
import kr.cosh.module.anniversary.repository.AnniversaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnniversaryService {
    private final AnniversaryRepository repository;

    @Autowired
    public AnniversaryService(AnniversaryRepository anniversaryRepository) { this.repository = anniversaryRepository; }

    public List<AnniversaryDTO> getAllAnniversaries() {
        LocalDate today = LocalDate.now();
        return repository.findAll().stream().map(anniversary -> {
            long daysBetween = ChronoUnit.DAYS.between(anniversary.getDate(), today);
            return new AnniversaryDTO(anniversary, daysBetween);
        }).collect(Collectors.toList());
    }

    public Optional<Anniversary> getAnniversary(Long id) {
        return repository.findById(id);
    }

    public Anniversary createAnniversary(Anniversary anniversary) {
        return repository.save(anniversary);
    }

    public Anniversary updateAnniversary(Anniversary anniversary) {
        return repository.save(anniversary);
    }

    public void deleteAnniversary(Long id) {
        repository.deleteById(id);
    }
}