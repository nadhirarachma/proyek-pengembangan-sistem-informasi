package propensi.b02.sobatarlydia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import propensi.b02.sobatarlydia.model.ObatModel;

@Repository
public interface ObatDb extends JpaRepository<ObatModel, String> {
    
    @Query("SELECT c FROM ObatModel c WHERE c.farmasi = :farmasi")
    List<ObatModel> findByFarmasi(@Param("farmasi") String farmasi);

    // @Query("SELECT c FROM ObatModel c WHERE c.farmasi = :farmasi AND c.namaObat = :nama")
    // Optional<ObatModel> findByNamaDanFarmasi(@Param("farmasi") String farmasi, @Param("nama") String nama);
    // Optional<ObatModel> findByNamaObat(@Param("farmasi") String farmasi, @Param("nama") String nama);

    @Query("SELECT o FROM ObatModel o WHERE o.kategori.no= :no")
    List<ObatModel> findKategoriFiltered(@Param("no") Integer no);

}
