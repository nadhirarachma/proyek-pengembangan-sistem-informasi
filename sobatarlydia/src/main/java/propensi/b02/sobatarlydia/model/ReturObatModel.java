package propensi.b02.sobatarlydia.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="retur")
public class ReturObatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long returId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "penjualan_id", referencedColumnName = "id")
    private PenjualanModel penjualan;

    @Column(name = "waktu")
    private LocalDateTime waktu;

    @Column(name = "obat_lama")
    private ObatDetailModel obatLama; 

    @Column(name = "jml_obt_lama_ditukar")
    private int jumlahObatLamaDitukar;

    // @OneToMany(mappedBy = "id")
    // private List<ReturKuantitas> obatLama;
    
    // @OneToMany(mappedBy = "id")
    // private List<ReturKuantitas2> obatBaru;
    
    @Column(name = "obat_baru")
    private ObatDetailModel obatBaru;

    @Column(name = "jml_obt_baru_ditukar")
    private int jumlahObatBaruDitukar;
}
