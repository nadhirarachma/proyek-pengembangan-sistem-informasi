package propensi.b02.sobatarlydia.restcontroller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import propensi.b02.sobatarlydia.dto.ObatDto;
import propensi.b02.sobatarlydia.model.ObatModel;
import propensi.b02.sobatarlydia.service.ObatService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/obat")
public class ObatRestController {

    @Autowired
    private ObatService obatService;

    @GetMapping(value="/by-farmasi")
    public List<ObatDto> findObatByFarmasi(@RequestParam(value = "farmasi", required = true) String farmasi) {
        List<ObatModel> lst = obatService.getObatByFarmasi(farmasi);
        return lst.stream().map(obat -> {
            ObatDto dto = new ObatDto();
            dto.setNamaObat(obat.getNamaObat());
            return dto;
        })
        .collect(Collectors.toList());
    }

}
