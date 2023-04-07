package propensi.b02.sobatarlydia.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="obat_detail")
public class ObatDetailModel implements Serializable {

    @EmbeddedId
    private ObatDetailId obatDetailId;

    @NotNull
    @Column(name = "status", nullable = false)
    private String status;

    @NotNull
    @Column(name = "status_konfirmasi", nullable = false)
    private String statusKonfirmasi;

    @NotNull
    @Column(name = "tanggal_kadaluarsa")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate tanggalKadaluarsa;

    @NotNull
    @Column(name = "jumlah_box", nullable = false)
    private int jumlahBox;
    
    @NotNull
    @Column(name = "satuan_per_box", nullable = false)
    private String satuanPerBox;
    
    @NotNull
    @Column(name = "jumlah_per_box", nullable = false)
    private int jumlahPerBox;

    @NotNull
    @Column(name = "stok_total", nullable = false)
    private int stokTotal;

    // Relasi dengan FakturModel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no_faktur", referencedColumnName = "no_faktur")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FakturModel noFaktur;

    // Relasi dengan PenjualanModel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no_penjualan", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PenjualanModel noPenjualan;
}
