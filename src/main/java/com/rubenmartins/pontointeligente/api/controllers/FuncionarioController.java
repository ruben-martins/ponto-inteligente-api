package com.rubenmartins.pontointeligente.api.controllers;

import com.rubenmartins.pontointeligente.api.dtos.FuncionarioDTO;
import com.rubenmartins.pontointeligente.api.entities.Funcionario;
import com.rubenmartins.pontointeligente.api.response.Response;
import com.rubenmartins.pontointeligente.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @PutMapping(value = "/{id}")
    public ResponseEntity<Response<FuncionarioDTO>> atualizar(@PathVariable("id") Long id,
                                                              @Valid @RequestBody FuncionarioDTO funcionarioDTO,
                                                              BindingResult result) {
        log.info("Atualizando funcionário: {}", funcionarioDTO.toString());
        Response<FuncionarioDTO> response = new Response<>();
        Optional<Funcionario> optional = funcionarioService.buscarPorId(id);
        if (!optional.isPresent()) {
            result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
        }
        // atualiza tudo

        if (result.hasErrors()) {
            log.error("Erro ao validar funcionário: {}", result.getAllErrors());
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        funcionarioService.persistir(optional.get());

        return ResponseEntity.ok(response);
    }

}
