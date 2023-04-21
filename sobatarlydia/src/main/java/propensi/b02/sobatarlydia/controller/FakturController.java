package propensi.b02.sobatarlydia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.b02.sobatarlydia.model.FakturModel;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.service.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/faktur")
public class FakturController {

    @Autowired
    FakturService fakturService;

    @GetMapping("/updateStatusFaktur/{noFaktur}")
    public String statusFaktur(Model model, @PathVariable String noFaktur) {
        FakturModel fakturObat = fakturService.getFakturByNo(noFaktur);

        if (fakturObat==null) {
            return "redirect:/faktur/viewall";
        }
        List<ObatDetailModel> listInputObat = fakturObat.getListObatDetail();
        System.out.println(listInputObat.size());

        String statusObat = "Diterima";
        for (int i = 0; i < listInputObat.size(); i++) {
            if (!listInputObat.get(i).getStatusKonfirmasi().equals("Diterima")) {
                statusObat = "Belum";
                break;
            }
        }

        if (statusObat.equals("Belum")) {
            return "redirect:/faktur/viewall";
        }

        FakturModel updateStatusFaktur = fakturService.updateFakturStatus(fakturObat);
        model.addAttribute("faktur", updateStatusFaktur);
        return "redirect:/faktur/detail-faktur/"+ noFaktur; //nanti bawa ke page view all fakturnya hezkie
    }

    @GetMapping("/viewall")
    private String viewAllFaktur(Model model) {
        List<FakturModel> listFaktur = fakturService.getAllFaktur();
        model.addAttribute("listFaktur", listFaktur);
        return "viewall-faktur";
    }

    @GetMapping("/detail-faktur/{noFaktur}")
    public String detailFaktur(Model model, @PathVariable String noFaktur){
        // ObatModel obatModel = obatService.getObatById(idObat);
        FakturModel fakturModel = fakturService.getFakturByNo(noFaktur);
        if (fakturModel==null) {
            return "redirect:/faktur/viewall";
        }
        List<ObatDetailModel> listInputObat = fakturModel.getListObatDetail();

        String statusObat = "Diterima";
        for (int i = 0; i < listInputObat.size(); i++) {
            if (!listInputObat.get(i).getStatusKonfirmasi().equals("Diterima")) {
                statusObat = "Belum";
                break;
            }
        }

        model.addAttribute("fakturModel", fakturModel);
        model.addAttribute("listInputObat", listInputObat);
        model.addAttribute("statusObat", statusObat);
        return "viewdetail-faktur";
    }

}