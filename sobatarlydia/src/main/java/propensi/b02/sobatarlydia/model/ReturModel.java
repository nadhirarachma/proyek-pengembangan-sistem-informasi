package propensi.b02.sobatarlydia.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="returr")
public class ReturModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "obat_awal")
    private String obatAwal;
    
    @Column(name = "harga_awal")
    private long hargaAwal;

    @Column(name = "kuantitas_awal")
    private int kuantitasAwal;

    @Column(name = "obat_akhir")
    private String obatAkhir;

    @Column(name = "harga_akhir")
    private long hargaAkhir;

    @Column(name = "kuantitas_akhir")
    private int kuantitasAkhir;

    @Column(name = "status")
    private String status;

    @Column(name = "tanggal")
    private LocalDate tanggal;

    @Column(name = "feedback")
    private String feedback;
}
