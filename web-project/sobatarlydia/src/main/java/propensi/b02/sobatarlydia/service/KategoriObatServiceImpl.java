package propensi.b02.sobatarlydia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.KategoriObatModel;
import propensi.b02.sobatarlydia.repository.KategoriObatDb;

@Service
public class KategoriObatServiceImpl implements KategoriObatService {
    @Autowired
    KategoriObatDb kategoriObatDb;

    public List<KategoriObatModel> getListKategori() {
        return kategoriObatDb.findAll();
    }
}
