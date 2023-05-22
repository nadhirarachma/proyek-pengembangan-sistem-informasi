package propensi.b02.sobatarlydia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PenjualanHarianDto {
    @JsonProperty("obat")
    private String obat;
    
    @JsonProperty("kuantitas")
    private int kuantitas;

    @JsonProperty("harga")
    private String harga;
}
