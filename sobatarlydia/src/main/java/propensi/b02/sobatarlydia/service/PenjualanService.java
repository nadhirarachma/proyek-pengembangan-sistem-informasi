package propensi.b02.sobatarlydia.service;

import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.model.PenjualanModel;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Service
public interface PenjualanService {
    List<PenjualanModel> getListPenjualan();
    PenjualanModel addPenjualan(PenjualanModel penjualan);
    void deletePenjualan(PenjualanModel penjualan);
    PenjualanModel getPenjualanById(Long idPenjualan);
    HashMap<ObatModel, Integer> getListPenjualanByDate(LocalDate date);
    HashMap<LocalDate, Integer> getListPendapatanByMonth(int month, int year);
}
