package propensi.b02.sobatarlydia.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ListPenjualanDto {
    @JsonProperty("kode")
    private Long kode;

    @JsonProperty("obatlama")
    private String obatlama;
}
