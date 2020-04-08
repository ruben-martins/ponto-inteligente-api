package com.rubenmartins.pontointeligente.api.services.impl;

import com.rubenmartins.pontointeligente.api.entities.Funcionario;
import com.rubenmartins.pontointeligente.api.repositories.FuncionarioRepository;
import com.rubenmartins.pontointeligente.api.services.FuncionarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private static final Logger LOG = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public Optional<Funcionario> buscarPorCpf(String cpf) {
        LOG.info("Buscando funcionario pelo CPF {}", cpf);
        Funcionario funcionario = funcionarioRepository.findByCpf(cpf);
        return Optional.ofNullable(funcionario);
    }

    @Override
    public Optional<Funcionario> buscarPorEmail(String email) {
        LOG.info("Buscando funcionario pelo e-mail {}", email);
        Funcionario funcionario = funcionarioRepository.findByEmail(email);
        return Optional.ofNullable(funcionario);
    }

    @Override
    public Optional<Funcionario> buscarPorId(Long id) {
        LOG.info("Buscando funcionario pelo ID {}", id);
        return funcionarioRepository.findById(id);
    }

    @Override
    public Funcionario persistir(Funcionario funcionario) {
        LOG.info("Persistindo funcionario {}", funcionario);
        return funcionarioRepository.save(funcionario);
    }
}
