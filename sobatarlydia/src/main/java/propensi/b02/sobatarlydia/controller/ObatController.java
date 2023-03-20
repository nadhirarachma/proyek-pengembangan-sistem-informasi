package propensi.b02.sobatarlydia.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import propensi.b02.sobatarlydia.dto.ObatDetailDto;
import propensi.b02.sobatarlydia.model.FakturModel;
import propensi.b02.sobatarlydia.model.KategoriObatModel;
import propensi.b02.sobatarlydia.model.ObatDetailId;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.rest.ObatWaiting;
import propensi.b02.sobatarlydia.service.*;

@Controller
@RequestMapping("/obat")

public class ObatController {
    @Autowired
    ObatService obatService;

    @Autowired
    FakturService fakturService;

    @Autowired
    ObatDetailService obatDetailService;

    @Autowired
    KategoriObatService kategoriService;

    @Autowired
    UserService userService;
    
    @GetMapping("/daftarkan-obat")
    private String daftarkanObatForm(Model model){
        ObatModel obat = new ObatModel();
        List<KategoriObatModel> listKategori = kategoriService.getListKategori();
        model.addAttribute("obat", obat);
        model.addAttribute("listKategori", listKategori);
        return "form-add-obat";
    }

    @PostMapping(value = "/daftarkan-obat")
    private String daftarkanObatSubmit(@ModelAttribute ObatModel obat, Model model){
        ObatModel obatt = obatService.getObatByNamaDanFarmasi(obat.getNamaObat(), obat.getFarmasi());

        if (obatt != null) {
            model.addAttribute("obat", obat);
            model.addAttribute("statMsg", 2);
            return "form-add-obat";
        }

        obatService.setId(obat);
        
        obatService.addObat(obat);
        model.addAttribute("obat", obat);
        model.addAttribute("statMsg", 1);
        return "form-add-obat";
    }

    @GetMapping("/input-data")
    private String inputDataObatFormPage(Model model){
        ObatDetailDto obatDetail = new ObatDetailDto();
        List<ObatModel> listObat = obatService.getListObat();
        Set<String> listFarmasi = new HashSet<>();
        for (ObatModel obat: listObat) {
            listFarmasi.add(obat.getFarmasi());
        }
        System.out.println(listFarmasi);

        model.addAttribute("obatDetail", obatDetail);
        model.addAttribute("listObat", listObat);
        model.addAttribute("listFarmasi", listFarmasi);
        return "form-input-data-obat";
    }

    @PostMapping("/input-data")
    private String inputDataObatSubmitPage(@ModelAttribute ObatDetailDto obatDetail, Model model) {
        ObatModel obat = obatService.getObatByNamaDanFarmasi(obatDetail.getNamaObat(), obatDetail.getFarmasi());
        
        LocalDate today = LocalDate.now();
        List<FakturModel> fakturs = fakturService.getFakturByFarmasi(obatDetail.getFarmasi());
        FakturModel faktur = fakturService.getFakturByFarmasiDanTanggal(fakturs, today);
        int kodeBatch;

        if (fakturs.size() == 0) {
            kodeBatch = 1;
            faktur = new FakturModel();
            faktur.setFarmasi(obatDetail.getFarmasi());
            faktur.setKodeBatch(kodeBatch);
            faktur.setTanggal(today);
            fakturService.add(faktur);
        } else {
            if (faktur == null) {
                kodeBatch = fakturService.generateKodeBatch(fakturs);
                faktur = new FakturModel();
                faktur.setFarmasi(obatDetail.getFarmasi());
                faktur.setKodeBatch(kodeBatch);
                faktur.setTanggal(today);
                fakturService.add(faktur);
            } else {
                kodeBatch = faktur.getKodeBatch();
            }            
        }
        
        
        ObatDetailId id = new ObatDetailId(obat, kodeBatch);
        ObatDetailModel obatDetailBaru = new ObatDetailModel();

        obatDetailBaru.setObatDetailId(id);
        obatDetailBaru.setStatusKonfirmasi("Menunggu");
        obatDetailBaru.setStatus("Tersedia");
        obatDetailBaru.setTanggalKadaluarsa(obatDetail.getTanggalKadaluarsa());
        obatDetailBaru.setJumlahBox(obatDetail.getJumlahBox());
        obatDetailBaru.setSatuanPerBox(obatDetail.getSatuanPerBox());
        obatDetailBaru.setJumlahPerBox(obatDetail.getJumlahPerBox());
        obatDetailBaru.setStokTotal(obatDetail.getJumlahPerBox() * obatDetail.getJumlahBox());
        obatDetailBaru.setNoFaktur(faktur);
        
        obatService.addObatDetail(obatDetailBaru);

        List<ObatModel> listObat = obatService.getListObat();
        Set<String> listFarmasi = new HashSet<>();
        for (ObatModel obatt: listObat) {
            listFarmasi.add(obatt.getFarmasi());
        }
        System.out.println(listFarmasi);
        model.addAttribute("obatDetail", obatDetail);
        model.addAttribute("listObat", listObat);
        model.addAttribute("listFarmasi", listFarmasi);
        model.addAttribute("statMsg", 1);


        return "form-input-data-obat";
    }

