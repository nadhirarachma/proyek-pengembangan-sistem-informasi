package propensi.b02.sobatarlydia.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="obat")
public class ObatModel implements Serializable {
    @Id
    @Size(max = 50)
    @Column(name = "id_obat", nullable = false)
    private String idObat;

    @NotNull
    @Column(name = "nama_obat", nullable = false)
    private String namaObat;

    @NotNull
    @Column(name = "farmasi", nullable = false)
    private String farmasi;

    @NotNull
    @Column(name = "bentuk_obat", nullable = false)
    private String bentukObat;

    @NotNull
    @Column(name = "harga", nullable = false)
    private int harga;

    @NotNull
    @Column(name = "stok")
    private int stok;

    // Relasi dengan KategoriObatModel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "kategori", referencedColumnName = "no")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KategoriObatModel kategori;

    // Relasi dengan ObatDetailModel
    @OneToMany(mappedBy = "obatDetailId.idObat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObatDetailModel> listDetailObat;

    // Relasi dengan ObatDetailModel
    @OneToMany(mappedBy = "key.idObat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RiwayatObatModel> listRiwayat;
}
