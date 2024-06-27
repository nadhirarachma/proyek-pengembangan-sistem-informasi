package propensi.b02.sobatarlydia.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import propensi.b02.sobatarlydia.model.FakturModel;

@Repository
public interface FakturDb extends JpaRepository<FakturModel, String> {
    
    @Query("SELECT c FROM FakturModel c WHERE c.farmasi = :farmasi")
    List<FakturModel> findByFarmasi(@Param("farmasi") String farmasi);
    Optional<FakturModel> findFakturModelByNoFaktur(String noFaktur);
    @Query("SELECT c FROM FakturModel c ORDER BY c.tanggal desc")
    List<FakturModel> findAll();
}
