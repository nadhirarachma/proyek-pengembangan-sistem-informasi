package propensi.b02.sobatarlydia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PenjualanBulananDto {
    @JsonProperty("obat")
    private String obat;
    
    @JsonProperty("kuantitas")
    private int kuantitas;

}
