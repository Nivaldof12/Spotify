package com.streammuse.domain.antifraude;

import com.streammuse.domain.shared.Cpf;

import java.math.BigDecimal;
import java.util.Optional;

public record SolicitacaoAutorizacaoTransacao(
        Cpf cpf,
        BigDecimal valor,
        String comerciante
) {
}
