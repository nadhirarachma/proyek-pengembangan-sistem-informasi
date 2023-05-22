package propensi.b02.sobatarlydia.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import propensi.b02.sobatarlydia.dto.*;
import propensi.b02.sobatarlydia.model.ReturObatModel;
import propensi.b02.sobatarlydia.service.ReturService;
import propensi.b02.sobatarlydia.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.security.Principal;
import java.text.NumberFormat;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/retur")
public class ReturRestController {

    @Autowired
    private ReturService returService;

    @Autowired
    private UserService userService;
    
    @GetMapping(value="/list-all")
    public List<ReturDto> findAllRetur(Principal principal) {
        String role = userService.getAkunByEmail(principal.getName()).getRole();

        List<ReturObatModel> lst = returService.getListRetur();
        return lst.stream().map(retur -> {
            ReturDto dto = new ReturDto();
            dto.setKode(retur.getReturId());

            String tanggal = retur.getWaktu().toString().split("T")[0];
            dto.setTanggal(tanggal);
            dto.setObatLama(retur.getObatLama().getObatDetailId().getIdObat().getNamaObat());
            dto.setKuantitasLama(Integer.toString(retur.getJumlahObatLamaDitukar()));
            dto.setObatBaru(retur.getObatBaru().getObatDetailId().getIdObat().getNamaObat());
            dto.setKuantitasBaru(Integer.toString(retur.getJumlahObatBaruDitukar()));

            NumberFormat nf = NumberFormat.getInstance(new Locale("id", "ID"));
            String hargaLama = nf.format(retur.getObatLama().getObatDetailId().getIdObat().getHarga() * retur.getJumlahObatLamaDitukar());
            String hargaBaru = nf.format(retur.getObatBaru().getObatDetailId().getIdObat().getHarga() * retur.getJumlahObatBaruDitukar());
            dto.setHargaLama("Rp"+hargaLama);
            dto.setHargaBaru("Rp"+hargaBaru);

            if (!retur.getStatus().equals("Menunggu")) {
                dto.setStatus(retur.getStatus());
            } else {
                if (role.equals("Karyawan")) {
                    dto.setStatus("Menunggu");
                }
            }
            dto.setFeedback(retur.getFeedback());
            return dto;
        })
        .collect(Collectors.toList());
    }
}
