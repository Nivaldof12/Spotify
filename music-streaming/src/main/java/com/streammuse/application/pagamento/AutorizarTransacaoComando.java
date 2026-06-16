package com.streammuse.application.pagamento;

import java.math.BigDecimal;

public record AutorizarTransacaoComando(
        String cpf,
        BigDecimal valor,
        String comerciante
) {
}
