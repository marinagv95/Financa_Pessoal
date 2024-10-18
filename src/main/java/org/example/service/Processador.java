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


    List<MovimentacaoFinanceira> filtrarPorData(Date dataInicial, Date dataFinal);
    void adicionarMovimentacao(MovimentacaoFinanceira movimentacao);
    boolean removerMovimentacao(Date data, String descricao);

    Map<String, Long> filtrarRecorrentes();
    Map<String, BigDecimal> criarResumoMensal();
    List<Map.Entry<String, BigDecimal>> ordenarResumoPorMesAno(Map<String, BigDecimal> resumo);

}
