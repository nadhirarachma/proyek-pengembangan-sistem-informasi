package propensi.b02.sobatarlydia.service;
import org.springframework.stereotype.Service;
import propensi.b02.sobatarlydia.model.ObatDetailModel;
import propensi.b02.sobatarlydia.rest.ObatWaiting;

import java.util.List;

@Service
public interface ObatDetailService {
    List<ObatDetailModel> getAllObatDetail();
    List<ObatWaiting> getAllObatWaiting();

}