    @GetMapping("/data-obat")
    public String listObat(Model model) {
        List<ObatModel> listObat = obatService.getListObat();
        List<KategoriObatModel> listKategori = kategoriService.getListKategori();
        model.addAttribute("listKategori", listKategori);
        model.addAttribute("listObat", listObat);
        return "viewall-data-obat";
    }

    @RequestMapping(value = "/filter-obat", method = RequestMethod.POST)
    public String controllerMethod(HttpServletRequest request, Model model){
        //this way you get value of the input you want
        Object kategori = request.getParameter("no");
        if (kategori != null ) {
            String kategoriString = request.getParameter("no");
            if (kategori.equals("0")){
                List<ObatModel> listObat = obatService.getListObat();
//            List<ObatModel> listKategoriObat = obatService.getListObatFiltered(noKategori);
                List<KategoriObatModel> listKategori = obatService.getListKategori();
                KategoriObatModel kategoriObatModel = obatService.getKategoriById(Integer.valueOf(kategoriString));

                model.addAttribute("listKategoriObat", listObat);
                model.addAttribute("listKategori", listKategori);
                model.addAttribute("listObat", listObat);
                model.addAttribute("judulKategoriObat", kategoriObatModel.getNamaKategori());
            }
            else {
//                String kategoriString = request.getParameter("no");
                Integer noKategori = Integer.valueOf(kategoriString);
                List<ObatModel> listKategoriObat = obatService.getListObatFiltered(noKategori);
                List<KategoriObatModel> listKategori = obatService.getListKategori();
                List<ObatModel> listObat = obatService.getListObat();
                KategoriObatModel kategoriObatModel = obatService.getKategoriById(Integer.valueOf(kategoriString));


                model.addAttribute("listKategoriObat", listKategoriObat);
                model.addAttribute("listKategori", listKategori);
                model.addAttribute("listObat", listObat);
                model.addAttribute("judulKategoriObat", kategoriObatModel.getNamaKategori());
            }
        }
        else if (kategori==null) {
//            kategori = "0";
            List<ObatModel> listObat = obatService.getListObat();
//            List<ObatModel> listKategoriObat = obatService.getListObatFiltered(noKategori);
            List<KategoriObatModel> listKategori = obatService.getListKategori();
//            KategoriObatModel kategoriObatModel = obatService.getKategoriById(Integer.valueOf(kategori));


            model.addAttribute("listKategoriObat", listObat);
            model.addAttribute("listKategori", listKategori);
            model.addAttribute("listObat", listObat);
            model.addAttribute("judulKategoriObat", "Seluruh Obat");

        }
        return "viewall-data-obat";
    }

