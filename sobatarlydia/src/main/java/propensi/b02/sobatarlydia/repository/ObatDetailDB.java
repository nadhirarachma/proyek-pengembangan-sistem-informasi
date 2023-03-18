package propensi.b02.sobatarlydia.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import propensi.b02.sobatarlydia.model.ObatDetailModel;

import java.util.Optional;
public interface ObatDetailDB extends JpaRepository <ObatDetailModel, String>{
}
