package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.repository.AkunDb;

import java.util.List;
import java.util.Optional;

@Service
public class AkunServiceImpl implements AkunService{

    @Autowired
    private AkunDb akunDb;

    @Override
    public List<PenggunaModel> getListAkun() {
        return akunDb.findAll();
    }

    @Override
    public void nonaktifAkun(PenggunaModel akun){
        akunDb.save(akun);
    }

    @Override
    public PenggunaModel getAkunByEmail(String email){
        Optional<PenggunaModel> akun = akunDb.findByEmail(email);
        if(akun.isPresent()){
            return akun.get();
        } else return null;
    }

}
