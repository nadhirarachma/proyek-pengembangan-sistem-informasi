package propensi.b02.sobatarlydia.model;

import java.io.Serializable;
import java.time.LocalDate;
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
@Table(name="faktur")
public class FakturModel implements Serializable {
    @Id
    @Column(name = "no_faktur")
    private String noFaktur;

    @Column(name = "farmasi")
    private String farmasi;

    @Column(name = "kodeBatch")
    private int kodeBatch;

    @Column(name = "tanggal")
    private LocalDate tanggal;

    @NotNull
    @Column(name = "status_faktur", nullable = false)
    private String statusFaktur;


    // Relasi dengan ObatDetailModel
    @OneToMany(mappedBy = "noFaktur", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObatDetailModel> listObatDetail;
}
