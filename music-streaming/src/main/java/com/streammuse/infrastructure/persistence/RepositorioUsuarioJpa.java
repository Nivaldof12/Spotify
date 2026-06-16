package com.streammuse.infrastructure.persistence;

import com.streammuse.domain.conta.RepositorioUsuario;
import com.streammuse.domain.conta.Usuario;
import com.streammuse.domain.shared.Cpf;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RepositorioUsuarioJpa implements RepositorioUsuario {

    private final UsuarioJpaRepository jpaRepository;

    public RepositorioUsuarioJpa(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        return jpaRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorCpf(Cpf cpf) {
        return jpaRepository.findByCpf(cpf);
    }

    @Override
    public boolean existePorCpf(Cpf cpf) {
        return jpaRepository.existsByCpf(cpf);
    }
}
