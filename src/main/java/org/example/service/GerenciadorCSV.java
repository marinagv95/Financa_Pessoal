package org.example.service;

import org.example.model.MovimentacaoFinanceira;
import org.example.util.DataUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

    public Map<String, BigDecimal> criarResumoMensal() {
        return processadorMovimentacoes.resumoMensalPorCategoria()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().values().stream().reduce(BigDecimal.ZERO, BigDecimal::add)
                ));
    }

    public void definirOrcamentoMensal(String mesAno, BigDecimal valor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("orcamentos.csv", true))) {
            writer.write(mesAno + "," + valor);
            writer.newLine();
            System.out.println("Orçamento definido com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao definir orçamento: " + e.getMessage());
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
}
