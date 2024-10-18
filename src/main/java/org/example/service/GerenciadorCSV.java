package org.example.service;

import org.example.model.MovimentacaoFinanceira;
import org.example.util.DataUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class GerenciadorCSV {

    private String arquivoCSV;
    ProcessadorMovimentacoes processadorMovimentacoes;

    public GerenciadorCSV(String arquivoCSV, List<MovimentacaoFinanceira> movimentacoes) {
        this.arquivoCSV = arquivoCSV;
        this.processadorMovimentacoes = new ProcessadorMovimentacoes(movimentacoes);
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    DecimalFormat df = new DecimalFormat("#,##0.00");

    public void adicionarMovimentacao(MovimentacaoFinanceira movimentacao) {
        processadorMovimentacoes.adicionarMovimentacao(movimentacao);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoCSV, true))) {
            writer.write(DataUtil.dataParaString(movimentacao.getData()) + "," +
                    movimentacao.getDescricao() + "," +
                    movimentacao.getValor() + "," +
                    movimentacao.getTipoPagamento() + "," +
                    movimentacao.getCategoria());
            writer.newLine();
            System.out.println("Movimentação adicionada com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao adicionar movimentação: " + e.getMessage());
        }
    }

    public void exportarRelatorio(String arquivoRelatorio) {
        List<MovimentacaoFinanceira> movimentacoes = processadorMovimentacoes.filtrarPorCategoria("Todas");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoRelatorio))) {
            writer.write("Data,Descrição,Valor,Pagamento,Categoria");
            writer.newLine();
            for (MovimentacaoFinanceira mov : movimentacoes) {
                writer.write(DataUtil.dataParaString(mov.getData()) + "," +
                        mov.getDescricao() + "," +
                        mov.getValor() + "," +
                        mov.getTipoPagamento() + "," +
                        mov.getCategoria());
                writer.newLine();
            }
            System.out.println("Relatório exportado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao exportar relatório: " + e.getMessage());
        }
    }

    public boolean escreverMovimentacoes(List<MovimentacaoFinanceira> movimentacoes, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write("Data,Descrição,Valor,Pagamento,Categoria");
            writer.newLine();
            for (MovimentacaoFinanceira mov : movimentacoes) {
                writer.write(sdf.format(mov.getData()) + "," +
                        mov.getDescricao() + "," +
                        df.format(mov.getValor()) + "," +
                        mov.getTipoPagamento() + "," +
                        mov.getCategoria());
                writer.newLine();
            }
            System.out.println("Arquivo CSV gerado com sucesso: " + caminhoArquivo);
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
            return false;
        }
    }


    public void gerarResumoMensalCSV(Map<String, BigDecimal> resumo) {
        if (resumo.isEmpty()) {
            System.out.println("Não há movimentações para gerar o CSV.");
            return;
        }
        List<Map.Entry<String, BigDecimal>> sortedEntries = processadorMovimentacoes.ordenarResumoPorMesAno(resumo);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resumo_mensal.txt"))) {
            writer.write("MÊS/ANO   | TOTAL GASTOS");
            writer.newLine();
            writer.write("---------------------------");
            writer.newLine();

            for (Map.Entry<String, BigDecimal> entry : sortedEntries) {
                String mesAno = entry.getKey();
                String totalFormatado = String.format("%,.2f", entry.getValue()).replace('.', ',');
                writer.write(String.format("%-10s | %s", mesAno, totalFormatado));
                writer.newLine();
            }

            writer.write("---------------------------");
            writer.newLine();

            System.out.println("Resumo mensal gerado com sucesso em resumo_mensal.txt!");
        } catch (IOException e) {
            System.err.println("Erro ao criar o arquivo TXT: " + e.getMessage());
        }
    }
}
