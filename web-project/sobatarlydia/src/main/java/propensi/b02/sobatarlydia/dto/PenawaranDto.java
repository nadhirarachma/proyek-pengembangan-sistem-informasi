package propensi.b02.sobatarlydia.dto;

import java.time.LocalDate;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class PenawaranDto {
    private String namaperusahaan;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalpenawaran;

    private String namaobat;

    private int jumlah;

    private int harga;

    private int totalharga;

    private String statuspenawaran;

    private long id;

}
