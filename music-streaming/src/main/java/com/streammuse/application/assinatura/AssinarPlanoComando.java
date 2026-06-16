package com.streammuse.application.assinatura;

import com.streammuse.domain.conta.TipoPlanoAssinatura;

public record AssinarPlanoComando(String cpf, TipoPlanoAssinatura plano) {
}
