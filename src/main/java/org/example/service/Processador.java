package org.example.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface Processador<T> {
    List<T> filtrarPorCategoria(String categoria);
    BigDecimal calcularTotalDeGasto();
    Map<String, BigDecimal> cacularTotalPorCategoria();
    Map<String, BigDecimal> cacularTotalPorTipoPagamento();
    T encontrarMaiorMovimentacao();
    BigDecimal calcularMediaDeGastos();
}
