package propensi.b02.sobatarlydia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.b02.sobatarlydia.model.ObatDetailId;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import java.util.Optional;

@Repository
public interface ObatDetailDb extends JpaRepository<ObatDetailModel,String> {
    Optional<ObatDetailModel> findByObatDetailId(ObatDetailId idObat);
}
