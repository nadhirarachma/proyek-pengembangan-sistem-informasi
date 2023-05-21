package propensi.b02.sobatarlydia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensi.b02.sobatarlydia.model.ReturObatModel;
import propensi.b02.sobatarlydia.repository.ReturDb;

@Service
public class ReturServiceImpl {

    @Autowired
    ReturDb returDb;

    public void add(ReturObatModel retur) {
        returDb.save(retur);
    }
}
