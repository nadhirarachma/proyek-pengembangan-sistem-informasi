package propensi.b02.sobatarlydia.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.NoArgsConstructor;

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
    @ManyToOne
    @MapsId("idObat")
    private ObatModel idObat;

    private int kodeBatch;

    public String toString() {
        return idObat.getIdObat();
    }
}