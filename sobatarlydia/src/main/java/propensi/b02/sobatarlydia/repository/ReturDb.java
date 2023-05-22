package propensi.b02.sobatarlydia.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.b02.sobatarlydia.model.ReturObatModel;


@Repository
public interface ReturDb extends JpaRepository<ReturObatModel, Long> {
    Optional<ReturObatModel> findById(Long returId);
}
