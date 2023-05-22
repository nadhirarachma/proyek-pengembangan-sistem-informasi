package propensi.b02.sobatarlydia.service;

import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.model.PenawaranModel;

import java.util.List;

@Service
public interface PenawaranService {
    public List<PenawaranModel> getAllPenawaran();
    public PenawaranModel getPenawaranById(Long id);
    public PenawaranModel updateStatusPenawaran(PenawaranModel penawaranModel, String status);

}
