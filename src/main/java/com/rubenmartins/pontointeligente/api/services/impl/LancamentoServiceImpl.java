package com.rubenmartins.pontointeligente.api.services.impl;

import com.rubenmartins.pontointeligente.api.entities.Lancamento;
import com.rubenmartins.pontointeligente.api.repositories.LancamentoRepository;
import com.rubenmartins.pontointeligente.api.services.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private Logger LOG = LoggerFactory.getLogger(LancamentoServiceImpl.class);

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Override
    public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
        LOG.info("Buscando funcionario pelo ID {}", funcionarioId);
        return lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
    }

    @Override
    public Optional<Lancamento> buscaPorId(Long id) {
        LOG.info("Buscando funcionario pelo ID {}", id);
        return lancamentoRepository.findById(id);
    }

    @Override
    public Lancamento persistir(Lancamento lancamento) {
        LOG.info("Persistindo lançamento D {}", lancamento);
        return lancamentoRepository.save(lancamento);
    }

    @Override
    public void remover(Long id) {
        LOG.info("Removendo lançamento {}", id);
        lancamentoRepository.deleteById(id);
    }
}
