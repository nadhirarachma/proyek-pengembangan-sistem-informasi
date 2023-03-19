package propensi.b02.sobatarlydia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import propensi.b02.sobatarlydia.rest.ObatWaiting;
import propensi.b02.sobatarlydia.service.ObatDetailService;

import java.util.List;

@Controller
@RequestMapping("/obat-detail")
public class ObatDetailController {

    @Autowired
    ObatDetailService obatDetailService;

    @GetMapping("/waiting")
    public String listAllWaiting(Model model) {
        List<ObatWaiting> listWaiting = obatDetailService.getAllObatWaiting();
        String status="isi";
        if (listWaiting.size()==0){
            status="kosong";
        }

        String role = "apoteker";

        System.out.println(status);
        model.addAttribute("status", status);
        model.addAttribute("role", role);
        model.addAttribute("listWaiting", listWaiting);
        return "input-obat/viewall-waiting";
    }


}
