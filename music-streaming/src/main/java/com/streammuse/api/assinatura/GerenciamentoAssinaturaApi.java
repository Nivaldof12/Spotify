package com.streammuse.api.assinatura;

import com.streammuse.infrastructure.web.dto.AssinarPlanoRequest;
import com.streammuse.infrastructure.web.dto.UsuarioResponse;
import org.springframework.http.ResponseEntity;

public interface GerenciamentoAssinaturaApi {

    ResponseEntity<UsuarioResponse> assinarPlano(AssinarPlanoRequest request);
}
