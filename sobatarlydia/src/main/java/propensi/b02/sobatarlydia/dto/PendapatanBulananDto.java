package propensi.b02.sobatarlydia.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PendapatanBulananDto {
    @JsonProperty("tanggal")
    private LocalDate tanggal;
    
    @JsonProperty("total")
    private String total;
}
