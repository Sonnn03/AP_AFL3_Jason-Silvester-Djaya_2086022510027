package demooo.Kontakk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KontakRepository extends JpaRepository<Kontak, Long> {

    List<Kontak> findByNamaContainingIgnoreCase(String nama);

    List<Kontak> findByEmailContainingIgnoreCase(String email);

    @Query("SELECT k FROM Kontak k WHERE " +
           "LOWER(k.nama) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(k.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Kontak> cariByKeyword(@Param("keyword") String keyword);

    List<Kontak> findAllByOrderByCreatedAtDesc();
}