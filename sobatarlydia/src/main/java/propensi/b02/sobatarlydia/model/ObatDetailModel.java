package propensi.b02.sobatarlydia.model;

import java.io.Serializable;

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
    @Column(name = "stok", nullable = false)
    private int stok;

    // Relasi dengan FakturModel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "no_faktur", referencedColumnName = "no_faktur")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private FakturModel noFaktur;
}
