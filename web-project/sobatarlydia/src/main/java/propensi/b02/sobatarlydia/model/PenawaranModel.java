package propensi.b02.sobatarlydia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="penawaran")
public class PenawaranModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull
    @Column(name = "nama_obat", nullable = false)
    private String namaObat;

    @Column(name = "tanggal")
    private LocalDate tanggal;

    @NotNull
    @Column(name = "harga", nullable = false)
    private int harga;

    @NotNull
    @Column(name = "jumlah_box", nullable = false)
    private int jumlahBox;

    @Column(name = "tanggalKadaluarsa")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate tanggalKadaluarsa;

    @Column(name = "status_penawaran", nullable = false)
    private String statusPenawaran;

    // Relasi dengan PenggunaModel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "distributor", referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PenggunaModel distributor;
}
