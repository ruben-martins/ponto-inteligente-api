package com.rubenmartins.pontointeligente.api.service;

import com.rubenmartins.pontointeligente.api.entities.Empresa;
import com.rubenmartins.pontointeligente.api.repositories.EmpresaRepository;
import com.rubenmartins.pontointeligente.api.services.EmpresaService;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaServiceTest {

    @MockBean
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaService empresaService;

    private static final String CNPJ = "12345678910";

    @Before
    public void setUp() {
        when(empresaRepository.findByCnpj(anyString())).thenReturn(new Empresa());
        when(empresaRepository.save(any(Empresa.class))).thenReturn(new Empresa());
    }

    @Test
    public void testBuscarEmpresaPorCnpj() {
        Optional<Empresa> optional = empresaService.buscarPorCnpj(CNPJ);
        assertTrue(optional.isPresent());
    }

    @Test
    public void testPersistirEmpresa() {
        Empresa empresa = empresaService.persistir(new Empresa());
        assertNotNull(empresa);
    }

}
