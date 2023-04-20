package propensi.b02.sobatarlydia.service;

import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.KuantitasModel;

@Service
public interface KuantitasService {
    KuantitasModel addKuantitas(KuantitasModel kuantitas);
}
