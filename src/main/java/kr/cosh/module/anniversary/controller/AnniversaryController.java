package kr.cosh.module.anniversary.controller;

import kr.cosh.module.anniversary.dto.AnniversaryDTO;
import kr.cosh.module.anniversary.model.Anniversary;
import kr.cosh.module.anniversary.service.AnniversaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/anniversarys")
public class AnniversaryController {

    private final AnniversaryService service;

    @Autowired
    public AnniversaryController(AnniversaryService anniversaryService) { this.service = anniversaryService; }

    @GetMapping
    public ResponseEntity<List<AnniversaryDTO>> getAllAnniversaries() {
        List<AnniversaryDTO> anniversaries = service.getAllAnniversaries();
        return ResponseEntity.ok(anniversaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anniversary> getMemo(@PathVariable Long id) {
        return service.getAnniversary(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}