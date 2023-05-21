package propensi.b02.sobatarlydia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.b02.sobatarlydia.model.ReturObatModel;

@Repository
public interface ReturDb extends JpaRepository<ReturObatModel, String> {

}
