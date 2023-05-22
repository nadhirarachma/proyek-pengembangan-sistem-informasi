package propensi.b02.sobatarlydia.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class InputReturDto {
    @JsonProperty("tanggal_penjualan")
    LocalDate tanggalPenjualan;
    
    @JsonProperty("id_penjualan")
    long idPenjualan;
    
    @JsonProperty("obat_lama")
    String obatlama;

    @JsonProperty("kuantitas_obat_lama")
    int kuantitasobatlama;

    @JsonProperty("obat_baru")
    String obatbaru;

    @JsonProperty("kuantitas_obat_baru")
    int kuantitasobatbaru;
}
