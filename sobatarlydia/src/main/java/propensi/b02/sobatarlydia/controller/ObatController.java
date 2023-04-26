package propensi.b02.sobatarlydia.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import propensi.b02.sobatarlydia.dto.FakturDto;
import propensi.b02.sobatarlydia.dto.ObatDetailDto;
import propensi.b02.sobatarlydia.dto.ObatUpdtDTO;
import propensi.b02.sobatarlydia.dto.ObatWaiting;
import propensi.b02.sobatarlydia.model.FakturModel;
import propensi.b02.sobatarlydia.model.KategoriObatModel;
import propensi.b02.sobatarlydia.model.RiwayatObatModel;
import propensi.b02.sobatarlydia.model.ObatDetailId;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.service.*;
import propensi.b02.sobatarlydia.setting.Setting;

@Controller
@RequestMapping("/obat")

public class ObatController {
    @Autowired
    ObatService obatService;

    @Autowired
    FakturService fakturService;

    @Autowired
    RiwayatService riwayatService;

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
    private String daftarkanObatSubmit(@ModelAttribute ObatModel obat, Model model, Principal principal){
        ObatModel obatt = obatService.getObatByNamaDanFarmasi(obat.getNamaObat(), obat.getFarmasi());
        List<KategoriObatModel> listKategori = kategoriService.getListKategori();
        model.addAttribute("listKategori", listKategori);

        if (obatt != null) {
            model.addAttribute("obat", obat);
            model.addAttribute("statMsg", 2);
            return "form-add-obat";
        }

        obatService.setId(obat);
        obat.setStok(0);
        
        obatService.addObat(obat);

        PenggunaModel akun = userService.getAkunByEmail(principal.getName());

        riwayatService.record(obat, akun, "daftar");

        model.addAttribute("obat", obat);
        model.addAttribute("statMsg", 1);
        return "form-add-obat";
    }

    @GetMapping("/input-data")
    private String inputDataObatFormPage(Model model){
        List<ObatModel> listObat = obatService.getListObat();
        Set<String> listFarmasi = new HashSet<>();
        for (ObatModel obat: listObat) {
            listFarmasi.add(obat.getFarmasi());
        }
        
        FakturDto faktur = new FakturDto();
        List<ObatDetailDto> listObatDetail = new ArrayList<>();

        faktur.setObat(listObatDetail);
        faktur.getObat().add(new ObatDetailDto());

        model.addAttribute("listFarmasi", listFarmasi);
        model.addAttribute("faktur", faktur);
        model.addAttribute("listObat", new ArrayList<>());

        model.addAttribute("host", Setting.CLIENT_BASE_URL);

        return "form-input-data-obat";
    }

    @PostMapping(value = "/input-data", params = {"addRow"})
    public String addRowObatMultiple(@ModelAttribute FakturDto faktur, Model model) {
        if (faktur.getObat() == null || faktur.getObat().size() == 0) {
            faktur.setObat(new ArrayList<>());
        }
        
        faktur.getObat().add(new ObatDetailDto());
        
        List<ObatModel> listObat = obatService.getListObat();
        Set<String> listFarmasi = new HashSet<>();
        for (ObatModel obat: listObat) {
            listFarmasi.add(obat.getFarmasi());
        }

        List<ObatModel> listObatBaru = obatService.getObatByFarmasi(faktur.getFarmasi());

        model.addAttribute("listFarmasi", listFarmasi);
        model.addAttribute("faktur", faktur);
        model.addAttribute("listObat", listObatBaru);


        return "form-input-data-obat";
    }

    @PostMapping(value = "/input-data", params = {"deleteRow"})
    private String deleteRowObat(@ModelAttribute FakturDto faktur, 
                                 @RequestParam("deleteRow") Integer row,
                                 Model model
    ) {
        final Integer rowId = Integer.valueOf(row);
        faktur.getObat().remove(rowId.intValue());

        List<ObatModel> listObat = obatService.getListObat();
        Set<String> listFarmasi = new HashSet<>();
        for (ObatModel obat: listObat) {
            listFarmasi.add(obat.getFarmasi());
        }

        List<ObatModel> listObatBaru = obatService.getObatByFarmasi(faktur.getFarmasi());

        model.addAttribute("listFarmasi", listFarmasi);
        model.addAttribute("faktur", faktur);
        model.addAttribute("listObat", listObatBaru);


        return "form-input-data-obat";
    }


