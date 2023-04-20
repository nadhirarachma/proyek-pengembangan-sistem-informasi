package propensi.b02.sobatarlydia.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import propensi.b02.sobatarlydia.dto.FakturDto;
import propensi.b02.sobatarlydia.dto.ObatDetailDto;
import propensi.b02.sobatarlydia.dto.ObatUpdtDTO;
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
        List<KategoriObatModel> listKategori = kategoriService.getListKategori();
        model.addAttribute("listKategori", listKategori);

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
        List<ObatModel> listObat = obatService.getListObat();
        Set<String> listFarmasi = new HashSet<>();
        for (ObatModel obat: listObat) {
            listFarmasi.add(obat.getFarmasi());
        }
        
        FakturDto faktur = new FakturDto();
        List<ObatDetailDto> listObatDetail = new ArrayList<>();

        faktur.setObat(listObatDetail);
        faktur.getObat().add(new ObatDetailDto());
        System.out.println(faktur.getObat());

        model.addAttribute("listFarmasi", listFarmasi);
        model.addAttribute("faktur", faktur);
        model.addAttribute("listObat", new ArrayList<>());

        return "form-input-data-obat";
    }

    @PostMapping(value = "/input-data", params = {"addRow"})
    public String addRowObatMultiple(@ModelAttribute FakturDto faktur,
                                      Model model
    ) {
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
    private String inputDataObatSubmitPage(@ModelAttribute FakturDto faktur, Model model) {

        List<FakturModel> fakturs = fakturService.getFakturByFarmasi(faktur.getFarmasi());
        int kodeBatch = fakturs.size() + 1;

        FakturModel fakturBaru = new FakturModel();
        fakturBaru.setNoFaktur(faktur.getNoFaktur());
        fakturBaru.setFarmasi(faktur.getFarmasi());
        fakturBaru.setKodeBatch(kodeBatch);
        fakturBaru.setTanggal(faktur.getTanggal());
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

                model.addAttribute("listKategoriObat", listObat);
                model.addAttribute("listKategori", listKategori);
                model.addAttribute("listObat", listObat);
                model.addAttribute("judulKategoriObat", "Seluruh Obat");
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

                model.addAttribute("listKategoriObat", listObat);
                model.addAttribute("listKategori", listKategori);
                model.addAttribute("listObat", listObat);
                model.addAttribute("judulKategoriObat", "Seluruh Obat");
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
        List<ObatDetailModel> statusKonfirmasiObat = new ArrayList<>();
        for (int i = 0; i < statusObat.size(); i++) {
            if (statusObat.get(i).getStatusKonfirmasi().equals("Diterima")) {
                statusKonfirmasiObat.add(statusObat.get(i));
            }
        }
        String statusNow="Kosong";
        for (int i = 0; i < statusObat.size(); i++) {
                if (statusObat.get(i).getStatus().equals("Tersedia")){
                statusNow = "Tersedia";
                break;
            }
        }

        model.addAttribute("detailObat",obatModel);
        model.addAttribute("statusObat", statusKonfirmasiObat);
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

        return "input-obat/viewall-waiting";
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
        String role = userService.getAkunByEmail(principal.getName()).getRole();

        System.out.println(status);
        model.addAttribute("status", status);
        model.addAttribute("role", role);
        model.addAttribute("listWaiting", listWaiting);

        return "input-obat/viewall-waiting";
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
    public String updateObatDetailSubmitPage(@ModelAttribute ObatUpdtDTO obat, Model model){
        ObatModel obt = obatService.getObatById(obat.getIdobat());
        ObatDetailId idObat = new ObatDetailId(obt, obat.getKodebatch());
        int stat = 1;
        if(obat.getTanggalkadaluarsa().isAfter(LocalDate.now())){
            ObatDetailModel obatDetailPast = obatDetailService.getObatDetailByObatDetailId(idObat);
            ObatDetailModel obatDetailNow = obatDetailService.makeSetterDetail(obatDetailPast, obat);
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

}
