package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import propensi.b02.sobatarlydia.model.PenawaranModel;
import propensi.b02.sobatarlydia.model.PenjualanModel;
import propensi.b02.sobatarlydia.repository.PenawaranDb;
import propensi.b02.sobatarlydia.repository.PenjualanDb;

import java.util.Optional;

@Service
@Transactional
public class PenawaranServiceImpl implements PenawaranService {
    @Autowired
    private PenawaranDb penawaranDb;

    @Override
    public PenawaranModel getPenawaranById(Long idPenawaran) {
        Optional<PenawaranModel> penawaran = penawaranDb.findById(idPenawaran);
        if (penawaran.isPresent()) {
            return penawaran.get();
        } else return null;
    }

    @Override
    public PenawaranModel addPenawaran(PenawaranModel penawaran) {
        return penawaranDb.save(penawaran);
    }
}
