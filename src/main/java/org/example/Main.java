package org.example;

import org.example.model.MovimentacaoFinanceira;
import org.example.service.GerenciadorCSV;
import org.example.service.ProcessadorMovimentacoes;
import org.example.service.LeitorCSV;
import org.example.translator.TradutorCSV;
import org.example.util.FormatarValor;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        TradutorCSV tradutor = new TradutorCSV();
        tradutor.traduzirArquivo();

        LeitorCSV leitor = new LeitorCSV("financas_pessoais.csv");
        List<MovimentacaoFinanceira> movimentacoes = leitor.lerMovimentacoes();

        ProcessadorMovimentacoes processador = new ProcessadorMovimentacoes(movimentacoes);

        BigDecimal totalGastos = processador.calcularTotalDeGasto();
        System.out.println("Total de Gastos: " + FormatarValor.formatarValor(totalGastos));

        System.out.println("----------------------------------------------------------------");
        BigDecimal mediaGastos = processador.calcularMediaDeGastos();
        System.out.println("Média de Gastos: " + FormatarValor.formatarValor(mediaGastos));

        System.out.println("----------------------------------------------------------------");
        MovimentacaoFinanceira maiorMovimentacao = processador.encontrarMaiorMovimentacao();
        if (maiorMovimentacao != null) {
            System.out.println("Maior Movimentação: " + maiorMovimentacao.getDescricao() +
                    " - Valor: " + FormatarValor.formatarValor(maiorMovimentacao.getValor()));
        } else {
            System.out.println("Nenhuma movimentação encontrada.");
        }

        System.out.println("----------------------------------------------------------------");
        System.out.println("Total por Categoria:");
        processador.calcularTotalPorCategoria().forEach((categoria, total) ->
                System.out.println(categoria + ": " + FormatarValor.formatarValor(total)));

        System.out.println("----------------------------------------------------------------");
        System.out.println("Total por Tipo de Pagamento:");
        processador.calcularTotalPorTipoPagamento().forEach((tipoPagamento, total) ->
                System.out.println(tipoPagamento + ": " + FormatarValor.formatarValor(total)));

        System.out.println("----------------------------------------------------------------");
        GerenciadorCSV gerenciadorCSV = new GerenciadorCSV();
        gerenciadorCSV.escreverMovimentacoes(movimentacoes, "todas_movimentacoes.csv");
        System.out.println("Arquivo CSV gerado com sucesso: todas_movimentacoes.csv");
    }
}