    @PostMapping("/input-data")
    private String inputDataObatSubmitPage(@ModelAttribute FakturDto faktur, Model model, Principal principal) {

        PenggunaModel akun = userService.getAkunByEmail(principal.getName());

        List<FakturModel> fakturs = fakturService.getFakturByFarmasi(faktur.getFarmasi());
        int kodeBatch = fakturs.size() + 1;

        FakturModel fakturBaru = new FakturModel();
        fakturBaru.setNoFaktur(faktur.getNoFaktur());
        fakturBaru.setFarmasi(faktur.getFarmasi());
        fakturBaru.setKodeBatch(kodeBatch);
        fakturBaru.setTanggal(faktur.getTanggal());
        fakturBaru.setTanggalJatuhTempo(faktur.getTanggalJatuhTempo());
        fakturBaru.setStatusFaktur("Belum Lunas");
        
        fakturService.add(fakturBaru);

        List<ObatDetailModel> listBaru = new ArrayList<>();

        for (ObatDetailDto obat: faktur.getObat()) {
            ObatModel parent = obatService.getObatByNamaDanFarmasi(obat.getNamaObat(), faktur.getFarmasi());
            ObatDetailId key = new ObatDetailId(parent, kodeBatch);
            ObatDetailModel obatDetailBaru = new ObatDetailModel();

            obatDetailBaru.setObatDetailId(key);
            obatDetailBaru.setStatusKonfirmasi("Menunggu");
            obatDetailBaru.setStatus("Tersedia");
            obatDetailBaru.setTanggalKadaluarsa(obat.getTanggalKadaluarsa());
            obatDetailBaru.setJumlahBox(obat.getJumlahBox());
            obatDetailBaru.setSatuanPerBox(obat.getSatuanPerBox());
            obatDetailBaru.setJumlahPerBox(obat.getJumlahPerBox());
            obatDetailBaru.setStokTotal(obat.getJumlahPerBox() * obat.getJumlahBox());
            obatDetailBaru.setNoFaktur(fakturBaru);

            obatService.addObatDetail(obatDetailBaru);

            riwayatService.record(parent, obatDetailBaru, akun, "input detail");

            listBaru.add(obatDetailBaru);
        }

        fakturBaru.setListObatDetail(listBaru);
        fakturService.add(fakturBaru);

        List<ObatModel> listObat = obatService.getListObat();
        Set<String> listFarmasi = new HashSet<>();
        for (ObatModel obat2: listObat) {
            listFarmasi.add(obat2.getFarmasi());
        }

        model.addAttribute("listObat", listObat);
        model.addAttribute("listFarmasi", listFarmasi);
        model.addAttribute("faktur", faktur);
        model.addAttribute("statMsg", 1);


        return "form-input-data-obat";
    }

    @GetMapping("/data-obat")
    public String listObat(Model model) {
        List<ObatModel> listObat = obatService.getListObat();
        List<KategoriObatModel> listKategori = kategoriService.getListKategori();

        model.addAttribute("listKategoriObat", listObat);
        model.addAttribute("listKategori", listKategori);
        model.addAttribute("listObat", listObat);
        model.addAttribute("judulKategoriObat", "Seluruh Obat");
        return "viewall-obat";
    }


