package org.example.service;

import org.example.model.MovimentacaoFinanceira;
import org.example.util.DataUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class GerenciadorCSV {

    private String arquivoCSV;

    public GerenciadorCSV(String arquivoCSV) {
        this.arquivoCSV = arquivoCSV;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void adicionarMovimentacao(MovimentacaoFinanceira movimentacao) {
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

    public List<MovimentacaoFinanceira> filtrarPorData(List<MovimentacaoFinanceira> movimentacoes, Date dataInicio, Date dataFim) {
        return movimentacoes.stream()
                .filter(mov -> !mov.getData().before(dataInicio) && !mov.getData().after(dataFim))
                .collect(Collectors.toList());
    }

    public void exportarRelatorio(List<MovimentacaoFinanceira> movimentacoes, String arquivoRelatorio) {
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

    public Map<String, BigDecimal> criarResumoMensal(List<MovimentacaoFinanceira> movimentacoes) {
        Map<String, BigDecimal> resumoMensal = new HashMap<>();
        for (MovimentacaoFinanceira mov : movimentacoes) {
            String mesAno = DataUtil.dataParaString(mov.getData());
            resumoMensal.put(mesAno, resumoMensal.getOrDefault(mesAno, BigDecimal.ZERO).add(mov.getValor()));
        }
        return resumoMensal;
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

    public void escreverMovimentacoes(List<MovimentacaoFinanceira> movimentacoes, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write("Data,Descrição,Valor,Tipo de Pagamento,Categoria");
            writer.newLine();
            for (MovimentacaoFinanceira mov : movimentacoes) {
                writer.write(sdf.format(mov.getData()) + "," +
                        mov.getDescricao() + "," +
                        mov.getValor() + "," +
                        mov.getTipoPagamento() + "," +
                        mov.getCategoria());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
