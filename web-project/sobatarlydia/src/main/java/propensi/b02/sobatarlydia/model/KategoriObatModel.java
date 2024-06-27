package propensi.b02.sobatarlydia.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name="kategori_obat")
public class KategoriObatModel implements Serializable {
    
    @Id
    @Column(name = "no", nullable = false)
    private int no;
    
    @Column(name = "nama_kategori", nullable = false)
    private String namaKategori;

    // Relasi dengan ObatModel
    @OneToMany(mappedBy = "kategori", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObatModel> listObat;
}
