package propensi.b02.sobatarlydia.service;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.model.PenggunaModel;
import propensi.b02.sobatarlydia.model.RiwayatKey;
import propensi.b02.sobatarlydia.model.RiwayatObatModel;
import propensi.b02.sobatarlydia.repository.RiwayatDb;

@Service
@Transactional
public class RiwayatServiceImpl implements RiwayatService {
    @Autowired
    RiwayatDb riwayatDb;

    @Override
    public RiwayatObatModel addRiwayat(RiwayatObatModel riwayat) {
        riwayatDb.save(riwayat);
        return riwayat;
    }

    @Override
    public void record(ObatModel obat, PenggunaModel akun, String info) {
        RiwayatObatModel riwayat = new RiwayatObatModel();
        RiwayatKey key = new RiwayatKey();
        key.setIdObat(obat);
        key.setWaktuPerubahan(LocalDateTime.now());
        key.setInfo(info);

        riwayat.setKey(key);
        riwayat.setPeubah(akun);

        if (info.equals("daftar")) {
            riwayat.setKategori("Daftar");
            riwayat.setInformasiPerubahan("Obat " + obat.getIdObat() + " didaftarkan");
        } else if (info.equals("stok obat habis")) {
            riwayat.setKategori("Stok Obat Habis");
            riwayat.setInformasiPerubahan("Stok obat " + obat.getIdObat() + " habis.");
            riwayat.setStatusChange("Tersedia → Kosong");
        }
        addRiwayat(riwayat);
    }

    @Override
    public void record(ObatModel obat, ObatDetailModel obatDetail, PenggunaModel akun, String info) {
        RiwayatObatModel riwayat = new RiwayatObatModel();
        RiwayatKey key = new RiwayatKey();
        key.setIdObat(obat);
        key.setWaktuPerubahan(LocalDateTime.now());
        key.setInfo(info);


        riwayat.setKey(key);
        riwayat.setObatDetail(obatDetail);
        riwayat.setPeubah(akun);

        if (info.equals("input detail")) {
            riwayat.setKategori("Input Data");
            riwayat.setInformasiPerubahan("Batch " + obatDetail.getObatDetailId().getKodeBatch() + " diinput.");
            riwayat.setStatusChange("Menunggu Konfirmasi Apoteker");
        } else if (info.equals("terima")) {
            riwayat.setKategori("Update Status");
            riwayat.setInformasiPerubahan("Batch " + obatDetail.getObatDetailId().getKodeBatch() + " diterima. Stok " + obat.getNamaObat() + " bertambah sebanyak " + obatDetail.getStokTotal());
            riwayat.setStatusChange("Menunggu → Diterima");
        } else if (info.equals("tolak")) {
            riwayat.setKategori("Update Status");
            riwayat.setInformasiPerubahan("Batch " + obatDetail.getObatDetailId().getKodeBatch() + " ditolak. Menunggu data direvisi.");
            riwayat.setStatusChange("Menunggu → Ditolak");
        } else if (info.equals("revisi")) {
            riwayat.setKategori("Update Status");
            riwayat.setInformasiPerubahan("Batch " + obatDetail.getObatDetailId().getKodeBatch() + " telah direvisi. Menunggu konfirmasi ulang.");
            riwayat.setStatusChange("Ditolak → Menunggu");
        } else if (info.equals("stok batch habis" + obatDetail.getObatDetailId().getKodeBatch())) {
            riwayat.setKategori("Stok Batch Habis");
            riwayat.setInformasiPerubahan("Stok batch " + obatDetail.getObatDetailId().getKodeBatch() + " habis.");
            riwayat.setStatusChange("Tersedia → Kosong");
        }
        addRiwayat(riwayat);
    }

    @Override
    public void record(ObatModel obat, ObatDetailModel obatDetail, PenggunaModel akun, String info, int kuantitas) {
        RiwayatObatModel riwayat = new RiwayatObatModel();
        RiwayatKey key = new RiwayatKey();
        key.setIdObat(obat);
        key.setWaktuPerubahan(LocalDateTime.now());
        key.setInfo(info);

        
        riwayat.setKey(key);
        riwayat.setObatDetail(obatDetail);
        riwayat.setPeubah(akun);
        
        if (info.equals("jual" + obatDetail.getObatDetailId().getKodeBatch())) {
            riwayat.setKategori("Penjualan");
            riwayat.setInformasiPerubahan("Batch " + obatDetail.getObatDetailId().getKodeBatch() + " terjual sebanyak " + Integer.toString(kuantitas) + ". Stok diperbarui.");
        } else if (info.equals("arsip")) {
            riwayat.setKategori("Update Status");
            riwayat.setInformasiPerubahan("Batch " + obatDetail.getObatDetailId().getKodeBatch() + " dengan kuantitas " + Integer.toString(kuantitas) + " diarsipkan. Stok diperbarui.");
            riwayat.setStatusChange("Tersedia → Diarsipkan");
        } else if (info.equals("batal arsip")) {
            riwayat.setKategori("Update Status");
            riwayat.setInformasiPerubahan("Batch " + obatDetail.getObatDetailId().getKodeBatch() + " dengan kuantitas " + Integer.toString(kuantitas) + " batal diarsipkan. Stok diperbarui.");
            riwayat.setStatusChange("Diarsipkan → Tersedia");
        }
        addRiwayat(riwayat);

    }
}
