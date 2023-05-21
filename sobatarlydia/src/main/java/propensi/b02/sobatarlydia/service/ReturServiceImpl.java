package propensi.b02.sobatarlydia.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.dto.ReturUpdtDto;
import propensi.b02.sobatarlydia.model.ReturObatModel;
import propensi.b02.sobatarlydia.repository.ReturDb;


@Service
public class ReturServiceImpl implements ReturService {

    @Autowired
    private ReturDb returDb;

    @Override
    public ReturUpdtDto makeReturUpdateDTO(ReturObatModel retur) {
        ReturUpdtDto dto = new ReturUpdtDto();
        dto.setKode(retur.getReturId());
        dto.setObatlama(retur.getObatLama().getObatDetailId().getIdObat().getNamaObat());
        dto.setObatbaru(retur.getObatBaru().getObatDetailId().getIdObat().getNamaObat());
        dto.setKuantitasbaru(retur.getJumlahObatBaruDitukar());
        dto.setKuantitaslama(retur.getJumlahObatLamaDitukar());
        return dto;
    }

    @Override
    public void add(ReturObatModel retur) {
        returDb.save(retur);
    }

    @Override
    public List<ReturObatModel> getListRetur() {
        return returDb.findAll();
    }

    @Override
    public ReturObatModel getReturById(Long idRetur) {
        Optional<ReturObatModel> retur = returDb.findById(idRetur);
        if (retur.isPresent()) {
            return retur.get();
        } else return null;
    }

    @Override
    public ReturObatModel verifikasiRetur(ReturObatModel returObat, String status) {
        returObat.setStatus(status);
        return returDb.save(returObat);
    }
}
