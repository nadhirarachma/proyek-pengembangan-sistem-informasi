package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.repository.ObatDetailDb;
import propensi.b02.sobatarlydia.rest.ObatWaiting;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObatDetailServiceImpl implements ObatDetailService{

    @Autowired
    ObatDetailDb obatDetailDB;

    @Override
    public List<ObatDetailModel> getAllObatDetail() {
        return obatDetailDB.findAll();
    }

    @Override
    public List<ObatWaiting> getAllObatWaiting() {
        List<ObatWaiting> waitingList = new ArrayList<>();
        for (int i = 0; i < getAllObatDetail().size(); i++) {
            if (getAllObatDetail().get(i).getStatusKonfirmasi().equals("Menunggu")){
                ObatWaiting obat = new ObatWaiting();
                obat.setObatDetailId(getAllObatDetail().get(i).getObatDetailId().toString());
                obat.setJumlahperbox(getAllObatDetail().get(i).getJumlahPerBox());
                obat.setJumlahbox(getAllObatDetail().get(i).getJumlahBox());
                obat.setKodebatch(getAllObatDetail().get(i).getObatDetailId().getKodeBatch());
                obat.setStok(getAllObatDetail().get(i).getStokTotal());
                obat.setFarmasi(getAllObatDetail().get(i).getObatDetailId().getIdObat().getFarmasi());
                obat.setSatuanperbox(getAllObatDetail().get(i).getSatuanPerBox());
                obat.setNamaobat(getAllObatDetail().get(i).getObatDetailId().getIdObat().getNamaObat());
                obat.setTanggalexp(getAllObatDetail().get(i).getTanggalKadaluarsa());
                obat.setStatuskonfirmasi(getAllObatDetail().get(i).getStatusKonfirmasi());
                waitingList.add(obat);
            }
        }
        return waitingList;
    }


}
