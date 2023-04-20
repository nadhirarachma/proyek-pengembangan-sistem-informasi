package propensi.b02.sobatarlydia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensi.b02.sobatarlydia.model.KuantitasModel;

@Repository
public interface KuantitasDb extends JpaRepository<KuantitasModel, String> {

}
