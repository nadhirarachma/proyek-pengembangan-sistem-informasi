package propensi.b02.sobatarlydia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kuantitas")
public class KuantitasModel implements Serializable {
    @EmbeddedId
    KuantitasKey id;

    @NotNull
    @Column(name = "kuantitas", nullable = false)
    private int kuantitas;
}

