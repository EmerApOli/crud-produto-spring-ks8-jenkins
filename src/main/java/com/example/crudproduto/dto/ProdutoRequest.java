package com.example.crudproduto.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequest(
        @NotBlank(message = "nome é obrigatório")
        @Size(max = 120, message = "nome deve ter no máximo 120 caracteres")
        String nome,

        @Size(max = 500, message = "descricao deve ter no máximo 500 caracteres")
        String descricao,

        @NotNull(message = "preco é obrigatório")
        @DecimalMin(value = "0.01", message = "preco deve ser maior que zero")
        BigDecimal preco,

        @NotNull(message = "quantidade é obrigatória")
        @Min(value = 0, message = "quantidade não pode ser negativa")
        Integer quantidade
) {
}
