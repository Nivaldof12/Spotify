package com.streammuse.infrastructure.web;

import com.streammuse.api.operacao.ProcessamentoOperacaoApi;
import com.streammuse.application.operacao.ProcessadorOperacaoLinha;
import com.streammuse.infrastructure.web.dto.OperacaoLinhaRequest;
import com.streammuse.infrastructure.web.dto.ResultadoOperacaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/operacoes")
public class OperacaoController implements ProcessamentoOperacaoApi {

    private final ProcessadorOperacaoLinha processadorOperacaoLinha;

    public OperacaoController(ProcessadorOperacaoLinha processadorOperacaoLinha) {
        this.processadorOperacaoLinha = processadorOperacaoLinha;
    }

    @Override
    @PostMapping
    public ResponseEntity<ResultadoOperacaoResponse> processarLinha(@Valid @RequestBody OperacaoLinhaRequest request) {
        return ResponseEntity.ok(processadorOperacaoLinha.processar(request.linha()));
    }

    @Override
    @PostMapping("/lote")
    public ResponseEntity<List<ResultadoOperacaoResponse>> processarLote(@RequestBody List<String> linhas) {
        List<ResultadoOperacaoResponse> resultados = linhas.stream()
                .map(processadorOperacaoLinha::processar)
                .toList();
        return ResponseEntity.ok(resultados);
    }
}
