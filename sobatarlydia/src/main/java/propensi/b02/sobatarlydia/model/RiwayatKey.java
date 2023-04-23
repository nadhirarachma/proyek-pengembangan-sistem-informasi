package propensi.b02.sobatarlydia.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RiwayatKey implements Serializable {
    // Relasi dengan ObatModel
    @ManyToOne
    @MapsId("idObat")
    private ObatModel idObat;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime waktuPerubahan;

    private String info;
}