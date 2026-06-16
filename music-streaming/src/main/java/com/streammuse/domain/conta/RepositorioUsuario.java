package com.streammuse.domain.conta;

import com.streammuse.domain.shared.Cpf;

import java.util.Optional;

public interface RepositorioUsuario {

    Usuario salvar(Usuario usuario);

    Optional<Usuario> buscarPorCpf(Cpf cpf);

    boolean existePorCpf(Cpf cpf);
}
