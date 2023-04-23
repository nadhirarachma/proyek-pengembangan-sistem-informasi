package propensi.b02.sobatarlydia.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.PenjualanModel;
import propensi.b02.sobatarlydia.repository.PenjualanDb;

@Service
public class PenjualanServiceImpl implements PenjualanService {
    
    @Autowired
    private PenjualanDb penjualanDb;

    @Override
    public List<PenjualanModel> getListPenjualan() {
        return penjualanDb.findAll();
    }

    @Override
    public PenjualanModel addPenjualan(PenjualanModel penjualan) {
        return penjualanDb.save(penjualan);
    }

    @Override
    public void deletePenjualan(PenjualanModel penjualan) {
        penjualanDb.delete(penjualan);
    }

    @Override
    public PenjualanModel getPenjualanById(Long idPenjualan) {
        Optional<PenjualanModel> penjualan = penjualanDb.findById(idPenjualan);
        if (penjualan.isPresent()) {
            return penjualan.get();
        } else return null;
    }
}
