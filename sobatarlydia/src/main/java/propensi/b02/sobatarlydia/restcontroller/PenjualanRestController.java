package propensi.b02.sobatarlydia.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import propensi.b02.sobatarlydia.controller.ObatDetailController;
import propensi.b02.sobatarlydia.dto.*;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.model.RiwayatObatModel;
import propensi.b02.sobatarlydia.service.ObatService;
import propensi.b02.sobatarlydia.service.PenjualanService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

        System.out.println(ph);
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
        System.out.println("tes");
        return ph;
    }
    
    @GetMapping("/bulanan")
    public List<PendapatanBulananDto> pendapatanBulanan(@RequestParam(value = "month", required = true) String month) {
        int year = Integer.parseInt(month.split("-")[0]);
        int monthh = Integer.parseInt(month.split("-")[1]);

        HashMap<LocalDate, Integer> map = penjualanService.getListPendapatanByMonth(monthh, year);
        List<PendapatanBulananDto> ph = new ArrayList<>();
        
        System.out.println(ph);
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

        System.out.println(ph);
        for (Map.Entry<String, Integer> set : map.entrySet()) {
            PendapatanTahunanDto phNew = new PendapatanTahunanDto();
            phNew.setTanggal(set.getKey());
            phNew.setTotal(set.getValue());

            ph.add(phNew);
        }

        return ph;
    }
    
}
