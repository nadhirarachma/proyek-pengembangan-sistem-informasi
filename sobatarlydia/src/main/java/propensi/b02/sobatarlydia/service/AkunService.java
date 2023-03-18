package propensi.b02.sobatarlydia.service;

import propensi.b02.sobatarlydia.model.PenggunaModel;

import java.util.List;

public interface AkunService {
    public List<PenggunaModel> getListAkun();
    void nonaktifAkun(PenggunaModel akun);
    PenggunaModel getAkunByEmail(String email);
}
