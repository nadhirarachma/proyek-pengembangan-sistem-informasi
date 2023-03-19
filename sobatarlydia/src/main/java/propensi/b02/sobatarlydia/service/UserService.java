package propensi.b02.sobatarlydia.service;

import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.model.PenggunaModel;

import java.util.List;

@Service
public interface UserService {
    PenggunaModel addPengguna (PenggunaModel pengguna);
    List<PenggunaModel> getAllPengguna();
    public List<PenggunaModel> getListAkun();
    void nonaktifAkun(PenggunaModel akun);
    PenggunaModel getAkunByEmail(String email);
}
