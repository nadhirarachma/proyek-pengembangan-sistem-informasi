package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.model.PenawaranModel;
import propensi.b02.sobatarlydia.repository.PenawaranDb;

import java.util.List;
import java.util.Optional;

@Service
public class PenawaranServiceImpl implements PenawaranService{
    @Autowired
    private PenawaranDb penawaranDb;
    @Override
    public List<PenawaranModel> getAllPenawaran() {
        return penawaranDb.findAll();
    }

    @Override
    public PenawaranModel getPenawaranById(Long id){
        Optional<PenawaranModel> penawaran = penawaranDb.findPenawaranModelById(id);
        if(penawaran.isPresent()){
            return penawaran.get();
        }
        else return null;
    }

    @Override
    public PenawaranModel updateStatusPenawaran(PenawaranModel penawaran, String status) {
        penawaran.setStatusPenawaran(status);
        return penawaranDb.save(penawaran);
    }
}
