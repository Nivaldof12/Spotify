package com.streammuse.application.conta;

import com.streammuse.domain.conta.CartaoCredito;
import com.streammuse.domain.conta.RepositorioUsuario;
import com.streammuse.domain.conta.Usuario;
import com.streammuse.domain.shared.Cpf;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicoCriacaoConta {

    private final RepositorioUsuario repositorioUsuario;

    public ServicoCriacaoConta(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Transactional
    public Usuario criarConta(CriarContaComando comando) {
        Cpf cpf = new Cpf(comando.cpf());
        if (repositorioUsuario.existePorCpf(cpf)) {
            throw new IllegalStateException("conta-existente");
        }
        CartaoCredito cartao = CartaoCredito.criar(comando.numeroCartao(), comando.cartaoValido(), comando.cartaoAtivo());
        Usuario usuario = Usuario.criarConta(cpf, comando.nome(), comando.email(), cartao);
        return repositorioUsuario.salvar(usuario);
    }
}
