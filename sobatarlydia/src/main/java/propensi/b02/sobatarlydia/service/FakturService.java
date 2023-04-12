package propensi.b02.sobatarlydia.service;

import propensi.b02.sobatarlydia.model.FakturModel;
import propensi.b02.sobatarlydia.model.ObatDetailModel;

import java.time.LocalDate;
import java.util.List;

public interface FakturService {
    public List<FakturModel> getFakturByFarmasi(String farmasi);
    public FakturModel getFakturByFarmasiDanTanggal(List<FakturModel> fakturs, LocalDate tanggal);
    public int generateKodeBatch(List<FakturModel> faktur);
    public void add(FakturModel faktur);
    public FakturModel getFakturByNo(Long noFaktur);
    public FakturModel updateFakturStatus(FakturModel fakturObat);
}
