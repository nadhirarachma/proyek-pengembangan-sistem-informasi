package propensi.b02.sobatarlydia.rest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ObatWaiting {

    private int kodebatch;

    private String farmasi;

    private String namaobat;

    private LocalDate tanggalexp;

    private int stok;

    private int jumlahperbox;

    private String satuanperbox;

    private String statuskonfirmasi;
}
