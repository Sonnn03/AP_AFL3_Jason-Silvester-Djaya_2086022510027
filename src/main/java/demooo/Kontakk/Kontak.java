package demooo.Kontakk;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "kontak")
public class Kontak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nama;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String pesan;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ========== Constructor ==========
    public Kontak() {}

    public Kontak(String nama, String email, String pesan) {
        this.nama  = nama;
        this.email = email;
        this.pesan = pesan;
    }

    // ========== Getter & Setter ==========
    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }

    public String getNama()                   { return nama; }
    public void setNama(String nama)          { this.nama = nama; }

    public String getEmail()                  { return email; }
    public void setEmail(String email)        { this.email = email; }

    public String getPesan()                  { return pesan; }
    public void setPesan(String pesan)        { this.pesan = pesan; }

    public LocalDateTime getCreatedAt()       { return createdAt; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }

    public LocalDateTime getUpdatedAt()       { return updatedAt; }
    public void setUpdatedAt(LocalDateTime t) { this.updatedAt = t; }

    // Helper untuk Thymeleaf
    public String getFormattedCreatedAt() {
        if (createdAt == null) return "-";
        return createdAt.format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm"));
    }
}