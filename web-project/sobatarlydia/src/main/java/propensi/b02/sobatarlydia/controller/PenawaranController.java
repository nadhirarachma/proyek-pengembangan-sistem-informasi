package propensi.b02.sobatarlydia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.b02.sobatarlydia.dto.PenawaranDto;
import propensi.b02.sobatarlydia.model.PenawaranModel;
import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.service.PenawaranService;
import propensi.b02.sobatarlydia.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/penawaran")

public class PenawaranController {

    @Autowired
    PenawaranService penawaranService;

    @Autowired
    UserService userService;

    @GetMapping("/daftarkan-penawaran")
    private String daftarkanPenawaranForm(Model model){
        PenawaranModel penawaran = new PenawaranModel();
        model.addAttribute("penawaran", penawaran);
        return "form-add-penawaran";
    }

    @PostMapping(value = "/daftarkan-penawaran")
    private String daftarkanPenawaranSubmit(@ModelAttribute PenawaranModel penawaran, Model model, Principal principal){

        PenggunaModel akun = userService.getAkunByEmail(principal.getName());
        penawaran.setDistributor(akun);
        penawaran.setTanggal(LocalDate.now());
        penawaran.setStatusPenawaran("Menunggu");
        penawaranService.addPenawaran(penawaran);

        model.addAttribute("penawaran", penawaran);
        model.addAttribute("statMsg", 1);
        return "form-add-penawaran";
    }

    @GetMapping("/viewall")
    private String viewAllPenawaran(Model model, Principal principal) {
        List<PenawaranModel> listPenawaran = penawaranService.getAllPenawaran();
        List<PenawaranDto> listBaru = new ArrayList<>();

        for (PenawaranModel penawaran: listPenawaran) {
            PenawaranDto pn = new PenawaranDto();
            pn.setNamaobat(penawaran.getNamaObat());
            pn.setJumlah(penawaran.getJumlahBox());
            pn.setNamaperusahaan(penawaran.getDistributor().getNamaPerusahaan());
            pn.setTanggalpenawaran(penawaran.getTanggal());
            pn.setStatuspenawaran(penawaran.getStatusPenawaran());
            pn.setHarga(penawaran.getHarga());
            pn.setTotalharga(penawaran.getHarga()* penawaran.getJumlahBox());
            pn.setId(penawaran.getId());
            listBaru.add(pn);
        }
        String role = userService.getAkunByEmail(principal.getName()).getRole();

        model.addAttribute("role", role);
        model.addAttribute("listPenawaran", listBaru);
        return "viewall-penawaran-obat";
    }
    @GetMapping("/diterima/{id}")
    public String statusDiterima(Model model, @PathVariable Long id) {
        PenawaranModel penawaran = penawaranService.getPenawaranById(id);

        if (penawaran==null) {
            return "redirect:/penawaran/viewall";
        }

        penawaranService.updateStatusPenawaran(penawaran,"Diterima");
        model.addAttribute("penawaran", penawaran);
        return "redirect:/penawaran/viewall";
    }
    @GetMapping("/ditolak/{id}")
    public String statusDitolak(Model model, @PathVariable long id) {
        PenawaranModel penawaran = penawaranService.getPenawaranById(id);

        if (penawaran==null) {
            return "redirect:/penawaran/viewall";
        }

        penawaranService.updateStatusPenawaran(penawaran,"Ditolak");
        model.addAttribute("penawaran", penawaran);
        return "redirect:/penawaran/viewall";
    }
}
