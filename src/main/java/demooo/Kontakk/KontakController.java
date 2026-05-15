package demooo.Kontakk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KontakController {

    @Autowired
    private KontakService service;
    
    @GetMapping({"/", "/homepage"})
    public String halamanHomepage() {
        return "page1(main)";
    }

    @GetMapping("/aboutme")
    public String halamanAboutme() {
        return "page2(about)";
    }

    @GetMapping("/gallery")
    public String halamanGallery() {
        return "page4(gallery)";
    }

    @GetMapping("/project")
    public String halamanProject() {
        return "page5(project)";
    }

    @GetMapping("/skills")
    public String halamanSkills() {
        return "page6(skills)";
    }
    
    @GetMapping("/contact")
    public String halamanKontak(Model model,
                                @RequestParam(required = false) String keyword) {
        // Form kosong untuk kirim pesan baru
        model.addAttribute("kontak", new Kontak());

        if (keyword != null && !keyword.isBlank()) {
            model.addAttribute("listKontak", service.cariByKeyword(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("listKontak", service.ambilSemua());
        }

        model.addAttribute("totalKontak", service.total());
        return "page3(contact)";
    }

    @PostMapping("/contact/kirim")
    public String kirimPesan(@ModelAttribute("kontak") Kontak kontak,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("listKontak", service.ambilSemua());
            model.addAttribute("totalKontak", service.total());
            model.addAttribute("errorKirim", true);
            return "page3(contact)";
        }

        service.simpan(kontak);
        redirectAttrs.addFlashAttribute("successMessage",
            "Pesan berhasil dikirim! Kami akan segera menghubungi kamu.");
        return "redirect:/contact";
    }

    @GetMapping("/contact/edit/{id}")
    public String formEdit(@PathVariable Long id, Model model,
                           RedirectAttributes redirectAttrs) {
        return service.cariById(id).map(k -> {
            model.addAttribute("kontakEdit", k);
            model.addAttribute("kontak", new Kontak());
            model.addAttribute("listKontak", service.ambilSemua());
            model.addAttribute("totalKontak", service.total());
            model.addAttribute("editMode", true);
            return "page3(contact)";
        }).orElseGet(() -> {
            redirectAttrs.addFlashAttribute("errorMessage", "Kontak tidak ditemukan.");
            return "redirect:/contact";
        });
    }

    @PostMapping("/contact/edit/{id}")
    public String simpanEdit(@PathVariable Long id,
                             @ModelAttribute("kontakEdit") Kontak dataBaru,
                             BindingResult result,
                             Model model,
                             RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            model.addAttribute("kontak", new Kontak());
            model.addAttribute("listKontak", service.ambilSemua());
            model.addAttribute("totalKontak", service.total());
            model.addAttribute("editMode", true);
            return "page3(contact)";
        }

        try {
            service.update(id, dataBaru);
            redirectAttrs.addFlashAttribute("successMessage", "Kontak berhasil diperbarui!");
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/contact";
    }

    @PostMapping("/contact/hapus/{id}")
    public String hapus(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            service.hapus(id);
            redirectAttrs.addFlashAttribute("successMessage", "Kontak berhasil dihapus.");
        } catch (RuntimeException e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/contact";
    }
}