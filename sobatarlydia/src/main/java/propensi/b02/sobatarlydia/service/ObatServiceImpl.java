package propensi.b02.sobatarlydia.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import propensi.b02.sobatarlydia.dto.ObatUpdtDTO;
import propensi.b02.sobatarlydia.model.KategoriObatModel;
import propensi.b02.sobatarlydia.model.ObatDetailId;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.repository.KategoriObatDb;
import propensi.b02.sobatarlydia.repository.ObatDb;
import propensi.b02.sobatarlydia.repository.ObatDetailDb;

@Service
@Transactional
public class ObatServiceImpl implements ObatService {
    @Autowired
    ObatDb obatDb;

    @Autowired
    ObatDetailDb obatDetailDb;

    @Autowired
    KategoriObatDb kategoriObatDb;


    public ObatModel addObat(ObatModel obat) {
        obatDb.save(obat);
        return obat;
    }

    public ObatDetailModel addObatDetail(ObatDetailModel obat) {
        obatDetailDb.save(obat);
        return obat;
    }


    public ObatModel setId(ObatModel obat) {
        String nama = "";
        String[] namas = obat.getNamaObat().split(" ");

        for (String i : namas) {
            if (i.length() >= 3) {
                nama += i.substring(0, 3).toUpperCase();
            } else {
                nama += i.toUpperCase();
            }

        }

        String farmasi = "";
        if (obat.getFarmasi().length() >= 3) {
            farmasi += obat.getFarmasi().substring(0, 3).toUpperCase();
        } else {
            farmasi += obat.getFarmasi().toUpperCase();
        }
        obat.setIdObat(nama + farmasi);
        System.out.println(obat.getIdObat());

        return obat;
    }



    @Override
    public List<ObatModel> getListObat() {
        return obatDb.findAll();
    }

    public List<ObatModel> getObatByFarmasi(String farmasi) {
        System.out.println(farmasi);
        System.out.println("AAAAAA");
        List<ObatModel> lst = new ArrayList<>();
        try {
            lst = obatDb.findByFarmasi(farmasi);
        } catch (Exception e) {
            System.out.println("--");
        }
        
        return lst;
    }

    public ObatModel getObatByNamaDanFarmasi(String nama, String farmasi) {
        List<ObatModel> listObat = obatDb.findByFarmasi(farmasi);

        for (ObatModel obat : listObat) {
            if (obat.getNamaObat().equals(nama)) {
                System.out.println(obat.getNamaObat());
                return obat;
            }
        }
        return null;

    }

    @Override
    public ObatModel getObatById(String idObat){
        Optional<ObatModel> obat = obatDb.findById(idObat);
        if(obat.isPresent()){
            return obat.get();
        } else return null;
    }

    @Override
    public List<ObatModel> getListObatFiltered(Integer no) {
        return obatDb.findKategoriFiltered(no);
    }

    @Override
    public List<KategoriObatModel> getListKategori() {
        return kategoriObatDb.findAll();
    }

    @Override
    public KategoriObatModel getKategoriById(Integer no){
        Optional <KategoriObatModel> kategori = kategoriObatDb.findByNo(no);
        return kategori.orElse(null);
    }
    @Override
    public ObatDetailModel getDetailObat(ObatDetailId idObat)
    {
        return obatDetailDb.findByObatDetailId(idObat).get();
    }

    @Override
    public ObatDetailModel updateObatDitolak(ObatDetailModel tolak) {
        tolak.setStatusKonfirmasi("Ditolak");
        return obatDetailDb.save(tolak);
    }

    @Override
    public ObatDetailModel updateObatDiterima(ObatDetailModel terima) {
        terima.setStatusKonfirmasi("Diterima");
        return obatDetailDb.save(terima);    }

    @Override
    public ObatUpdtDTO makeObatUpdtDTO(ObatDetailModel obatDetail, String obatDetailId, int kodeBatch) {
        ObatUpdtDTO dto = new ObatUpdtDTO();
        dto.setJumlahperbox(obatDetail.getJumlahPerBox());
        dto.setSatuanperbox(obatDetail.getSatuanPerBox());
        dto.setIdobat(obatDetailId);
        dto.setKodebatch(kodeBatch);
        dto.setJumlahbox(obatDetail.getJumlahBox());
        dto.setTanggalkadaluarsa(obatDetail.getTanggalKadaluarsa());
        return dto;
    }

    @Override
    public List<ObatModel> getListObatDiterimaDanTersedia() {
        List<ObatModel> daftarObat = obatDb.findAll();
        List<ObatModel> obatDiterimaDanTersedia = new ArrayList<ObatModel>();
        Set<ObatModel> obatDiterimaDanTersediaNoDup = new HashSet<ObatModel>();

        for (int i = 0; i < daftarObat.size(); i++) {
            for (int j = 0; j < daftarObat.get(i).getListDetailObat().size(); j++) {
                if (daftarObat.get(i).getListDetailObat().get(j).getStatusKonfirmasi().equals("Diterima") && daftarObat.get(i).getListDetailObat().get(j).getStatus().equals("Tersedia")) {
                    obatDiterimaDanTersediaNoDup.add(daftarObat.get(i));
                }
            }
        }
        
        obatDiterimaDanTersedia.addAll(obatDiterimaDanTersediaNoDup);
        return obatDiterimaDanTersedia;
    }
}
