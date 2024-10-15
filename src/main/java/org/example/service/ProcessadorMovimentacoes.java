package org.example.service;

import org.example.model.MovimentacaoFinanceira;

import java.util.List;

public class ProcessadorMovimentacoes {
    private List<MovimentacaoFinanceira> movimentacoes;

    public ProcessadorMovimentacoes(List<MovimentacaoFinanceira> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }


}
