package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.FakturModel;

import propensi.b02.sobatarlydia.repository.FakturDb;

import java.time.LocalDate;
import java.util.List;

@Service
public class FakturServiceImpl implements FakturService{

    @Autowired
    private FakturDb fakturDb;

    public List<FakturModel> getFakturByFarmasi(String farmasi) {
        return fakturDb.findByFarmasi(farmasi);
    }

    public FakturModel getFakturByFarmasiDanTanggal(List<FakturModel> fakturs, LocalDate tanggal) {

        for (FakturModel faktur: fakturs) {
            if (faktur.getTanggal().equals(tanggal)) {
                return faktur;
            }
        }
        return null;
    }

    public int generateKodeBatch(List<FakturModel> fakturs) {
        int baru = 0;

        for (FakturModel faktur: fakturs) {
            if (faktur.getKodeBatch() > baru) {
                baru = faktur.getKodeBatch();
            }
        }

        return baru + 1;
    }

    public void add(FakturModel faktur) {
        fakturDb.save(faktur);
    }


}
