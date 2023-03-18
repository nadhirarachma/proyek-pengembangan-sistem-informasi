package propensi.b02.sobatarlydia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import propensi.b02.sobatarlydia.model.*;
import propensi.b02.sobatarlydia.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Objects;

@Controller
public class ObatController {
    @Autowired
    ObatService obatService;

    @GetMapping("/obat/data-obat")
    public String listObat(Model model) {
        List<ObatModel> listObat = obatService.getListObat();
        model.addAttribute("listObat", listObat);
        return "viewall-data-obat";
    }

    @RequestMapping(value = "/obat/filter-obat", method = RequestMethod.POST)
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

    @RequestMapping(value = "/obat/filter-obat", method = RequestMethod.GET)
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

    @GetMapping("/obat/detail-obat/{idObat}")
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

    @GetMapping("/obat/obat-ditolak/{obatDetailId}/{kodeBatch}")
    public String obatDitolak(Model model, @PathVariable String obatDetailId, @PathVariable Integer kodeBatch){
        ObatModel obat = obatService.getObatById(obatDetailId);
        ObatDetailId id = new ObatDetailId(obat, kodeBatch);
        ObatDetailModel tolak = obatService.getDetailObat(id);
        ObatDetailModel updateTolak = obatService.updateObatDitolak(tolak);

        model.addAttribute("tolak", tolak);
        model.addAttribute("updateTolak", updateTolak);

        return "view-page-ditolak";
    }

    @GetMapping("/obat/obat-diterima/{obatDetailId}/{kodeBatch}")
    public String obatDiterima(Model model, @PathVariable String obatDetailId, @PathVariable Integer kodeBatch){
        ObatModel obat = obatService.getObatById(obatDetailId);
        ObatDetailId id = new ObatDetailId(obat, kodeBatch);
        ObatDetailModel terima = obatService.getDetailObat(id);
        ObatDetailModel updateTerima = obatService.updateObatDiterima(terima);

        model.addAttribute("terima", terima);
        model.addAttribute("updateTerima", updateTerima);

        return "view-page-diterima";
    }

}
