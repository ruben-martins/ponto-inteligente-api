package com.rubenmartins.pontointeligente.api.controllers;

import com.rubenmartins.pontointeligente.api.dtos.CadastroPJDTO;
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
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/cadastrar-pj")
@CrossOrigin(origins = "*")
public class CadastroPJController {

    private static final Logger log = LoggerFactory.getLogger(CadastroPJController.class);

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Response<CadastroPJDTO>> cadastrar(@Valid @RequestBody CadastroPJDTO cadastroPJDTO,
                                                             BindingResult result) throws NoSuchAlgorithmException {
        log.info("Cadastrando PJ {}", cadastroPJDTO.toString());
        validarDadosExistentes(cadastroPJDTO, result);
        Response<CadastroPJDTO> response = new Response<>();

        if (result.hasErrors()) {
            log.error("Erro ao validar dados de cadastro PJ: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Empresa empresa = converteDtoParaEmpresa(cadastroPJDTO);
        empresaService.persistir(empresa);

        Funcionario funcionario = converteDtoParaFuncionario(cadastroPJDTO);
        funcionario.setEmpresa(empresa);
        funcionarioService.persistir(funcionario);

        response.setData(converteCadastrosDTO(funcionario));
        return ResponseEntity.ok(response);
    }

    private void validarDadosExistentes(CadastroPJDTO dto, BindingResult result) {
        empresaService.buscarPorCnpj(dto.getCnpj())
                .ifPresent(e -> result.addError(new ObjectError("Empresa", "Empresa já existente")));
        funcionarioService.buscarPorCpf(dto.getCpf())
                .ifPresent(f -> result.addError(new ObjectError("Funcionario", "CPF já existente")));
        funcionarioService.buscarPorEmail(dto.getEmail())
                .ifPresent(f -> result.addError(new ObjectError("Funcionario", "Email já existente")));
    }

    private Empresa converteDtoParaEmpresa(CadastroPJDTO cadastroPJDTO) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(cadastroPJDTO.getCnpj());
        empresa.setRazaoSocial(cadastroPJDTO.getRazaoSocial());
        return empresa;
    }

    private Funcionario converteDtoParaFuncionario(CadastroPJDTO dto){
        Funcionario funcionario = new Funcionario();
        funcionario.setNome(dto.getNome());
        funcionario.setEmail(dto.getEmail());
        funcionario.setCpf(dto.getCpf());
        funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
        funcionario.setSenha(PasswordUtils.gerarBCrypt(dto.getSenha()));
        return funcionario;
    }

    private CadastroPJDTO converteCadastrosDTO(Funcionario funcionario) {
        CadastroPJDTO dto = new CadastroPJDTO();
        dto.setId(funcionario.getId());
        dto.setNome(funcionario.getNome());
        dto.setCpf(funcionario.getCpf());
        dto.setEmail(funcionario.getEmail());
        dto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
        dto.setCnpj(funcionario.getEmpresa().getCnpj());
        return dto;
    }
}
