package propensi.b02.sobatarlydia.model;

import java.io.Serializable;
// import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
// import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
// import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="riwayat_obat")
public class RiwayatObatModel implements Serializable {
    
    @EmbeddedId
    private RiwayatKey key;

    // // Relasi dengan ObatModel
    // @Id
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "id_obat", referencedColumnName = "id_obat")
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // private ObatModel idObat;
    
    // Relasi dengan PenggunaModel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "peubah", referencedColumnName = "email")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PenggunaModel peubah;

    // @Id
    // @NotNull
    // @Column(name = "waktu_perubahan", nullable = false)
    // @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    // private LocalDateTime waktuPerubahan;

    @NotNull
    @Column(name = "informasi_perubahan", nullable = false)
    private String informasiPerubahan;

    @Column(name = "status_change")
    private String statusChange;

    @Column(name = "obat_detail")
    private ObatDetailModel obatDetail;

    @Column(name = "kategori")
    private String kategori;
}
