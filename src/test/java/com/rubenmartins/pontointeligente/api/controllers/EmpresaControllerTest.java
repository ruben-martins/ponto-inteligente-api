package com.rubenmartins.pontointeligente.api.controllers;

import com.rubenmartins.pontointeligente.api.entities.Empresa;
import com.rubenmartins.pontointeligente.api.services.EmpresaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpresaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmpresaService empresaService;

    private static final String BUSCAR_EMPRESA_CNPJ_URL = "/api/empresas/cnpj/";
    private static final Long ID = Long.valueOf(1);
    private static final String CNPJ = "11861136000102";
    private static final String RAZAO_SOCIAL = "Empresa_xyz";

    @Test
    public void testBuscaEmpresaCNPJInvalido() throws Exception {
        when(empresaService.buscarPorCnpj(Mockito.anyString()))
                .thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ)
                .accept(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors")
                        .value("Empresa não encontrada para o CNPJ " + CNPJ));
    }

    @Test
    public void testBuscarEmpresaCNPJValido() throws Exception {
        when(empresaService.buscarPorCnpj(anyString()))
                .thenReturn(Optional.of(obterDadosEmpresa()));
        mvc.perform(MockMvcRequestBuilders.get(BUSCAR_EMPRESA_CNPJ_URL + CNPJ)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(ID))
                .andExpect(jsonPath("$.data.razaoSocial").value(equalTo(RAZAO_SOCIAL)))
                .andExpect(jsonPath("$.data.cnpj").value(equalTo(CNPJ)))
                .andExpect(jsonPath("$.errors").isEmpty());
    }

    private Empresa obterDadosEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setId(ID);
        empresa.setCnpj(CNPJ);
        empresa.setRazaoSocial(RAZAO_SOCIAL);
        return empresa;
    }
}
