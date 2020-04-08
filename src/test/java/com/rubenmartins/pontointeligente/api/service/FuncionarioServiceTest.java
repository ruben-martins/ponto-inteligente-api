package com.rubenmartins.pontointeligente.api.service;

import com.rubenmartins.pontointeligente.api.entities.Funcionario;
import com.rubenmartins.pontointeligente.api.repositories.FuncionarioRepository;
import com.rubenmartins.pontointeligente.api.services.FuncionarioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioServiceTest {

    @MockBean
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioService funcionarioService;

    @Before
    public void setUp() {
        when(funcionarioRepository.findByCpf(anyString())).thenReturn(new Funcionario());
        when(funcionarioRepository.findByEmail(anyString())).thenReturn(new Funcionario());
        when(funcionarioRepository.findById(anyLong())).thenReturn(Optional.of(new Funcionario()));
        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(new Funcionario());
    }

    @Test
    public void testFindByCpf() {
        Optional<Funcionario> optional = funcionarioService.buscarPorCpf("12345");
        assertTrue(optional.isPresent());
    }

    @Test
    public void testFindByEmail() {
        Optional<Funcionario> optional = funcionarioService.buscarPorEmail("exemplo@email.com");
        assertTrue(optional.isPresent());
    }

    @Test
    public void testFindById() {
        Optional<Funcionario> optional = funcionarioService.buscarPorId(1L);
        assertTrue(optional.isPresent());
    }

    @Test
    public void testPersist() {
        Funcionario funcionario = funcionarioService.persistir(new Funcionario());
        assertNotNull(funcionario);
    }


}
