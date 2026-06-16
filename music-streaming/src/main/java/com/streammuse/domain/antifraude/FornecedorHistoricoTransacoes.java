package com.streammuse.domain.antifraude;

import com.streammuse.domain.shared.Cpf;

import java.util.List;

public interface FornecedorHistoricoTransacoes {

    List<TransacaoAntiFraude> buscarTransacoesRecentes(Cpf cpf, java.time.Instant desde);
}
