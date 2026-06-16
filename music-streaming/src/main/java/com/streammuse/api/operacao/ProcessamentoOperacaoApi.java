package com.streammuse.api.operacao;

import com.streammuse.infrastructure.web.dto.OperacaoLinhaRequest;
import com.streammuse.infrastructure.web.dto.ResultadoOperacaoResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProcessamentoOperacaoApi {

    ResponseEntity<ResultadoOperacaoResponse> processarLinha(OperacaoLinhaRequest request);

    ResponseEntity<List<ResultadoOperacaoResponse>> processarLote(List<String> linhas);
}
