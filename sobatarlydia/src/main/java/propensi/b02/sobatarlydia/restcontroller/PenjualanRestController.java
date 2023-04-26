package propensi.b02.sobatarlydia.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import propensi.b02.sobatarlydia.dto.*;
import propensi.b02.sobatarlydia.model.PenjualanModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.service.PenjualanService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.text.NumberFormat;
import java.util.Locale;


@RestController
@RequestMapping("/api/v1/penjualan")
public class PenjualanRestController {

    @Autowired
    private PenjualanService penjualanService;

    @GetMapping("/harian")
    public List<PenjualanHarianDto> laporanHarian(@RequestParam(value = "date", required = true) String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate datee = LocalDate.parse(date, formatter);

        HashMap<ObatModel, Integer> map = penjualanService.getListPenjualanByDate(datee);
        List<PenjualanHarianDto> ph = new ArrayList<>();

        for (Map.Entry<ObatModel, Integer> set : map.entrySet()) {
             PenjualanHarianDto phNew = new PenjualanHarianDto();
             phNew.setObat(set.getKey().getNamaObat());
             phNew.setKuantitas(set.getValue());
             phNew.setHarga(set.getKey().getHarga() * set.getValue());

             ph.add(phNew);
        }

        return ph;
    }
    @GetMapping("/bulan")
    public List<PenjualanBulananDto> laporanBulanan(@RequestParam(value = "month", required = true) String month) {
        int year = Integer.parseInt(month.split("-")[0]);
        int monthh = Integer.parseInt(month.split("-")[1]);

        HashMap<ObatModel, Integer> map = penjualanService.getListPenjualanByMonth(monthh, year);
        List<PenjualanBulananDto> ph = new ArrayList<>();

        for (Map.Entry<ObatModel, Integer> set : map.entrySet()) {
            PenjualanBulananDto phNew = new PenjualanBulananDto();
            phNew.setObat(set.getKey().getNamaObat());
            phNew.setKuantitas(set.getValue());

            ph.add(phNew);
        }
        return ph;
    }
    
    @GetMapping("/bulanan")
    public List<PendapatanBulananDto> pendapatanBulanan(@RequestParam(value = "month", required = true) String month) {
        int year = Integer.parseInt(month.split("-")[0]);
        int monthh = Integer.parseInt(month.split("-")[1]);

        HashMap<LocalDate, Integer> map = penjualanService.getListPendapatanByMonth(monthh, year);
        List<PendapatanBulananDto> ph = new ArrayList<>();
        
        for (Map.Entry<LocalDate, Integer> set : map.entrySet()) {
             PendapatanBulananDto phNew = new PendapatanBulananDto();
             phNew.setTanggal(set.getKey());
             phNew.setTotal(set.getValue());

             ph.add(phNew);
        }

        return ph;
    }

    @GetMapping("/tahun")
    public List<PenjualanTahunanDto> laporanTahunan(@RequestParam(value = "year", required = true) String year) {

        HashMap<ObatModel, Integer> map = penjualanService.getListPenjualanByYear(Integer.parseInt(year));
        List<PenjualanTahunanDto> ph = new ArrayList<>();

        for (Map.Entry<ObatModel, Integer> set : map.entrySet()) {
            PenjualanTahunanDto phNew = new PenjualanTahunanDto();
            phNew.setObat(set.getKey().getNamaObat());
            phNew.setKuantitas(set.getValue());

            ph.add(phNew);
        }
        return ph;
    }

    @GetMapping("/tahunan")
    public List<PendapatanTahunanDto> pendapatanTahunan(@RequestParam(value = "year", required = true) String year) {

        HashMap<String, Integer> map = penjualanService.getListPendapatanByYear(Integer.parseInt(year));
        List<PendapatanTahunanDto> ph = new ArrayList<>();

        for (Map.Entry<String, Integer> set : map.entrySet()) {
            PendapatanTahunanDto phNew = new PendapatanTahunanDto();
            phNew.setTanggal(set.getKey());
            phNew.setTotal(set.getValue());

            ph.add(phNew);
        }

        return ph;
    }

    @GetMapping(value="/list-all")
    public List<PenjualanDto> findAllObat() {
        List<PenjualanModel> lst = penjualanService.getListPenjualan();
        return lst.stream().map(penjualan -> {
            PenjualanDto dto = new PenjualanDto();
            dto.setKode(penjualan.getId());

            String tanggal = penjualan.getWaktu().toString().split("T")[0];
            String waktu = penjualan.getWaktu().toString().split("T")[1];
            dto.setTanggal(tanggal);
            dto.setWaktu(waktu);

            dto.setKaryawan(penjualan.getKaryawan().getNamaDepan() + " " + penjualan.getKaryawan().getNamaBelakang());

            NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
            String harga = nf.format(penjualan.getHarga());
            dto.setHarga("Rp"+harga);
            return dto;
        })
        .collect(Collectors.toList());
    }
    
    
}
