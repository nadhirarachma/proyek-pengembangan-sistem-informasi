package propensi.b02.sobatarlydia.rest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import propensi.b02.sobatarlydia.model.ObatDetailId;

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
