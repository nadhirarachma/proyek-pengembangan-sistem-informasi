package propensi.b02.sobatarlydia.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PendapatanBulananDto {
    @JsonProperty("tanggal")
    private LocalDate tanggal;
    
    @JsonProperty("total")
    private int total;
}
