package propensi.b02.sobatarlydia.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObatWaiting {

    private String obatDetailId;

    private int kodebatch;

    private String farmasi;

    private String namaobat;

    private LocalDate tanggalexp;

    private int stok;

    private int jumlahperbox;

    private int jumlahbox;

    private String satuanperbox;

    private String statuskonfirmasi;
}
