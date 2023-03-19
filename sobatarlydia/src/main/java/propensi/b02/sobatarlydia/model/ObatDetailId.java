package propensi.b02.sobatarlydia.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ObatDetailId implements Serializable {
    // Relasi dengan ObatModel
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_obat", referencedColumnName = "id_obat")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ObatModel idObat;

    private int kodeBatch;

    public String toString() {
        return idObat.getIdObat();
    }
}