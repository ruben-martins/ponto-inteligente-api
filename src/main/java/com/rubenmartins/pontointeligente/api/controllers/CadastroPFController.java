package com.rubenmartins.pontointeligente.api.controllers;

import com.rubenmartins.pontointeligente.api.dtos.CadastroPFDTO;
import com.rubenmartins.pontointeligente.api.entities.Empresa;
import com.rubenmartins.pontointeligente.api.entities.Funcionario;
import com.rubenmartins.pontointeligente.api.enums.PerfilEnum;
import com.rubenmartins.pontointeligente.api.response.Response;
import com.rubenmartins.pontointeligente.api.services.EmpresaService;
import com.rubenmartins.pontointeligente.api.services.FuncionarioService;
import com.rubenmartins.pontointeligente.api.utils.PasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/cadastrar-pf")
@CrossOrigin(origins = "*")
public class CadastroPFController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPFController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Response<CadastroPFDTO>> cadastrar(@Valid @RequestBody CadastroPFDTO dto,
                                                             BindingResult result) {
        log.info("Cadastrando pessoa física {}", dto.toString());
        Response<CadastroPFDTO> response = new Response<>();
        validarDadosExistentes(dto, result);
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        Funcionario funcionario = convertDTOEmFuncionario(dto);
        empresaService.buscarPorCnpj(dto.getCnpj()).ifPresent(funcionario::setEmpresa);
        funcionarioService.persistir(funcionario);
        response.setData(convertCadastrosEmDTO(funcionario));
        return ResponseEntity.ok(response);
    }

    public void validarDadosExistentes(CadastroPFDTO dto, BindingResult result) {
        Optional<Empresa> optionalEmpresa = empresaService.buscarPorCnpj(dto.getCnpj());
        if (!optionalEmpresa.isPresent()) {
            result.addError(new ObjectError("empresa", "Empresa não cadastrada."));
        }
        funcionarioService.buscarPorCpf(dto.getCpf()).ifPresent(f ->
                result.addError(new ObjectError("funcionario", "CPF já existente.")));
        funcionarioService.buscarPorEmail(dto.getEmail()).ifPresent(f ->
                result.addError(new ObjectError("funcionario", "Email já existente.")));
    }

    public Funcionario convertDTOEmFuncionario(CadastroPFDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setCpf(dto.getCpf());
        funcionario.setEmail(dto.getEmail());
        funcionario.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha()));
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        dto.getQdtHorasTrabalhoDia()
                .ifPresent(qtd -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtd)));
        dto.getQtdHorasAlmoco()
                .ifPresent(qtd -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtd)));
        dto.getValorHora()
                .ifPresent(qtd -> funcionario.setValorHora(new BigDecimal(qtd)));
        return funcionario;
    }

    public CadastroPFDTO convertCadastrosEmDTO(Funcionario funcionario) {
        CadastroPFDTO dto = new CadastroPFDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setEmail(funcionario.getEmail());
        dto.setCpf(funcionario.getCpf());
        dto.setCnpj(funcionario.getEmpresa().getCnpj());
        funcionario.getQtdHorasTrabalhoDiaOpt()
                .ifPresent(qtd -> dto.setQdtHorasTrabalhoDia(Optional.of(qtd.toString())));
        funcionario.getQtdHorasAlmocoOpt()
                .ifPresent(qtd -> dto.setQtdHorasAlmoco(Optional.of(qtd.toString())));
        funcionario.getValorHoraOpt()
                .ifPresent(qtd -> dto.setValorHora(Optional.of(qtd.toString())));
        return dto;
    }

}
