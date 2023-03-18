package propensi.b02.sobatarlydia.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import propensi.b02.sobatarlydia.model.ObatModel;

import java.util.List;

@Repository
public interface ObatDb extends JpaRepository<ObatModel,String> {
    @Query("SELECT o FROM ObatModel o WHERE o.kategori.no= :no")
    List<ObatModel> findKategoriFiltered(@Param("no") Integer no);


}
