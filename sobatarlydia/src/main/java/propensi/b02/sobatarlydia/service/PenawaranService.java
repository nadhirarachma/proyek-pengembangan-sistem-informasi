package propensi.b02.sobatarlydia.service;

import propensi.b02.sobatarlydia.model.PenawaranModel;
import propensi.b02.sobatarlydia.model.PenjualanModel;

public interface PenawaranService {
    PenawaranModel getPenawaranById(Long idPenawaran);
    PenawaranModel addPenawaran(PenawaranModel penawaran);

}
