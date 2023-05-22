package propensi.b02.sobatarlydia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import propensi.b02.sobatarlydia.model.PenawaranModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface PenawaranDb extends JpaRepository<PenawaranModel, Long> {
        @Query("SELECT c FROM PenawaranModel c ORDER BY c.tanggal desc")
        List<PenawaranModel> findAll();
        Optional<PenawaranModel> findPenawaranModelById(Long id);
}
