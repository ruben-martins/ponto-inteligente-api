package com.rubenmartins.pontointeligente.api.services;

import com.rubenmartins.pontointeligente.api.entities.Funcionario;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface FuncionarioService {

    /**
     * Busca funcionário pelo CPF.
     *
     * @param cpf
     * @return Optional<Funcionario>
     * */
    Optional<Funcionario> buscarPorCpf(String cpf);

    /**
     * Busca funcionário pelo CPF.
     *
     * @param email
     * @return Optional<Funcionario>
     * */
    Optional<Funcionario> buscarPorEmail(String email);

    /**
     * Busca funcionário pelo CPF.
     *
     * @param id
     * @return Optional<Funcionario>
     * */
    Optional<Funcionario> buscarPorId(Long id);

    /**
     * Grava funcionário base de dados.
     *
     * @param funcionario
     * @return Funcionario
     * */
    Funcionario persistir(Funcionario funcionario);


}
