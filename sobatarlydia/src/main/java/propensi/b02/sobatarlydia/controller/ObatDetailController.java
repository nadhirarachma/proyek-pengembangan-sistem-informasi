package propensi.b02.sobatarlydia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import propensi.b02.sobatarlydia.dto.ObatWaiting;
import propensi.b02.sobatarlydia.service.ObatDetailService;
import propensi.b02.sobatarlydia.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/obat-detail")
public class ObatDetailController {

    @Autowired
    ObatDetailService obatDetailService;

    @Autowired
    UserService userService;

    @GetMapping("/waiting")
    public String listAllWaiting(Principal principal, Model model) {
        List<ObatWaiting> listWaiting = obatDetailService.getAllObatWaiting();
        String status="isi";
        if (listWaiting.size()==0){
            status="kosong";
        }

        String role = userService.getAkunByEmail(principal.getName()).getRole();

        model.addAttribute("status", status);
        model.addAttribute("role", role);
        model.addAttribute("listWaiting", listWaiting);
        return "input-obat/viewall-waiting";
    }

}
