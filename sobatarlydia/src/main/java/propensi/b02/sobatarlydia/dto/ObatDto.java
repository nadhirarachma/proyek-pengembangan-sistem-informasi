package propensi.b02.sobatarlydia.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ObatDto {
    @JsonProperty("nama_obat")
    private String namaObat;
}
