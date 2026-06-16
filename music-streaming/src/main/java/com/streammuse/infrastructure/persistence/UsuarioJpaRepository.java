package com.streammuse.infrastructure.persistence;

import com.streammuse.domain.conta.Usuario;
import com.streammuse.domain.shared.Cpf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<Usuario, Long> {

    java.util.Optional<Usuario> findByCpf(Cpf cpf);

    boolean existsByCpf(Cpf cpf);
}
