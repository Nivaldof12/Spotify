package com.streammuse.domain.antifraude;

import java.math.BigDecimal;
import java.time.Instant;

public record TransacaoAntiFraude(
        BigDecimal valor,
        String comerciante,
        Instant dataHora
) {
}
