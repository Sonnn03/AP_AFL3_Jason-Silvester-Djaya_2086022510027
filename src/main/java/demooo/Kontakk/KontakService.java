package demooo.Kontakk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KontakService {

    @Autowired
    private KontakRepository repository;

    //CREATE
    public Kontak simpan(Kontak kontak) {
        return repository.save(kontak);
    }

    //READ (terbaru dulu)
    public List<Kontak> ambilSemua() {
        return repository.findAllByOrderByCreatedAtDesc();
    }

    //READ ID
    public Optional<Kontak> cariById(Long id) {
        return repository.findById(id);
    }

    //=READ keyword
    public List<Kontak> cariByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) return ambilSemua();
        return repository.cariByKeyword(keyword.trim());
    }

    //UPDATE
    public Kontak update(Long id, Kontak dataBaru) {
        Kontak existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Kontak ID " + id + " tidak ditemukan"));

        existing.setNama(dataBaru.getNama());
        existing.setEmail(dataBaru.getEmail());
        existing.setPesan(dataBaru.getPesan());
        return repository.save(existing);
    }

    //DELETE
    public void hapus(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Kontak ID " + id + " tidak ditemukan");
        }
        repository.deleteById(id);
    }

    //COUNT
    public long total() {
        return repository.count();
    }
}