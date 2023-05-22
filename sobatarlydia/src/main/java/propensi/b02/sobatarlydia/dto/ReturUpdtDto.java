package propensi.b02.sobatarlydia.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReturUpdtDto {
    @JsonProperty("kode")
    private Long kode;

    @JsonProperty("obatlama")
    private String obatlama;

    @JsonProperty("kuantitasama")
    private int kuantitaslama;

    @JsonProperty("obatbaru")
    private String obatbaru;

    @JsonProperty("kuantitasbaru")
    private int kuantitasbaru;
}
