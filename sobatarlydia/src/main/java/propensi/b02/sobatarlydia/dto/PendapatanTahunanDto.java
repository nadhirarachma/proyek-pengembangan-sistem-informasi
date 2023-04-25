package propensi.b02.sobatarlydia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PendapatanTahunanDto {
    @JsonProperty("tanggal")
    private String tanggal;
    
    @JsonProperty("total")
    private int total;
}
