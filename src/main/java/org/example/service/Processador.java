package org.example.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface Processador<T> {
    List<T> filtrarPorCategoria(String categoria);
    BigDecimal calcularTotalDeGasto();
    Map<String, BigDecimal> calcularTotalPorCategoria();
    Map<String, BigDecimal> calcularTotalPorTipoPagamento();
    T encontrarMaiorMovimentacao();
    BigDecimal calcularMediaDeGastos();
}
