package propensi.b02.sobatarlydia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.b02.sobatarlydia.model.PenggunaModel;

public interface UserDB extends JpaRepository <PenggunaModel, String> {
    Optional<PenggunaModel> findByEmail(String email);
}
