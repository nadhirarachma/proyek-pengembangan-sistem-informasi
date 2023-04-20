package propensi.b02.sobatarlydia.service;

import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.PenjualanModel;

import java.util.List;

@Service
public interface PenjualanService {
    List<PenjualanModel> getListPenjualan();
    PenjualanModel addPenjualan(PenjualanModel penjualan);
    PenjualanModel getPenjualanById(Long idPenjualan);
}
