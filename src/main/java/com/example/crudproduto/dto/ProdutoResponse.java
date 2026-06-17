package com.example.crudproduto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer quantidade,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
