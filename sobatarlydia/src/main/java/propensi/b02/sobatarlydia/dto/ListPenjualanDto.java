package propensi.b02.sobatarlydia.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ListPenjualanDto {
    @JsonProperty("kode")
    private Long kode;

    @JsonProperty("obatlama")
    private String obatlama;
}
