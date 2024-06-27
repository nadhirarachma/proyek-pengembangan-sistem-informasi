package propensi.b02.sobatarlydia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReturDto {
    
    @JsonProperty("kode")
    private Long kode;

    @JsonProperty("tanggal")
    private String tanggal;

    @JsonProperty("obatLama")
    private String obatLama;

    @JsonProperty("kuantitasLama")
    private String kuantitasLama;

    @JsonProperty("hargaLama")
    private String hargaLama;

    @JsonProperty("obatBaru")
    private String obatBaru;

    @JsonProperty("kuantitasBaru")
    private String kuantitasBaru;

    @JsonProperty("hargaBaru")
    private String hargaBaru;

    @JsonProperty("status")
    private String status;

    @JsonProperty("feedback")
    private String feedback;
}
