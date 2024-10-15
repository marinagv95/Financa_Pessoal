package org.example.service;

import org.example.model.MovimentacaoFinanceira;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProcessadorMovimentacoes implements Processador<MovimentacaoFinanceira> {

    private List<MovimentacaoFinanceira> movimentacoes;

    public ProcessadorMovimentacoes(List<MovimentacaoFinanceira> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }


    @Override
    public List<MovimentacaoFinanceira> filtrarPorCategoria(String categoria) {
        return List.of();
    }

    @Override
    public BigDecimal calcularTotalDeGasto() {
        return null;
    }

    @Override
    public Map<String, BigDecimal> cacularTotalPorCategoria() {
        return Map.of();
    }

    @Override
    public Map<String, BigDecimal> cacularTotalPorTipoPagamento() {
        return Map.of();
    }

    @Override
    public MovimentacaoFinanceira encontrarMaiorMovimentacao() {
        return null;
    }

    @Override
    public BigDecimal calcularMediaDeGastos() {
        return null;
    }
}
