package org.example.service;

import org.example.model.MovimentacaoFinanceira;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessadorMovimentacoes implements Processador<MovimentacaoFinanceira> {

    private List<MovimentacaoFinanceira> movimentacoes;

    public ProcessadorMovimentacoes(List<MovimentacaoFinanceira> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }


    @Override
    public List<MovimentacaoFinanceira> filtrarPorCategoria(String categoria) {
        return movimentacoes.stream()
                .filter(m -> m.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calcularTotalDeGasto() {
        return movimentacoes.stream()
                .map(MovimentacaoFinanceira::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Map<String, BigDecimal> calcularTotalPorCategoria() {
        return movimentacoes.stream()
                .collect(Collectors.groupingBy(MovimentacaoFinanceira::getCategoria,
                        Collectors.mapping(MovimentacaoFinanceira::getValor,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    public Map<String, BigDecimal> calcularTotalPorTipoPagamento() {
        return movimentacoes.stream()
                .collect(Collectors.groupingBy(MovimentacaoFinanceira::getTipoPagamento,
                        Collectors.mapping(MovimentacaoFinanceira::getValor,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Override
    public MovimentacaoFinanceira encontrarMaiorMovimentacao() {
        return movimentacoes.stream()
                .max(Comparator.comparing(MovimentacaoFinanceira::getValor))
                .orElse(null);
    }

    @Override
    public BigDecimal calcularMediaDeGastos() {
        return calcularTotalDeGasto().divide(BigDecimal.valueOf(movimentacoes.size()),
                BigDecimal.ROUND_HALF_UP);
    }
}
