package propensi.b02.sobatarlydia.service;

import java.util.List;

import propensi.b02.sobatarlydia.dto.ObatUpdtDTO;
import propensi.b02.sobatarlydia.model.KategoriObatModel;
import propensi.b02.sobatarlydia.model.ObatDetailId;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;

public interface ObatService {
    ObatModel addObat(ObatModel obat);
    ObatDetailModel addObatDetail(ObatDetailModel obat);
    ObatModel setId(ObatModel obat);
    List<ObatModel> getListObat();
    List<ObatModel> getObatByFarmasi(String farmasi);
    ObatModel getObatByNamaDanFarmasi(String nama, String farmasi);
    ObatModel getObatById(String idObat);
    List<ObatModel> getListObatFiltered(Integer no);
    List<KategoriObatModel> getListKategori();
    KategoriObatModel getKategoriById(Integer no);
    ObatDetailModel getDetailObat(ObatDetailId idObat);
    ObatDetailModel updateObatDitolak(ObatDetailModel tolak);
    ObatDetailModel updateObatDiterima(ObatDetailModel terima);
    List<ObatModel> getListObatDiterimaDanTersedia();
    ObatUpdtDTO makeObatUpdtDTO (ObatDetailModel obatDetail, String obatDetailId, int kodeBatch);
}
