package propensi.b02.sobatarlydia.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import propensi.b02.sobatarlydia.model.ReturObatModel;


@Repository
public interface ReturDb extends JpaRepository<ReturObatModel, Long> {
    @Query("SELECT c FROM ReturObatModel c ORDER BY CASE WHEN c.status = 'Ditolak' THEN 1 WHEN c.status = 'Menunggu' THEN 2 WHEN c.status = 'Menunggu, Sudah Direvisi' THEN 3 WHEN c.status = 'Menunggu, Pernah Direvisi' THEN 4 ELSE 5 END")
    List<ReturObatModel> findAllForKaryawan();

    @Query("SELECT c FROM ReturObatModel c ORDER BY CASE WHEN c.status = 'Menunggu, Sudah Direvisi' THEN 1 WHEN c.status = 'Menunggu, Pernah Direvisi' THEN 2 WHEN c.status = 'Menunggu' THEN 3 WHEN c.status = 'Ditolak' THEN 4 ELSE 5 END")
    List<ReturObatModel> findAllForApoteker();
    Optional<ReturObatModel> findById(Long returId);
}
