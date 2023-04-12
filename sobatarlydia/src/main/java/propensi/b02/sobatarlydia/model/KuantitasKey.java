package propensi.b02.sobatarlydia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KuantitasKey implements Serializable {
    @ManyToOne
    @MapsId("obatDetailId")
    ObatDetailModel obat;
    
    @ManyToOne
    @MapsId("id")
    PenjualanModel penjualan;
}