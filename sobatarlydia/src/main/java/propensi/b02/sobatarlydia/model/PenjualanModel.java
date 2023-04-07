package propensi.b02.sobatarlydia.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name="penjualan")
public class PenjualanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "karyawan")
    private PenggunaModel karyawan;

    @Column(name = "kuantitas")
    private int kuantitas;

    @Column(name = "waktu")
    private LocalDateTime waktu;

    // Relasi dengan ObatDetailModel
    @OneToMany(mappedBy = "noPenjualan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObatDetailModel> listObatDetail;
}
