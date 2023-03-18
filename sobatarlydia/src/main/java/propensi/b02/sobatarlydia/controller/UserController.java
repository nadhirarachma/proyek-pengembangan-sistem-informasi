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

@Controller
@RequestMapping("/pengguna")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/add")
    public String addPengguna(Model model) {
        PenggunaModel pengguna = new PenggunaModel();
        model.addAttribute("pengguna",pengguna);
        return "user/form-add-pengguna";
    }

    @PostMapping("/add")
    public String savePengguna(@ModelAttribute PenggunaModel pengguna, Model model) {
        PenggunaModel savedUser = userService.addPengguna(pengguna);
        if (savedUser == null) {
            model.addAttribute("stat",1);
            model.addAttribute("pengguna", new PenggunaModel());
            return "user/form-add-pengguna";
        }
        else {
//            model.addAttribute("stat","sukses");
            model.addAttribute("pengguna", savedUser);
            return "user/notif-sukses";
        }

    }

}