    @RequestMapping(value = "/filter-obat", method = RequestMethod.GET)
    public String controllerMethod1(HttpServletRequest request, Model model) {
        //this way you get value of the input you want
        Object kategori = request.getParameter("no");
        if (kategori != null ) {
            String kategoriString = request.getParameter("no");
            if (kategori.equals("0")){
                List<ObatModel> listObat = obatService.getListObat();
//            List<ObatModel> listKategoriObat = obatService.getListObatFiltered(noKategori);
                List<KategoriObatModel> listKategori = obatService.getListKategori();
                KategoriObatModel kategoriObatModel = obatService.getKategoriById(Integer.valueOf(kategoriString));

                model.addAttribute("listKategoriObat", listObat);
                model.addAttribute("listKategori", listKategori);
                model.addAttribute("listObat", listObat);
                model.addAttribute("judulKategoriObat", kategoriObatModel.getNamaKategori());
            }
            else {
//                String kategoriString = request.getParameter("no");
                Integer noKategori = Integer.valueOf(kategoriString);
                List<ObatModel> listKategoriObat = obatService.getListObatFiltered(noKategori);
                List<KategoriObatModel> listKategori = obatService.getListKategori();
                List<ObatModel> listObat = obatService.getListObat();
                KategoriObatModel kategoriObatModel = obatService.getKategoriById(Integer.valueOf(kategoriString));


                model.addAttribute("listKategoriObat", listKategoriObat);
                model.addAttribute("listKategori", listKategori);
                model.addAttribute("listObat", listObat);
                model.addAttribute("judulKategoriObat", kategoriObatModel.getNamaKategori());
            }
        }
        else if (kategori==null) {
//            kategori = "0";
            List<ObatModel> listObat = obatService.getListObat();
//            List<ObatModel> listKategoriObat = obatService.getListObatFiltered(noKategori);
            List<KategoriObatModel> listKategori = obatService.getListKategori();
//            KategoriObatModel kategoriObatModel = obatService.getKategoriById(Integer.valueOf(kategori));


            model.addAttribute("listKategoriObat", listObat);
            model.addAttribute("listKategori", listKategori);
            model.addAttribute("listObat", listObat);
            model.addAttribute("judulKategoriObat", "Seluruh Obat");

        }
        return "viewall-data-obat";
    }

    @GetMapping("/detail-obat/{idObat}")
    public String detailObat(Model model, @PathVariable String idObat){
        ObatModel obatModel = obatService.getObatById(idObat);
        if (obatModel==null) {
            return "viewall-data-obat";
        }
        List<ObatDetailModel> statusObat = obatModel.getListDetailObat();
        String statusNow="Kosong";
        for (int i = 0; i < statusObat.size(); i++) {
                if (statusObat.get(i).getStatus().equals("Tersedia")){
                statusNow = "Tersedia";
                break;
            }
        }

        model.addAttribute("detailObat",obatModel);
        model.addAttribute("statusObat", statusObat);
        model.addAttribute("status", statusNow);
        return "viewall-detail-obat";
    }

    @GetMapping("/obat-ditolak/{obatDetailId}/{kodeBatch}")
    public String obatDitolak(Model model, @PathVariable String obatDetailId, @PathVariable Integer kodeBatch){
        ObatModel obat = obatService.getObatById(obatDetailId);
        ObatDetailId id = new ObatDetailId(obat, kodeBatch);
        ObatDetailModel tolak = obatService.getDetailObat(id);
        ObatDetailModel updateTolak = obatService.updateObatDitolak(tolak);

        model.addAttribute("tolak", tolak);
        model.addAttribute("updateTolak", updateTolak);

        return "view-page-ditolak";
    }

    @GetMapping("/obat-diterima/{obatDetailId}/{kodeBatch}")
    public String obatDiterima(Principal principal, Model model, @PathVariable String obatDetailId, @PathVariable Integer kodeBatch){
        ObatModel obat = obatService.getObatById(obatDetailId);
        ObatDetailId id = new ObatDetailId(obat, kodeBatch);
        ObatDetailModel terima = obatService.getDetailObat(id);
        ObatDetailModel updateTerima = obatService.updateObatDiterima(terima);

        model.addAttribute("terima", terima);
        model.addAttribute("updateTerima", updateTerima);
        
        model.addAttribute("stat", 1);

        List<ObatWaiting> listWaiting = obatDetailService.getAllObatWaiting();
        String status="isi";
        if (listWaiting.size()==0){
            status="kosong";
        }

//        String role = "apoteker";
//        String role =  String.valueOf(((User) authentication.getPrincipal()).getAuthorities().iterator().next());
        String role = userService.getAkunByEmail(principal.getName()).getRole();

        System.out.println(status);
        model.addAttribute("status", status);
        model.addAttribute("role", role);
        model.addAttribute("listWaiting", listWaiting);

        return "input-obat/viewall-waiting";
    }

}
