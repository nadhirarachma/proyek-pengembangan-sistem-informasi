package propensi.b02.sobatarlydia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.b02.sobatarlydia.model.KategoriObatModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.model.PenawaranModel;
import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.service.ObatService;
import propensi.b02.sobatarlydia.service.PenawaranService;
import propensi.b02.sobatarlydia.service.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/penawaran")
public class PenawaranController {
    @Autowired
    UserService userService;

    @Autowired
    PenawaranService penawaranService;

    @GetMapping("/daftarkan-penawaran")
    private String daftarkanPenawaranForm(Model model){
        PenawaranModel penawaran = new PenawaranModel();
        model.addAttribute("penawaran", penawaran);
        return "form-add-penawaran";
    }

    @PostMapping(value = "/daftarkan-penawaran")
    private String daftarkanPenawaranSubmit(@ModelAttribute PenawaranModel penawaran, Model model, Principal principal){
//        PenawaranModel penawarann = penawaranService.getPenawaranById(penawaran.getId());
//
//        if (penawarann != null) {
//            model.addAttribute("penawaran", penawaran);
//            model.addAttribute("statMsg", 2);
//            return "form-add-penawaran";
//        }

        PenggunaModel akun = userService.getAkunByEmail(principal.getName());
        penawaran.setDistributor(akun);
        penawaran.setTanggal(LocalDate.now());
        penawaranService.addPenawaran(penawaran);

        model.addAttribute("penawaran", penawaran);
        model.addAttribute("statMsg", 1);
        return "form-add-penawaran";
    }
}
