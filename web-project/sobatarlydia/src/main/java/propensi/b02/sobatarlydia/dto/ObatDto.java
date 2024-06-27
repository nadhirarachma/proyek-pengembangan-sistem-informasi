package propensi.b02.sobatarlydia.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObatDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("nama_obat")
    private String namaObat;

    @JsonProperty("harga")
    private String harga;

    @JsonProperty("bentuk")
    private String bentuk;

    @JsonProperty("kategori")
    private String kategori;

    @JsonProperty("farmasi")
    private String farmasi;
}
