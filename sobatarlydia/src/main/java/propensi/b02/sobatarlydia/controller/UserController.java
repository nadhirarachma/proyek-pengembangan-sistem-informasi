package propensi.b02.sobatarlydia.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.service.UserService;

@Controller
@RequestMapping("/pengguna")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping( "/viewall")
    private String viewAllAkun (Model model) {
        List<PenggunaModel> listAkun = userService.getListAkun();
        listAkun.removeIf(x -> x.getIsActive() == 1);
        model.addAttribute("listAkun", listAkun);
        return "viewall-akun";
    }

    @GetMapping( "/profil")
    private String profil(Model model, Principal principal) {
        PenggunaModel akun = userService.getAkunByEmail(principal.getName());

        model.addAttribute("akun", akun);
        return "profil";
    }

    @GetMapping("/nonaktif/{email}")
    public String nonaktifAkun(@PathVariable String email, Model model){
        PenggunaModel akun = userService.getAkunByEmail(email);
        System.out.println(akun.getRole());
        if (!"Distributor".equals(akun.getRole())) {
            akun.setIsActive(1);
            userService.nonaktifAkun(akun);
        }
        return "redirect:/pengguna/viewall";
    }

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
            model.addAttribute("pengguna", savedUser);
            return "redirect:/pengguna/viewall";
        }

    }

    @GetMapping("/update-password")
    public String updatePassFormPage(Model model){
        return "user/form-update-pass";
    }

    @PostMapping("/update-password")
    public String updatePassSubmitPage(@RequestParam(value = "passwordlama") String passwordlama,
                                       @RequestParam(value = "passwordbaru") String passwordbaru,
                                       @RequestParam(value = "passwordbaru1") String passwordbaru1,
                                       Model model, Principal principal){
        PenggunaModel user1 = userService.getAkunByEmail(principal.getName());
        int stat =0;

        Boolean isPassLamaTrue = userService.getPassChecker(passwordlama, user1.getPassword());
        if(isPassLamaTrue){
            if(passwordbaru.equals(passwordbaru1)){
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String passBaruEncode = passwordEncoder.encode(passwordbaru);
                userService.updatePass(user1, passBaruEncode);
                return "redirect:/";
            }
        }
        model.addAttribute("stat", stat);
        return "user/form-update-pass";
    }

}
