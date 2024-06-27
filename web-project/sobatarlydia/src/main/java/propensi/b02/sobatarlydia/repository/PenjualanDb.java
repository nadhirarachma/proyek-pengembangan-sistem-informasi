package propensi.b02.sobatarlydia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.b02.sobatarlydia.model.PenjualanModel;

@Repository
public interface PenjualanDb extends JpaRepository <PenjualanModel, Long> {
    Optional<PenjualanModel> findById(long id);
}
