package com.streammuse.api.conta;

import com.streammuse.infrastructure.web.dto.CriarContaRequest;
import com.streammuse.infrastructure.web.dto.UsuarioResponse;
import org.springframework.http.ResponseEntity;

public interface GerenciamentoContaApi {

    ResponseEntity<UsuarioResponse> criarConta(CriarContaRequest request);
}
