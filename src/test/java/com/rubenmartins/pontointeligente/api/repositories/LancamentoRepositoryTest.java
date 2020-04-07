package com.rubenmartins.pontointeligente.api.repositories;

import com.rubenmartins.pontointeligente.api.entities.Empresa;
import com.rubenmartins.pontointeligente.api.entities.Funcionario;
import com.rubenmartins.pontointeligente.api.entities.Lancamento;
import com.rubenmartins.pontointeligente.api.enums.PerfilEnum;
import com.rubenmartins.pontointeligente.api.enums.TipoEnum;
import com.rubenmartins.pontointeligente.api.utils.PasswordUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LancamentoRepositoryTest {

    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private Long funcionarioId;

    @Before
    public void setUp() {
        Empresa empresa = empresaRepository.save(createEmpresa());

        Funcionario funcionario = funcionarioRepository.save(createFuncionario(empresa));
        this.funcionarioId = funcionario.getId();

        lancamentoRepository.save(createLancamento(funcionario));
        lancamentoRepository.save(createLancamento(funcionario));
    }

    @After
    public void tearDown() {
        lancamentoRepository.deleteAll();
        funcionarioRepository.deleteAll();
        empresaRepository.deleteAll();
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioId() {
        List<Lancamento> lancamentos = lancamentoRepository.findByFuncionarioId(funcionarioId);
        Assert.assertEquals(2, lancamentos.size());
    }

    @Test
    public void testBuscarLancamentosPorFuncionarioIdPaginado() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Lancamento> page = lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
        Assert.assertEquals(2, page.getTotalElements());
    }

    private Lancamento createLancamento(Funcionario funcionario) {
        Lancamento lancamento = new Lancamento();
        lancamento.setData(new Date());
        lancamento.setFuncionario(funcionario);
        lancamento.setTipo(TipoEnum.INICIO_ALMOCO);
        return lancamento;
    }

    private Funcionario createFuncionario(Empresa empresa) {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Fulano de tal");
        funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
        funcionario.setSenha(PasswordUtils.gerarBCrypt("senha"));
        funcionario.setEmpresa(empresa);
        funcionario.setCpf("12345678901");
        funcionario.setEmail("rubnelson@email.com");
        return funcionario;
    }

    private Empresa createEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Empresa de exemplo");
        empresa.setCnpj("1234567");
        return empresa;
    }

}
