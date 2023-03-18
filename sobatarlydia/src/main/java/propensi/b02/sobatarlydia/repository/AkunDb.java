package propensi.b02.sobatarlydia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.b02.sobatarlydia.model.PenggunaModel;

import java.util.Optional;

@Repository
public interface AkunDb extends JpaRepository<PenggunaModel, String> {
    Optional<PenggunaModel> findByEmail(String email);
}
