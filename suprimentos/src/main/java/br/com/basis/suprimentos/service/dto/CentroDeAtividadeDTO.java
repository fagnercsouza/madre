package br.com.basis.suprimentos.service.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CentroDeAtividadeDTO implements Serializable {
    private Long id;

    @NotNull
    @Size(max = 120)
    private String descricao;
}
