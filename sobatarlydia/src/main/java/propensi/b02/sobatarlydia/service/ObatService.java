package propensi.b02.sobatarlydia.service;

import java.util.List;

import propensi.b02.sobatarlydia.model.KategoriObatModel;
import propensi.b02.sobatarlydia.model.ObatDetailId;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;

public interface ObatService {
    List<ObatModel> getListObat();
    ObatModel getObatById(String idObat);
//    ObatDetailModel getStatus(String status);
    List<ObatModel> getListObatFiltered(Integer no);
    List<KategoriObatModel> getListKategori();
    KategoriObatModel getKategoriById(Integer no);
    ObatDetailModel getDetailObat(ObatDetailId idObat);
    ObatDetailModel updateObatDitolak(ObatDetailModel tolak);
    ObatDetailModel updateObatDiterima(ObatDetailModel terima);

}
