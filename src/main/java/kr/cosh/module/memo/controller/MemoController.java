package kr.cosh.module.memo.controller;

import kr.cosh.module.memo.model.Memo;
import kr.cosh.module.memo.service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memo")
public class MemoController {

    private final MemoService service;

    public MemoController(MemoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Memo>> getAllMemos() {
        return ResponseEntity.ok(service.getAllMemos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Memo> getMemo(@PathVariable Long id) {
        return service.getMemo(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Memo> createMemo(@RequestBody Memo memo) {
        return ResponseEntity.ok(service.createMemo(memo));
    }

    @PutMapping
    public ResponseEntity<Memo> updateMemo(@RequestBody Memo memo) {
        return ResponseEntity.ok(service.updateMemo(memo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMemo(@PathVariable Long id) {
        service.deleteMemo(id);
        return ResponseEntity.ok().build();
    }

}
