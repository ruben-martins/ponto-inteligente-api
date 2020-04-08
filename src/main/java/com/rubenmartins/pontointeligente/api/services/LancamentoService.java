package com.rubenmartins.pontointeligente.api.services;

import com.rubenmartins.pontointeligente.api.entities.Lancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface LancamentoService {

    /**
     * Busca lançamentos por funcionario
     *
     * @param funcionarioId
     * @param pageRequest
     * @return Page<Lancamento>
     * */
    Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);

    /**
     * Busca lançamento por ID
     *
     * @param id
     * @return Optional<Lancamento>
     * */
    Optional<Lancamento> buscaPorId(Long id);

    /**
     * Persiste lançamentos na base de dados
     *
     * @param lancamento
     * @return Lancamento
     * */
    Lancamento persistir(Lancamento lancamento);

    /**
     * Remove lançamento pelo ID
     *
     * @param id
     * */
    void remover(Long id);



}
