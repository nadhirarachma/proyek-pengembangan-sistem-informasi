package propensi.b02.sobatarlydia.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import propensi.b02.sobatarlydia.dto.InputReturDto;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.model.PenjualanModel;
import propensi.b02.sobatarlydia.model.ReturObatModel;
import propensi.b02.sobatarlydia.repository.ReturDb;

import propensi.b02.sobatarlydia.service.ObatService;
import propensi.b02.sobatarlydia.service.PenjualanService;

import propensi.b02.sobatarlydia.setting.Setting;

@Controller
@RequestMapping("/retur")
public class ReturController {

    @Autowired
    PenjualanService penjualanService;
    
    @Autowired
    ObatService obatService;

    @Autowired
    ReturDb returService;

    @GetMapping(value = "/add/")
    public String addReturSelectPage(Model model, @RequestParam long penjualan, @ModelAttribute InputReturDto retur) {
        retur.setIdPenjualan(penjualan);

        PenjualanModel penjualanModel = penjualanService.getPenjualanById(penjualan);

        List<ObatModel> listobat = obatService.getListObat();

        System.out.println(LocalDate.now().minusDays(7));
        model.addAttribute("tanggalbatasbawah", LocalDate.now().minusDays(7));
        model.addAttribute("tanggalbatasatas", LocalDate.now());
        model.addAttribute("host", Setting.CLIENT_BASE_URL);
        model.addAttribute("retur", retur);
        model.addAttribute("statMsg", 1);
        model.addAttribute("penjualan", penjualanModel);
        model.addAttribute("listObat", listobat);
    
        return "form-add-retur";
    }
    
    @GetMapping("/add")
    public String addReturFormPage(Model model){
        InputReturDto retur = new InputReturDto();
        PenjualanModel penjualan = new PenjualanModel();
        penjualan.setId(0);

        model.addAttribute("tanggalbatasbawah", LocalDate.now().minusDays(7));
        model.addAttribute("tanggalbatasatas", LocalDate.now());
        model.addAttribute("host", Setting.CLIENT_BASE_URL);

        model.addAttribute("retur", retur);
        model.addAttribute("penjualan", penjualan);
    
        return "form-add-retur";
    }

    @PostMapping("/add")
    private String daftarkanObatSubmit(@ModelAttribute InputReturDto retur, Model model, Principal principal){
        ReturObatModel returModel = new ReturObatModel();

        System.out.println(retur.getIdPenjualan());

        PenjualanModel penjualan = penjualanService.getPenjualanById(retur.getIdPenjualan());
        returModel.setPenjualan(penjualan);
        
        returModel.setWaktu(LocalDateTime.now());
        
        ObatDetailModel obatlama = penjualanService.getObatForRetur(penjualan, retur.getObatlama());
        returModel.setObatLama(obatlama);
        
        returModel.setJumlahObatLamaDitukar(retur.getKuantitasobatlama());
        
        System.out.println(retur.getObatlama());
        System.out.println(retur.getObatbaru());
        System.out.println(retur.getKuantitasobatbaru());
        ObatModel obat = obatService.getObatById(retur.getObatbaru());
        System.out.println(returModel);
        ObatDetailModel obatbaru = obat.getListDetailObat().get(0);
        returModel.setObatBaru(obatbaru);

        returModel.setJumlahObatBaruDitukar(retur.getKuantitasobatbaru());
        returService.save(returModel);


        model.addAttribute("statMsg", 2);
        model.addAttribute("penjualan", returModel.getPenjualan());
        model.addAttribute("retur", new InputReturDto());

    
        return "form-add-retur";
    }
}