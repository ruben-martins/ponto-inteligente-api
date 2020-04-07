package com.rubenmartins.pontointeligente.api.repositories;

import com.rubenmartins.pontointeligente.api.entities.Empresa;
import com.rubenmartins.pontointeligente.api.entities.Funcionario;
import com.rubenmartins.pontointeligente.api.enums.PerfilEnum;
import com.rubenmartins.pontointeligente.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private static final String CPF = "12345678901";
    private static final String EMAIL = "rubnelson@email.com";

    @Before
    public void preUpdate() {
        Empresa empresa = empresaRepository.save(createEmpresa());
        funcionarioRepository.save(createFuncionario(empresa));
    }

    @After
    public void tearDown() {
        funcionarioRepository.deleteAll();
        empresaRepository.deleteAll();
    }

    private Funcionario createFuncionario(Empresa empresa) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Fulano de tal");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt("senha"));
        funcionario.setEmpresa(empresa);
        funcionario.setCpf(CPF);
        funcionario.setEmail(EMAIL);
        return funcionario;
    }

    private Empresa createEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa de exemplo");
        empresa.setCnpj("1234567");
        return empresa;
    }

    @Test
    public void testBuscarFuncionarioPorEmail() {
        Funcionario funcionario = funcionarioRepository.findByEmail(EMAIL);
        assertFuncionario(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorCpf() {
        Funcionario funcionario = funcionarioRepository.findByCpf(CPF);
        assertFuncionario(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorCpfOrEmail() {
        Funcionario funcionario = funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
        assertFuncionario(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorCpfOrEmailInvalido() {
        Funcionario funcionario = funcionarioRepository.findByCpfOrEmail(CPF, "invalido@email.com");
        assertNotNull(funcionario);
        assertFuncionario(funcionario);
    }

    @Test
    public void testBuscarFuncionarioPorCpfInvalidoOrEmail() {
        Funcionario funcionario = funcionarioRepository.findByCpfOrEmail("invalido", EMAIL);
        assertNotNull(funcionario);
        assertFuncionario(funcionario);
    }

    private void assertFuncionario(Funcionario funcionario) {
        assertEquals(EMAIL, funcionario.getEmail());
        assertEquals(CPF, funcionario.getCpf());
    }

}
