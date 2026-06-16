package com.streammuse.api.pagamento;

import com.streammuse.infrastructure.web.dto.AutorizarTransacaoRequest;
import com.streammuse.infrastructure.web.dto.TransacaoResponse;
import org.springframework.http.ResponseEntity;

public interface AutorizacaoTransacaoApi {

    ResponseEntity<TransacaoResponse> autorizarTransacao(AutorizarTransacaoRequest request);
}
