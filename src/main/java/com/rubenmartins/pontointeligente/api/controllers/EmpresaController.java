package com.rubenmartins.pontointeligente.api.controllers;

import com.rubenmartins.pontointeligente.api.dtos.EmpresaDTO;
import com.rubenmartins.pontointeligente.api.entities.Empresa;
import com.rubenmartins.pontointeligente.api.response.Response;
import com.rubenmartins.pontointeligente.api.services.EmpresaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

    private final static Logger log = LoggerFactory.getLogger(EmpresaController.class);

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Response<EmpresaDTO>> buscarPorCnpj(@PathVariable("cnpj") String cnpj) {
        log.info("Buscando empresas pelo CNPJ: " + cnpj);
        Response<EmpresaDTO> response = new Response<>();
        Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
        if (!empresa.isPresent()) {
            log.info("Empresa não encontrada para o CNPJ " + cnpj);
            response.getErrors().add("Empresa não encontrada para o CNPJ " + cnpj);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(convertEmpresaToDTO(empresa.get()));
        return ResponseEntity.ok(response);
    }

    private EmpresaDTO convertEmpresaToDTO(Empresa empresa) {
        EmpresaDTO dto = new EmpresaDTO();
        dto.setId(empresa.getId());
        dto.setRazaoSocial(empresa.getRazaoSocial());
        dto.setCnpj(empresa.getCnpj());
        return dto;
    }


}
