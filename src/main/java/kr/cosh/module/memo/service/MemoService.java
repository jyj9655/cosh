package kr.cosh.module.memo.service;

import kr.cosh.module.memo.model.Memo;
import kr.cosh.module.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemoService {

    private final MemoRepository repository;

    public MemoService(MemoRepository repository) {
        this.repository = repository;
    }

    public List<Memo> getAllMemos() {
        return repository.findAll();
    }

    public Optional<Memo> getMemo(Long id) {
        return repository.findById(id);
    }

    public Memo createMemo(Memo memo) {
        return repository.save(memo);
    }

    public Memo updateMemo(Memo memo) {
        return repository.save(memo);
    }

    public void deleteMemo(Long id) {
        repository.deleteById(id);
    }
}
