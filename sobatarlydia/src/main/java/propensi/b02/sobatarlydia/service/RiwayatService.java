package propensi.b02.sobatarlydia.service;

import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.model.RiwayatObatModel;

public interface RiwayatService {
    RiwayatObatModel addRiwayat(RiwayatObatModel riwayat);
    void record(ObatModel obat, PenggunaModel akun, String info);
    void record(ObatModel obat, ObatDetailModel obatDetail, PenggunaModel akun, String info);
    void record(ObatModel obat, ObatDetailModel obatDetail, PenggunaModel akun, String info, int kuantitas);
    
}
