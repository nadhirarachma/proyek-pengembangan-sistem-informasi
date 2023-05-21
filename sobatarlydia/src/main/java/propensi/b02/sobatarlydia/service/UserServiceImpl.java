package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.repository.UserDB;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDB userDB;

    @Override
    public PenggunaModel addPengguna(PenggunaModel pengguna) {
        List<PenggunaModel> listPengguna = getAllPengguna();
        List<String> listEmail = new ArrayList<>();
        for (int i = 0; i < listPengguna.size(); i++) {
            listEmail.add(listPengguna.get(i).getEmail());
        }
        if(listEmail.contains(pengguna.getEmail())){
            return null;
        }
        pengguna.setPassword(encrypt(pengguna.getPassword()));

        return userDB.save(pengguna);
    }

    @Override
    public List<PenggunaModel> getAllPengguna() {
        return userDB.findAll();
    }

    @Override
    public List<PenggunaModel> getListAkun() {
        return userDB.findAll();
    }

    @Override
    public void nonaktifAkun(PenggunaModel akun){
        userDB.save(akun);
    }

    @Override
    public PenggunaModel getAkunByEmail(String email){
        Optional<PenggunaModel> akun = userDB.findByEmail(email);
        if(akun.isPresent()){
            return akun.get();
        } else return null;
    }

    @Override
    public String encrypt(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(password);
        System.out.println("AAAAAAA");
        return passwordEncoder.encode(password);
    }

    @Override
    public Boolean getPassChecker(String passwordlama, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(passwordlama, password);
    }

    @Override
    public void updatePass(PenggunaModel user1, String passBaruEncode) {
        user1.setPassword(passBaruEncode);
        userDB.save(user1);
    }

    @Override
    public PenggunaModel addDistributor(PenggunaModel pengguna) {

        String email = pengguna.getEmail();
        if (getAkunByEmail(email) == null) {
            
            String pass = encrypt(pengguna.getPassword());

            pengguna.setPassword(pass);
            pengguna.setIsActive(0);
            pengguna.setRole("Distributor");
            return userDB.save(pengguna);
        }
        else {
            PenggunaModel penggunaExist = new PenggunaModel();
            penggunaExist.setEmail("exist");
            return penggunaExist;
        }
    }
}