    @GetMapping("/detail-obat/{idObat}")
    public String detailObat(Model model, Principal principal, @PathVariable String idObat){
        PenggunaModel akun = userService.getAkunByEmail(principal.getName());
        ObatModel obatModel = obatService.getObatById(idObat);
        if (obatModel==null) {
            return "viewall-obat";
        }
        List<ObatDetailModel> statusObat = obatModel.getListDetailObat();
        List<ObatDetailModel> statusKonfirmasiObat = new ArrayList<>();
        int total = 0;
        for (int i = 0; i < statusObat.size(); i++) {
            if (statusObat.get(i).getStatusKonfirmasi().equals("Diterima")) {
                statusKonfirmasiObat.add(statusObat.get(i));
                if (statusObat.get(i).getIsArsip() == 0) {
                    total += statusObat.get(i).getStokTotal();
                }
            }
        }
        obatModel.setStok(total);
        String statusNow="Kosong";
        for (int i = 0; i < statusKonfirmasiObat.size(); i++) {
            if(statusKonfirmasiObat.get(i).getStatus().equals("Diarsipkan")) {
                statusNow = "Diarsipkan";
            }
            if (statusKonfirmasiObat.get(i).getStatus().equals("Tersedia")){
                statusNow = "Tersedia";
                break;
            }
        }

        List<RiwayatObatModel> listRiwayat = obatModel.getListRiwayat();
        Collections.sort(listRiwayat, new Comparator<RiwayatObatModel>() {
            public int compare(RiwayatObatModel synchronizedListOne, RiwayatObatModel synchronizedListTwo) {
                return ((RiwayatObatModel) synchronizedListOne).getKey().getWaktuPerubahan()
                        .compareTo(((RiwayatObatModel) synchronizedListTwo).getKey().getWaktuPerubahan());
            }
        }); 

        List<RiwayatObatModel> lst = new ArrayList<>();

        if (listRiwayat.size() > 10) {
            
            for (int i = listRiwayat.size(); i >= listRiwayat.size() - 9; i--) {
                lst.add(listRiwayat.get(i-1));
            }
        } else {
            for (int i = listRiwayat.size(); i > 0; i--) {
                lst.add(listRiwayat.get(i-1));
            }
        }

        
        model.addAttribute("listRiwayat",lst);
        model.addAttribute("detailObat",obatModel);
        model.addAttribute("statusObat", statusKonfirmasiObat);
        model.addAttribute("status", statusNow);
        model.addAttribute("total", obatModel.getStok());
        model.addAttribute("akun", akun);
        return "viewall-detail-obat";
    }

    @GetMapping("/obat-ditolak/{obatDetailId}/{kodeBatch}")
    public String obatDitolak(Principal principal, Model model, @PathVariable String obatDetailId, @PathVariable Integer kodeBatch){
        PenggunaModel akun = userService.getAkunByEmail(principal.getName());
        ObatModel obat = obatService.getObatById(obatDetailId);
        ObatDetailId id = new ObatDetailId(obat, kodeBatch);
        ObatDetailModel tolak = obatService.getDetailObat(id);
        ObatDetailModel updateTolak = obatService.updateObatDitolak(tolak);

        model.addAttribute("tolak", tolak);
        model.addAttribute("updateTolak", updateTolak);

        List<ObatWaiting> listWaiting = obatDetailService.getAllObatWaiting();
        String status="isi";
        if (listWaiting.size()==0){
            status="kosong";
        }

        String role = userService.getAkunByEmail(principal.getName()).getRole();

        riwayatService.record(obat, tolak, akun, "tolak");

        model.addAttribute("status", status);
        model.addAttribute("role", role);
        model.addAttribute("id", obatDetailId);
        model.addAttribute("listWaiting", listWaiting);
        model.addAttribute("stat", 2);

        return "input-obat/viewall-waiting";
    }

    @GetMapping("/obat-diterima/{obatDetailId}/{kodeBatch}")
    public String obatDiterima(Principal principal, Model model, @PathVariable String obatDetailId, @PathVariable Integer kodeBatch){
        PenggunaModel akun = userService.getAkunByEmail(principal.getName());

        ObatModel obat = obatService.getObatById(obatDetailId);
        ObatDetailId id = new ObatDetailId(obat, kodeBatch);
        ObatDetailModel terima = obatService.getDetailObat(id);
        ObatDetailModel updateTerima = obatService.updateObatDiterima(terima);

        obat.setStok(obat.getStok() + terima.getStokTotal());
        obatService.addObat(obat);

        model.addAttribute("terima", terima);
        model.addAttribute("updateTerima", updateTerima);
        

        model.addAttribute("stat", 1);

        List<ObatWaiting> listWaiting = obatDetailService.getAllObatWaiting();
        String status="isi";
        if (listWaiting.size()==0){
            status="kosong";
        }
        String role = userService.getAkunByEmail(principal.getName()).getRole();

        riwayatService.record(obat, terima, akun, "terima");

        model.addAttribute("status", status);
        model.addAttribute("role", role);
        model.addAttribute("id", obatDetailId);
        model.addAttribute("listWaiting", listWaiting);
        model.addAttribute("stat", 1);

        return "input-obat/viewall-waiting";
    }

