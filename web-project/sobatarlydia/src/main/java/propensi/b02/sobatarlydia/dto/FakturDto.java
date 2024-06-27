package propensi.b02.sobatarlydia.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FakturDto {
    @JsonProperty("no_faktur")
    private String noFaktur;
    
    @JsonProperty("farmasi")
    private String farmasi;

    @JsonProperty("tanggal")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggal;

    @JsonProperty("tanggalJatuhTempo")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalJatuhTempo;

    @JsonProperty("obat")
    private List<ObatDetailDto> obat;
}
