package com.streammuse.infrastructure.persistence;

import com.streammuse.domain.pagamento.Transacao;
import com.streammuse.domain.shared.Cpf;
import com.streammuse.domain.shared.StatusTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TransacaoJpaRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findByCpfUsuarioAndStatusAndDataHoraAfter(
            Cpf cpfUsuario,
            StatusTransacao status,
            Instant dataHora
    );
}
