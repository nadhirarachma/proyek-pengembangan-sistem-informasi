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
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pengguna")
public class PenggunaModel implements Serializable {
    @Id
    @Column(name = "email", nullable = false)
    private String email;
    
    @NotNull
    @Column(name = "nama_depan", nullable = false)
    private String namaDepan;

    @Column(name = "nama_belakang")
    private String namaBelakang;

    @Column(name = "nama_perusahaan")
    private String namaPerusahaan;

    @NotNull
    @Column(name = "nomor_telepon", nullable = false)
    private String nomorTelepon;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column(name = "role", nullable = false)
    private String role;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private int isActive;

    // Relasi dengan RiwayatObatModel
    @OneToMany(mappedBy = "peubah", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RiwayatObatModel> listRiwayatObat;

    // Relasi dengan PenawaranModel
    @OneToMany(mappedBy = "distributor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PenawaranModel> listPenawaran;
}
