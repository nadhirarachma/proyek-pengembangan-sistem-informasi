package propensi.b02.sobatarlydia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import propensi.b02.sobatarlydia.model.PenggunaModel;

import java.util.Optional;

public interface UserDB extends JpaRepository <PenggunaModel, String> {

}
