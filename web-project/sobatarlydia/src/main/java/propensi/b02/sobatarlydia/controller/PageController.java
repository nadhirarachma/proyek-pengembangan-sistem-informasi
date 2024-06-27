package propensi.b02.sobatarlydia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.service.UserService;

import java.security.Principal;

@Controller
public class PageController {

    @Autowired
    UserService userService;
    
    @RequestMapping("/")
    public String home (Principal principal, Model model){
        String role = userService.getAkunByEmail(principal.getName()).getRole();
        model.addAttribute("role",role);
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/registrasi")
    public String registrasiFormPage() {
        return "registrasi";
    }

    @PostMapping("/registrasi")
    public String registrasiSubmit(@ModelAttribute PenggunaModel pengguna, Model model) {
        
        PenggunaModel newPengguna = userService.addDistributor(pengguna);

        if (newPengguna.getEmail().equals("exist")) {
            model.addAttribute("validasi", "Pengguna dengan email yang sama telah terdapat pada sistem. Mohon input kembali.");
            return "registrasi";
        }
        else {
            model.addAttribute("statMsg", 1);
            return "registrasi";
        }
    }
}
