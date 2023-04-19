package propensi.b02.sobatarlydia.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class ObatUpdtDTO {
    public String idobat;
    public int kodebatch;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate tanggalkadaluarsa;

    public int jumlahbox;
    public String satuanperbox;
    public int jumlahperbox;
}
