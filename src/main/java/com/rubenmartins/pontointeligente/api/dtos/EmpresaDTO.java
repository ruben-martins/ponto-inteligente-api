package com.rubenmartins.pontointeligente.api.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaDTO {

    private Long id;
    private String razaoSocial;
    private String cnpj;

    @Override
    public String toString() {
        return "EmpresaDTO{" +
                "id=" + id +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
