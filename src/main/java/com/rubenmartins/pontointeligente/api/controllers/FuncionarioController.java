package com.rubenmartins.pontointeligente.api.controllers;

import com.rubenmartins.pontointeligente.api.dtos.FuncionarioDTO;
import com.rubenmartins.pontointeligente.api.entities.Funcionario;
import com.rubenmartins.pontointeligente.api.response.Response;
import com.rubenmartins.pontointeligente.api.services.FuncionarioService;
import com.rubenmartins.pontointeligente.api.utils.PasswordUtils;
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
import java.math.BigDecimal;
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

        Optional<Funcionario> funcionario = funcionarioService.buscarPorId(id);
        if (!funcionario.isPresent()) {
            result.addError(new ObjectError("funcionario", "Funcionário não encontrado."));
        } else {
            atualizarDadosFuncionario(funcionario.get(), funcionarioDTO, result);
        }

        if (result.hasErrors()) {
            log.error("Erro ao validar funcionário: {}", result.getAllErrors());
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        funcionarioService.persistir(funcionario.get());
        response.setData(convertFuncionarioEmDTO(funcionario.get()));
        return ResponseEntity.ok(response);
    }

    private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDTO dto, BindingResult result) {
        funcionario.setNome(dto.getNome());
        funcionario.setEmail(dto.getEmail());
        if (!funcionario.getEmail().equalsIgnoreCase(dto.getEmail())) {
            funcionarioService.buscarPorEmail(dto.getEmail())
                .ifPresent(f -> result.addError(new ObjectError("email", "Email já existente")));
            funcionario.setEmail(dto.getEmail());
        }
        dto.getQtdHorasAlmoco().ifPresent(qtd -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtd)));
        dto.getValorHora().ifPresent(v -> funcionario.setValorHora(new BigDecimal(v)));
        dto.getSenha().ifPresent(s -> funcionario.setSenha(PasswordUtils.gerarBCrypt(s)));
    }

    private FuncionarioDTO convertFuncionarioEmDTO(Funcionario funcionario) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setEmail(funcionario.getEmail());
        funcionario.getQtdHorasTrabalhoDiaOpt().ifPresent(
            qtd -> dto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(funcionario.getQtdHorasTrabalhoDia()))));
        funcionario.getQtdHorasAlmocoOpt().ifPresent(
            qtd -> dto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(funcionario.getQtdHorasAlmoco()))));
        funcionario.getValorHoraOpt().ifPresent(
            v -> dto.setValorHora(Optional.of(funcionario.getValorHora().toString())));
        return dto;
    }

}
