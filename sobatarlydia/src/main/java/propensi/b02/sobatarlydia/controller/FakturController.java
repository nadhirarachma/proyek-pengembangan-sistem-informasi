package propensi.b02.sobatarlydia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import propensi.b02.sobatarlydia.model.FakturModel;
import propensi.b02.sobatarlydia.service.*;



public class FakturController {

    @Autowired
    FakturService fakturService;

    @GetMapping("/updateStatusFaktur/{noFaktur}")
    public String statusFaktur(Model model, @PathVariable Long noFaktur){
        FakturModel fakturObat = fakturService.getFakturByNo(noFaktur);
        FakturModel updateStatusFaktur = fakturService.updateFakturStatus(fakturObat);

        model.addAttribute("faktur", updateStatusFaktur);

        return "view-page-lunas"; //nanti bawa ke page view all fakturnya hezkie
    }
}
