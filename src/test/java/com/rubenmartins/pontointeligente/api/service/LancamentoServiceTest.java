package com.rubenmartins.pontointeligente.api.service;

import com.rubenmartins.pontointeligente.api.entities.Lancamento;
import com.rubenmartins.pontointeligente.api.repositories.LancamentoRepository;
import com.rubenmartins.pontointeligente.api.services.LancamentoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoServiceTest {

    @MockBean
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Before
    public void setUp() {
        when(lancamentoRepository.findByFuncionarioId(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(new ArrayList<>()));
        when(lancamentoRepository.findById(anyLong())).thenReturn(Optional.of(new Lancamento()));
        when(lancamentoRepository.save(any(Lancamento.class))).thenReturn(new Lancamento());
    }

    @Test
    public void testBuscaFuncionarioId() {
        Page<Lancamento> page = lancamentoService.buscarPorFuncionarioId(1L, PageRequest.of(0, 10));
        assertNotNull(page);
    }

    @Test
    public void testBuscoPorId() {
        Optional<Lancamento> optional = lancamentoService.buscaPorId(1L);
        assertTrue(optional.isPresent());
    }

    @Test
    public void testPersiste() {
        Lancamento lancamento = lancamentoService.persistir(new Lancamento());
        assertNotNull(lancamento);
    }
}
