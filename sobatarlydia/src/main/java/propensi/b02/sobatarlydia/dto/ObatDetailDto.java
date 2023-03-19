package propensi.b02.sobatarlydia.dto;


import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObatDetailDto {
    @JsonProperty("farmasi")
    public String farmasi;

    @JsonProperty("nama_obat")
    public String namaObat;

    @JsonProperty("tanggal_kadaluarsa")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate tanggalKadaluarsa;

    @JsonProperty("jumlah_box")
    public int jumlahBox;

    @JsonProperty("satuan_per_box")
    private String satuanPerBox;
    
    @JsonProperty("jumlah_per_box")
    private int jumlahPerBox;

}
