package propensi.b02.sobatarlydia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.service.AkunService;

import java.util.List;

@Controller
@RequestMapping("/akun")
public class AkunController {

    @Qualifier("akunServiceImpl")
    @Autowired
    private AkunService akunService;

    @GetMapping( "/viewall")
    private String viewAllAkun (Model model) {
        List<PenggunaModel> listAkun = akunService.getListAkun();
        listAkun.removeIf(x -> x.getIsActive() == 1);
        model.addAttribute("listAkun", listAkun);
        return "viewall-akun";
    }

    @GetMapping("/nonaktif/{email}")
    public String nonaktifAkun(@PathVariable String email, Model model){
        PenggunaModel akun = akunService.getAkunByEmail(email);
        akun.setIsActive(1);
        akunService.nonaktifAkun(akun);
        return "redirect:/akun/viewall";
    }

}
