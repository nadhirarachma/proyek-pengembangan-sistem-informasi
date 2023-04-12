package propensi.b02.sobatarlydia.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(name = "harga")
    private long harga;

    @Column(name = "waktu")
    private LocalDateTime waktu;

    @JsonIgnore
    @OneToMany(mappedBy = "id.penjualan")
    private List<KuantitasModel> kuantitas;
}
