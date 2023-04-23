package propensi.b02.sobatarlydia.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import propensi.b02.sobatarlydia.dto.ObatDto;
import propensi.b02.sobatarlydia.dto.RiwayatDto;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.model.RiwayatObatModel;
import propensi.b02.sobatarlydia.service.ObatService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/obat")
public class ObatRestController {

    @Autowired
    private ObatService obatService;

    @GetMapping(value="/by-farmasi")
    public List<ObatDto> findObatByFarmasi(@RequestParam(value = "farmasi", required = true) String farmasi) {
        List<ObatModel> lst = obatService.getObatByFarmasi(farmasi);
        return lst.stream().map(obat -> {
            ObatDto dto = new ObatDto();
            dto.setNamaObat(obat.getNamaObat());
            return dto;
        })
        .collect(Collectors.toList());
    }
    
    @GetMapping(value="/list-all")
    public List<ObatDto> findAllObat() {
        List<ObatModel> lst = obatService.getListObat();
        return lst.stream().map(obat -> {
            ObatDto dto = new ObatDto();
            dto.setId(obat.getIdObat());
            dto.setNamaObat(obat.getNamaObat());
            dto.setHarga(obat.getHarga());
            dto.setBentuk(obat.getBentukObat());
            dto.setKategori(obat.getKategori().getNamaKategori());
            dto.setFarmasi(obat.getFarmasi());
            return dto;
        })
        .collect(Collectors.toList());
    }
    
    @GetMapping(value="/kategori")
    public List<ObatDto> perKategori(@RequestParam(value = "kategori", required = true) int kategori) {
        List<ObatModel> lst = obatService.getListObatFiltered(kategori);
        if (kategori == 0) {
            lst = obatService.getListObat();
        } 
        return lst.stream().map(obat -> {
            ObatDto dto = new ObatDto();
            dto.setId(obat.getIdObat());
            dto.setNamaObat(obat.getNamaObat());
            dto.setHarga(obat.getHarga());
            dto.setBentuk(obat.getBentukObat());
            dto.setKategori(obat.getKategori().getNamaKategori());
            dto.setFarmasi(obat.getFarmasi());
            return dto;
        })
        .collect(Collectors.toList());
    }



    @GetMapping(value = "/riwayat")
    public List<RiwayatDto> riwayat(@RequestParam(value = "obat", required = true) String id) {
        ObatModel obat = obatService.getObatById(id);
        List<RiwayatObatModel> lst = obat.getListRiwayat();
        return lst.stream().map(riwayat -> {
            RiwayatDto dto = new RiwayatDto();
            dto.setKategori(riwayat.getKategori());
            dto.setObat(obat.getNamaObat());
            dto.setInfo(riwayat.getInformasiPerubahan());
            dto.setPeubah(riwayat.getPeubah().getNamaDepan() + " " + riwayat.getPeubah().getNamaBelakang());
            dto.setTanggal(LocalDate.of(riwayat.getKey().getWaktuPerubahan().getYear(), riwayat.getKey().getWaktuPerubahan().getMonth(), riwayat.getKey().getWaktuPerubahan().getDayOfMonth()));
            dto.setWaktu(LocalTime.of(riwayat.getKey().getWaktuPerubahan().getHour(), riwayat.getKey().getWaktuPerubahan().getMinute(), riwayat.getKey().getWaktuPerubahan().getSecond()));
            if (riwayat.getObatDetail() != null) {
                dto.setDetail("BATCH " + Integer.toString(riwayat.getObatDetail().getObatDetailId().getKodeBatch()));
            }

            dto.setStatchange(riwayat.getStatusChange());
            return dto;
        })
        .collect(Collectors.toList());
    }

    
}
