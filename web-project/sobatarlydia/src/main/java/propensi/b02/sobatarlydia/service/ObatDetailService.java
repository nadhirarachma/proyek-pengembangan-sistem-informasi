package propensi.b02.sobatarlydia.service;
import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.dto.ObatUpdtDTO;
import propensi.b02.sobatarlydia.dto.ObatWaiting;
import propensi.b02.sobatarlydia.model.ObatDetailId;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.model.ObatModel;

import java.util.List;

@Service
public interface ObatDetailService {
    List<ObatDetailModel> getAllObatDetail();
    List<ObatWaiting> getAllObatWaiting();
    ObatDetailModel getObatDetailByObatDetailId(ObatDetailId kode);
    ObatDetailModel updateObatDetail(ObatDetailModel obatDetail);
    ObatDetailModel makeSetterDetail(ObatDetailModel obatDetailPast, ObatUpdtDTO obat);
    ObatDetailModel updateToArsip (ObatDetailModel obatDetail);
    void addObatDetail(ObatDetailModel obat);
    ObatDetailModel getObatDetailByIdObat(ObatModel idObat);
    ObatDetailModel getObatDetailByKodeBatch(ObatModel idObat, int kodeBatch);
}
