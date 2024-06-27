package propensi.b02.sobatarlydia.dto;


import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RiwayatDto {
    @JsonProperty("obat")
    private String obat;

    @JsonProperty("info")
    private String info;

    @JsonProperty("peubah")
    private String peubah;

    @JsonProperty("tanggal")
    private LocalDate tanggal;

    @JsonProperty("waktu")
    private LocalTime waktu;

    @JsonProperty("detail")
    private String detail;

    @JsonProperty("statchange")
    private String statchange;

    @JsonProperty("kategori")
    private String kategori;
}
