package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.dto.ObatUpdtDTO;
import propensi.b02.sobatarlydia.model.ObatDetailId;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
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
            if (!(getAllObatDetail().get(i).getStatusKonfirmasi().equals("Diterima"))){
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

    @Override
    public void addObatDetail(ObatDetailModel obat) {
        obatDetailDB.save(obat);
    }

    @Override
    public ObatDetailModel getObatDetailByObatDetailId(ObatDetailId kode) {
        return obatDetailDB.findByObatDetailId(kode).get();
    }

    @Override
    public ObatDetailModel updateObatDetail(ObatDetailModel obatDetail) {
        return obatDetailDB.save(obatDetail);
    }

    @Override
    public ObatDetailModel makeSetterDetail(ObatDetailModel obatDetailPast, ObatUpdtDTO obat) {
        obatDetailPast.setJumlahBox(obat.getJumlahbox());
        obatDetailPast.setJumlahPerBox(obat.getJumlahperbox());
        obatDetailPast.setSatuanPerBox(obat.getSatuanperbox());
        obatDetailPast.setTanggalKadaluarsa(obat.getTanggalkadaluarsa());
        obatDetailPast.setStokTotal(obat.getJumlahbox() * obat.getJumlahperbox());
        obatDetailPast.setStatusKonfirmasi("Menunggu");
        return obatDetailPast;
    }

    @Override
    public ObatDetailModel updateToArsip(ObatDetailModel obatDetail) {
        obatDetail.setStatusKonfirmasi("Arsipkan");
        return updateObatDetail(obatDetail);
    }

    @Override
    public ObatDetailModel getObatDetailByIdObat(ObatModel idObat) {
    
        List<ObatDetailModel> allObatDetail = getAllObatDetail();
        for (int i=0; i < allObatDetail.size(); i++) {
            if (allObatDetail.get(i).getObatDetailId().getIdObat() == idObat) {
                return allObatDetail.get(i);
            }
        }
        return null;
    }

    @Override
    public ObatDetailModel getObatDetailByKodeBatch(ObatModel idObat, int kodeBatch) {
        List<ObatDetailModel> allObatDetail = getAllObatDetail();
        for (int i=0; i < allObatDetail.size(); i++) {
            if (allObatDetail.get(i).getObatDetailId().getIdObat() == idObat && allObatDetail.get(i).getObatDetailId().getKodeBatch() == kodeBatch) {
                return allObatDetail.get(i);
            }
        }
        return null;
    }
}
