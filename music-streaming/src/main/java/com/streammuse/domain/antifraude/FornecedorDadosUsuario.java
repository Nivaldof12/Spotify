package com.streammuse.domain.antifraude;

import com.streammuse.domain.shared.Cpf;

import java.util.Optional;

public interface FornecedorDadosUsuario {

    Optional<DadosUsuarioAntiFraude> buscarDadosPorCpf(Cpf cpf);
}
