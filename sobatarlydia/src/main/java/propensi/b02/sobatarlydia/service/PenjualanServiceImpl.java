package propensi.b02.sobatarlydia.service;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.PenjualanModel;
import propensi.b02.sobatarlydia.model.KuantitasModel;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
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

    @Override
    public HashMap<ObatModel, Integer> getListPenjualanByDate(LocalDate date) {
        List<PenjualanModel> lst = penjualanDb.findAll();
        HashMap<ObatModel, Integer> byDate = new HashMap<ObatModel, Integer>();

        for (PenjualanModel p: lst) {
            if (p.getWaktu().getDayOfYear() == date.getDayOfYear() && p.getWaktu().getMonth() == date.getMonth() && p.getWaktu().getYear() == date.getYear()) {
                for (KuantitasModel k: p.getKuantitas()) {
                    byDate.put(k.getId().getObat().getObatDetailId().getIdObat(), byDate.containsKey(k.getId().getObat().getObatDetailId().getIdObat()) ? byDate.get(k.getId().getObat().getObatDetailId().getIdObat()) + Integer.valueOf(k.getKuantitas()) : Integer.valueOf(k.getKuantitas()));
                }
            }
        }
        return byDate;
    }

    @Override
    public HashMap<LocalDate, Integer> getListPendapatanByMonth(int month, int year) {
        List<PenjualanModel> lst = penjualanDb.findAll();
        HashMap<LocalDate, Integer> byMonth = new HashMap<LocalDate, Integer>();

        for (PenjualanModel p: lst) {
            
            if (p.getWaktu().getMonthValue() == month && p.getWaktu().getYear() == year) {
                LocalDate tgl = LocalDate.of(year, month, p.getWaktu().getDayOfMonth());
                for (KuantitasModel k: p.getKuantitas()) {
                    byMonth.put(tgl, byMonth.containsKey(tgl) ? Integer.valueOf(byMonth.get(tgl) + (k.getKuantitas() * k.getId().getObat().getObatDetailId().getIdObat().getHarga())) : Integer.valueOf(k.getKuantitas() * k.getId().getObat().getObatDetailId().getIdObat().getHarga()));
                }
            }
        }

        return byMonth;
    }
    @Override
    public HashMap<ObatModel, Integer> getListPenjualanByMonth(int month, int year) {
        List<PenjualanModel> lst = penjualanDb.findAll();
        HashMap<ObatModel, Integer> byDate = new HashMap<ObatModel, Integer>();

        for (PenjualanModel p: lst) {
            if (p.getWaktu().getMonthValue() == month && p.getWaktu().getYear() == year) {
                for (KuantitasModel k: p.getKuantitas()) {
                    byDate.put(k.getId().getObat().getObatDetailId().getIdObat(), byDate.containsKey(k.getId().getObat().getObatDetailId().getIdObat()) ? byDate.get(k.getId().getObat().getObatDetailId().getIdObat()) + Integer.valueOf(k.getKuantitas()) : Integer.valueOf(k.getKuantitas()));
                }
            }
        }
        return byDate;
    }

    @Override
    public HashMap<ObatModel, Integer> getListPenjualanByYear(int year) {
        List<PenjualanModel> lst = penjualanDb.findAll();
        HashMap<ObatModel, Integer> byDate = new HashMap<ObatModel, Integer>();

        for (PenjualanModel p: lst) {
            if (p.getWaktu().getYear() == year) {
                for (KuantitasModel k: p.getKuantitas()) {
                    byDate.put(k.getId().getObat().getObatDetailId().getIdObat(), byDate.containsKey(k.getId().getObat().getObatDetailId().getIdObat()) ? byDate.get(k.getId().getObat().getObatDetailId().getIdObat()) + Integer.valueOf(k.getKuantitas()) : Integer.valueOf(k.getKuantitas()));
                }
            }
        }
        return byDate;
    }
    @Override
    public HashMap<String, Integer> getListPendapatanByYear(int year) {
        List<PenjualanModel> lst = penjualanDb.findAll();
        HashMap<String, Integer> byYear = new HashMap<String, Integer>();

        for (PenjualanModel p: lst) {
            if (p.getWaktu().getYear() == year) {
                for (KuantitasModel k: p.getKuantitas()) {
                    byYear.put(p.getWaktu().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH), byYear.containsKey(p.getWaktu().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)) ? Integer.valueOf(byYear.get(p.getWaktu().getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH)) + (k.getKuantitas() * k.getId().getObat().getObatDetailId().getIdObat().getHarga())) : Integer.valueOf(k.getKuantitas() * k.getId().getObat().getObatDetailId().getIdObat().getHarga()));
                }
            }
        }

        return byYear;
    }

    @Override
    public List<PenjualanModel> getListPerTanggal(LocalDate date) {
        List<PenjualanModel> lst = penjualanDb.findAll();
        List<PenjualanModel> baru = new ArrayList<>();

        for (PenjualanModel p: lst) {
            if (p.getWaktu().getDayOfYear() == date.getDayOfYear() && p.getWaktu().getMonth() == date.getMonth() && p.getWaktu().getYear() == date.getYear()) {
                baru.add(p);
            }
        }
        return baru;
    }

    public ObatDetailModel getObatForRetur(PenjualanModel penjualan, String obatlama){
        List<KuantitasModel> lst = penjualan.getKuantitas();
        
        for (KuantitasModel i : lst) {
            if (i.getId().getObat().getObatDetailId().getIdObat().getNamaObat().equals(obatlama)) {
                return i.getId().getObat();
            }
        }

        return null;
    }

}