    @GetMapping("/arsipkan/{obatDetailId}/{kodeBatch}")
    public String arsipkanObat(Model model, RedirectAttributes redirectAttrs, @PathVariable String obatDetailId, @PathVariable Integer kodeBatch, Principal principal){
        PenggunaModel akun = userService.getAkunByEmail(principal.getName());
        ObatModel obat = obatService.getObatById(obatDetailId);
        ObatDetailId id = new ObatDetailId(obat, kodeBatch);
        ObatDetailModel arsip = obatService.getDetailObat(id);
        
        if (arsip.getIsArsip()==1) {
            if (arsip.getTanggalKadaluarsa().isBefore(LocalDate.now())){
                redirectAttrs.addFlashAttribute("error", "Obat yang sudah expired tidak dapat dibatalkan arsip");
                return "redirect:/obat/detail-obat/" + obatDetailId;
            }
        }
        ObatDetailModel updateArsip = obatService.updateObatDiarsip(arsip, akun);


        model.addAttribute("arsip", arsip);
        model.addAttribute("updateArsip", updateArsip);

        return "redirect:/obat/detail-obat/" + obatDetailId;
    }
    @GetMapping("/update/{obatDetailId}/{kodeBatch}")
    public String updateObatDetailFormPage(@PathVariable String obatDetailId, @PathVariable int kodeBatch, Model model){
        ObatModel obt = obatService.getObatById(obatDetailId);
        ObatDetailId idObat = new ObatDetailId(obt, kodeBatch);
        ObatDetailModel obatDetail = obatDetailService.getObatDetailByObatDetailId(idObat);
        ObatUpdtDTO dto = obatService.makeObatUpdtDTO(obatDetail, obatDetailId, kodeBatch);

        model.addAttribute("obatdto", dto);
        return "form-update-detail-obat";
    }

    @PostMapping("/update")
    public String updateObatDetailSubmitPage(Principal principal, @ModelAttribute ObatUpdtDTO obat, Model model){
        PenggunaModel akun = userService.getAkunByEmail(principal.getName());
        ObatModel obt = obatService.getObatById(obat.getIdobat());
        ObatDetailId idObat = new ObatDetailId(obt, obat.getKodebatch());
        int stat = 1;
        if(obat.getTanggalkadaluarsa().isAfter(LocalDate.now())){
            ObatDetailModel obatDetailPast = obatDetailService.getObatDetailByObatDetailId(idObat);
            
            if (obatDetailPast.getStatusKonfirmasi().equals("Ditolak")) {
                riwayatService.record(obt, obatDetailPast, akun, "revisi");
            }
            
            ObatDetailModel obatDetailNow = obatDetailService.makeSetterDetail(obatDetailPast, obat);
            obatDetailNow.setStatusKonfirmasi("Menunggu");
            ObatDetailModel updateObatDetail = obatDetailService.updateObatDetail(obatDetailNow);
            model.addAttribute("obat", updateObatDetail);
        }
        else{
            stat=0;
        }
        model.addAttribute("obatdto", obat);
        model.addAttribute("stat", stat);
        return "form-update-detail-obat";
    }


    @GetMapping("/riwayat/{obat}")
    public String riwayat(@PathVariable String obat, Model model){
        model.addAttribute("idobat", obat);
        model.addAttribute("host", Setting.CLIENT_BASE_URL);


        return "riwayat-obat";
    }

    @GetMapping("/update/arsip/{obatDetailId}/{kodeBatch}")
    public String updateToArsipFormPage(@PathVariable String obatDetailId, @PathVariable int kodeBatch, Model model){
        ObatModel obt = obatService.getObatById(obatDetailId);
        ObatDetailId idObat = new ObatDetailId(obt, kodeBatch);
        ObatDetailModel obatDetail1 = obatDetailService.getObatDetailByObatDetailId(idObat);
        ObatDetailModel obatDetail = obatDetailService.updateToArsip(obatDetail1);
        ObatUpdtDTO dto = obatService.makeObatUpdtDTO(obatDetail, obatDetailId, kodeBatch);

        model.addAttribute("obatdto", dto);
        model.addAttribute("stat", 2);
        return "form-update-detail-obat";
    }

}
