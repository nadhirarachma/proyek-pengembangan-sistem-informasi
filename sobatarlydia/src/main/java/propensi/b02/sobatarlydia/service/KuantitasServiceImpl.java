package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.KuantitasModel;
import propensi.b02.sobatarlydia.repository.KuantitasDb;

@Service
public class KuantitasServiceImpl implements KuantitasService {
    
    @Autowired
    private KuantitasDb kuantitasDb;

    @Override
    public KuantitasModel addKuantitas(KuantitasModel kuantitas) {
        return kuantitasDb.save(kuantitas);
    }
}
