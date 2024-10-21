package org.example;

import org.example.model.MovimentacaoFinanceira;
import org.example.service.GerenciadorCSV;
import org.example.service.LeitorCSV;
import org.example.service.ProcessadorMovimentacoes;
import org.example.translator.TradutorCSV;
import org.example.visual.VisualMenu;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TradutorCSV tradutor = new TradutorCSV();
        tradutor.traduzirArquivo();

        LeitorCSV leitor = new LeitorCSV("financas_pessoais.csv");
        List<MovimentacaoFinanceira> movimentacoes = leitor.lerMovimentacoes();
        GerenciadorCSV gerenciadorCSV = new GerenciadorCSV("financas_pessoais.csv", movimentacoes);
        ProcessadorMovimentacoes processador = new ProcessadorMovimentacoes(movimentacoes);

        VisualMenu menu = new VisualMenu(processador, gerenciadorCSV, movimentacoes);
        menu.exibirMenu();
    }
}