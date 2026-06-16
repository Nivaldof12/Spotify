package com.streammuse.domain.pagamento;

import com.streammuse.domain.shared.Cpf;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface RepositorioTransacao {

    Transacao salvar(Transacao transacao);

    List<Transacao> buscarTransacoesAutorizadasRecentes(Cpf cpf, Instant desde);

    Optional<Transacao> buscarPorId(Long id);
}
