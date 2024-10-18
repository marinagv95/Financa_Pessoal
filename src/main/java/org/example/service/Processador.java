package org.example.service;

import org.example.model.MovimentacaoFinanceira;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface Processador<T> {
    List<T> filtrarPorCategoria(String categoria);
    BigDecimal calcularTotalDeGasto();
    Map<String, BigDecimal> calcularTotalPorCategoria();
    Map<String, BigDecimal> calcularTotalPorTipoPagamento();
    T encontrarMaiorMovimentacao();
    BigDecimal calcularMediaDeGastos();


    List<MovimentacaoFinanceira> filtrarPorData(Date dataInicial, Date dataFinal);
    Map<String, Map<String, BigDecimal>> resumoMensalPorCategoria();
    void adicionarMovimentacao(MovimentacaoFinanceira movimentacao);
    boolean removerMovimentacao(Date data, String descricao);
}
