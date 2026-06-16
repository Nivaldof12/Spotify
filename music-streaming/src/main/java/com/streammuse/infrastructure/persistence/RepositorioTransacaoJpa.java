package com.streammuse.infrastructure.persistence;

import com.streammuse.domain.pagamento.RepositorioTransacao;
import com.streammuse.domain.pagamento.Transacao;
import com.streammuse.domain.shared.Cpf;
import com.streammuse.domain.shared.StatusTransacao;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class RepositorioTransacaoJpa implements RepositorioTransacao {

    private final TransacaoJpaRepository jpaRepository;

    public RepositorioTransacaoJpa(TransacaoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Transacao salvar(Transacao transacao) {
        return jpaRepository.save(transacao);
    }

    @Override
    public List<Transacao> buscarTransacoesAutorizadasRecentes(Cpf cpf, Instant desde) {
        return jpaRepository.findByCpfUsuarioAndStatusAndDataHoraAfter(
                cpf, StatusTransacao.AUTORIZADA, desde
        );
    }

    @Override
    public Optional<Transacao> buscarPorId(Long id) {
        return jpaRepository.findById(id);
    }
}
