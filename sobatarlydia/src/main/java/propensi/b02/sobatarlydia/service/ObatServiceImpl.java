package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.model.KategoriObatModel;
import propensi.b02.sobatarlydia.model.ObatDetailId;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.repository.ObatDb;
import propensi.b02.sobatarlydia.repository.ObatDetailDb;
import propensi.b02.sobatarlydia.repository.KategoriObatDb;

import propensi.b02.sobatarlydia.repository.ObatDetailDb;
import javax.transaction.Transactional;


import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ObatServiceImpl implements ObatService{
    @Autowired
    ObatDb obatDb;

    @Autowired
    ObatDetailDb obatDetailDb;

    @Autowired
    KategoriObatDb kategoriObatDb;

    @Override
    public List<ObatModel> getListObat() {
        return obatDb.findAll();
    }

    @Override
    public ObatModel getObatById(String idObat){
        Optional<ObatModel> obat = obatDb.findById(idObat);
        if(obat.isPresent()){
            return obat.get();
        } else return null;
    }

    @Override
    public List<ObatModel> getListObatFiltered(Integer no) {
        return obatDb.findKategoriFiltered(no);
    }

    @Override
    public List<KategoriObatModel> getListKategori() {
        return kategoriObatDb.findAll();
    }

    @Override
    public KategoriObatModel getKategoriById(Integer no){
        Optional <KategoriObatModel> kategori = kategoriObatDb.findByNo(no);
        return kategori.orElse(null);
    }
    @Override
    public ObatDetailModel getDetailObat(ObatDetailId idObat)
    {
        return obatDetailDb.findByObatDetailId(idObat).get();
    }

    @Override
    public ObatDetailModel updateObatDitolak(ObatDetailModel tolak) {
        tolak.setStatusKonfirmasi("Ditolak");
        return obatDetailDb.save(tolak);

//        return obatDetailDb.findById(tolak.getObatDetailId().getIdObat().getIdObat()).get();
    }

    @Override
    public ObatDetailModel updateObatDiterima(ObatDetailModel terima) {
        terima.setStatusKonfirmasi("Diterima");
        return obatDetailDb.save(terima);    }
}
