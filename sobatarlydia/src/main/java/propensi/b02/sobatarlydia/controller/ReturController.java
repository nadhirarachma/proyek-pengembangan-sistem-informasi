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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import propensi.b02.sobatarlydia.dto.InputReturDto;
import propensi.b02.sobatarlydia.dto.ReturUpdtDto;
import propensi.b02.sobatarlydia.model.*;
import propensi.b02.sobatarlydia.service.KuantitasService;
import propensi.b02.sobatarlydia.service.ObatService;
import propensi.b02.sobatarlydia.service.PenjualanService;
import propensi.b02.sobatarlydia.service.ReturService;
import propensi.b02.sobatarlydia.service.UserService;
import propensi.b02.sobatarlydia.setting.Setting;

@Controller
@RequestMapping("/retur")
public class ReturController {

    @Autowired
    PenjualanService penjualanService;
    
    @Autowired
    ObatService obatService;

    @Autowired
    ReturService returService;
    
    @Autowired
    KuantitasService kuantitasService;

    @Autowired
    UserService userService;

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
    private String addReturSubmitPage(@ModelAttribute InputReturDto retur, Model model, Principal principal){
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
        returModel.setStatus("Menunggu");

        for (KuantitasModel kuantitas: returModel.getPenjualan().getKuantitas()) {
            if (kuantitas.getId().getObat().equals(returModel.getObatLama())) {
                if (returModel.getJumlahObatLamaDitukar() > kuantitas.getKuantitas()) {
                    model.addAttribute("statMsg", 3);
                    model.addAttribute("penjualan", returModel.getPenjualan());
                    model.addAttribute("retur", retur);
                    model.addAttribute("tanggalbatasbawah", LocalDate.now().minusDays(7));
                    model.addAttribute("tanggalbatasatas", LocalDate.now());
                    return "form-add-retur";
                }
            }
        }

        returService.add(returModel);


        model.addAttribute("statMsg", 2);
        model.addAttribute("penjualan", returModel.getPenjualan());
        model.addAttribute("retur", new InputReturDto());
        model.addAttribute("tanggalbatasbawah", LocalDate.now().minusDays(7));
        model.addAttribute("tanggalbatasatas", LocalDate.now());
    
        return "form-add-retur";
    }

    @GetMapping("/update/{returId}")
    public String updateReturObatFormPage(@PathVariable String returId, Model model){
        long returId1 = Long.parseLong(returId);
        ReturObatModel retur = returService.getReturById(returId1);
        ReturUpdtDto dto = returService.makeReturUpdateDTO(retur);
        PenjualanModel penjualan = penjualanService.getPenjualanById(retur.getPenjualan().getId());
        List<ObatModel> listobat = obatService.getListObat();
//        System.out.println(returId1);
//        System.out.println(penjualan.getKuantitas().size());
//        System.out.println(retur.getObatBaru().getObatDetailId().getIdObat().getNamaObat());
//        System.out.println(listobat);

        model.addAttribute("returdto", dto);
        model.addAttribute("penjualan", penjualan);
        model.addAttribute("listobat", listobat);
        return "form-update-retur";
    }

    @PostMapping("/update")
    public String updateReturObatSubmitPage(@ModelAttribute ReturUpdtDto retur, Model model){
        List<ObatModel> listobat = obatService.getListObat();
        ReturObatModel retur1 = returService.getReturById(retur.getKode());
        PenjualanModel penjualan = penjualanService.getPenjualanById(retur1.getPenjualan().getId());
        ObatDetailModel obatlama = penjualanService.getObatForRetur(penjualan, retur.getObatlama());
        ObatDetailModel obatbaru = obatService.getObatById(retur.getObatbaru()).getListDetailObat().get(0);
        int stat = 1;
        if (LocalDateTime.now().isBefore(penjualan.getWaktu().plusDays(7))){
            retur1.setObatLama(obatlama);
            retur1.setObatBaru(obatbaru);
            retur1.setJumlahObatLamaDitukar(retur.getKuantitaslama());
            retur1.setJumlahObatBaruDitukar(retur.getKuantitasbaru());
            retur1.setFeedback(null);
            
            if (retur1.getStatus().equals("Ditolak")) {
                retur1.setStatus("Menunggu, Sudah Direvisi");
            } else {
                retur1.setStatus("Menunggu, Pernah Direvisi");
            }
            
            returService.add(retur1);
        }
        else{
            stat=0;
        }
        if (stat==1){
            return "redirect:/retur/viewall";
        }
        model.addAttribute("returdto", retur);
        model.addAttribute("penjualan", penjualan);
        model.addAttribute("listobat", listobat);
        model.addAttribute("stat", stat);
        return "form-update-retur";

    }


    @GetMapping("/viewall")
    public String viewAllRetur (Principal principal, Model model) {
        String role = userService.getAkunByEmail(principal.getName()).getRole();

        List<ReturObatModel> listRetur = returService.getListRetur();
        model.addAttribute("listRetur", listRetur);
        model.addAttribute("role", role);
        model.addAttribute("retur", new ReturObatModel());
        return "viewall-retur";
    }

    @RequestMapping("/verifikasi")
    @ResponseBody
    public String verifikasiRetur(@ModelAttribute ReturObatModel retur, @RequestParam String feedback, @RequestParam String returId, @RequestParam String status) {
        ReturObatModel returObat = returService.getReturById(Long.parseLong(returId));
       
        if (retur.getStatus().equals("Diterima")) {
            returService.verifikasiRetur(returObat, "Diterima");
            PenjualanModel penjualan = returObat.getPenjualan();

            KuantitasModel kuantitasbaru = null;

            for (KuantitasModel kuantitas: penjualan.getKuantitas()) {
                if (kuantitas.getId().getObat().getObatDetailId().getIdObat().getIdObat().equals(returObat.getObatLama().getObatDetailId().getIdObat().getIdObat())) {
                    kuantitas.setKuantitas(kuantitas.getKuantitas() - returObat.getJumlahObatLamaDitukar());
                    kuantitasService.addKuantitas(kuantitas);
                }

                if (kuantitas.getId().getObat().getObatDetailId().getIdObat().getIdObat().equals(returObat.getObatBaru().getObatDetailId().getIdObat().getIdObat())) {
                    kuantitasbaru = kuantitas;
                }
            }

            if (kuantitasbaru == null) {
                KuantitasKey key = new KuantitasKey(returObat.getObatBaru(), penjualan);
                kuantitasbaru = new KuantitasModel(key, returObat.getJumlahObatBaruDitukar());
            } else {
                kuantitasbaru.setKuantitas(kuantitasbaru.getKuantitas() + returObat.getJumlahObatBaruDitukar());
            }

            penjualan.getKuantitas().add(kuantitasbaru);

            kuantitasService.addKuantitas(kuantitasbaru);

        } else {
            returObat.setFeedback(feedback);
            returService.verifikasiRetur(returObat, "Ditolak");
        }
        return "redirect:/retur/viewall";
    }
}