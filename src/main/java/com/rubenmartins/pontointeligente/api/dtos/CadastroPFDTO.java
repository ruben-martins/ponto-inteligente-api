package com.rubenmartins.pontointeligente.api.dtos;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Optional;

public class CadastroPFDTO {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private Optional<String> valorHora = Optional.empty();
    private Optional<String> qdtHorasTrabalhoDia = Optional.empty();
    private Optional<String> qtdHorasAlmoco = Optional.empty();
    private String cnpj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "Nome não pode ser vazio")
    @Length(min = 3, max = 200, message = "Nome deve conter entre 3 e 200 caracteres.")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NotEmpty(message = "Email não pode ser vazio.")
    @Length(min = 5, max = 200, message = "Email deve conter entre 5 e 200 caracteres.")
    @Email(message = "Email inválido")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotEmpty(message = "Senha não deve ser vazia")
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @NotEmpty(message = "CPF não pode ser vazio.")
    @CPF(message = "CPF inválido.")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Optional<String> getValorHora() {
        return valorHora;
    }

    public void setValorHora(Optional<String> valorHora) {
        this.valorHora = valorHora;
    }

    public Optional<String> getQdtHorasTrabalhoDia() {
        return qdtHorasTrabalhoDia;
    }

    public void setQdtHorasTrabalhoDia(Optional<String> qdtHorasTrabalhoDia) {
        this.qdtHorasTrabalhoDia = qdtHorasTrabalhoDia;
    }

    public Optional<String> getQtdHorasAlmoco() {
        return qtdHorasAlmoco;
    }

    public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
        this.qtdHorasAlmoco = qtdHorasAlmoco;
    }

    @NotEmpty(message = "CNPJ não pode ser vazio")
    @CNPJ(message = "CNPJ inválido")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @Override
    public String toString() {
        return "CadastroPFDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", cpf='" + cpf + '\'' +
                ", valorHora=" + valorHora +
                ", qdtHorasTrabalhoDia=" + qdtHorasTrabalhoDia +
                ", qtdHorasAlmoco=" + qtdHorasAlmoco +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
