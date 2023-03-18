package propensi.b02.sobatarlydia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import propensi.b02.sobatarlydia.model.KategoriObatModel;

import java.util.Optional;

@Repository
public interface KategoriObatDb extends JpaRepository<KategoriObatModel, Integer> {
    Optional<KategoriObatModel> findByNo(Integer no);
}
