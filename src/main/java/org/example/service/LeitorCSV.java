package org.example.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.example.model.MovimentacaoFinanceira;
import org.example.util.DataUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class LeitorCSV {

    private String arquivoCSV;

    public LeitorCSV(String arquivoCSV) {
        this.arquivoCSV = arquivoCSV;
    }

    public List<MovimentacaoFinanceira> lerMovimentacoes() {
        List<MovimentacaoFinanceira> movimentacoes = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(arquivoCSV))) {
            String[] linha;
            reader.readNext();
            int linhaIndex = 2;
            while ((linha = reader.readNext()) != null) {
                linhaIndex++;
                if (linha.length < 5) {
                    System.err.println("Linha inválida na linha " + linhaIndex + ": " + String.join(", ", linha));
                    continue;
                }
                Date data;
                try {
                    data = DataUtil.stringParaData(linha[0]);
                } catch (ParseException e) {
                    System.err.println("Erro ao converter a data na linha " + linhaIndex + ": " + linha[0]);
                    continue;
                }
                String descricao = linha[1];
                BigDecimal valor;
                try {
                    valor = new BigDecimal(linha[2]);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter o valor na linha " + linhaIndex + ": " + linha[2]);
                    continue;
                }
                String tipoPagamento = linha[3];
                String categoria = linha[4];
                MovimentacaoFinanceira movimentacao = new MovimentacaoFinanceira(data, descricao, valor, tipoPagamento, categoria);
                movimentacoes.add(movimentacao);
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
            e.printStackTrace();
        }
        return movimentacoes;
    }

    public void adicionarMovimentacao(MovimentacaoFinanceira movimentacao) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(arquivoCSV, true))) {
            String[] novaLinha = {
                    DataUtil.dataParaString(movimentacao.getData()),
                    movimentacao.getDescricao(),
                    movimentacao.getValor().toString(),
                    movimentacao.getTipoPagamento(),
                    movimentacao.getCategoria()
            };
            writer.writeNext(novaLinha);
            System.out.println("Movimentação adicionada com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao adicionar movimentação: " + e.getMessage());
        }
    }

    public List<MovimentacaoFinanceira> filtrarPorData(Date dataInicio, Date dataFim) {
        return lerMovimentacoes().stream()
                .filter(mov -> !mov.getData().before(dataInicio) && !mov.getData().after(dataFim))
                .collect(Collectors.toList());
    }

    public void exportarRelatorio(String arquivoRelatorio) {
        List<MovimentacaoFinanceira> movimentacoes = lerMovimentacoes();
        try (CSVWriter writer = new CSVWriter(new FileWriter(arquivoRelatorio))) {
            String[] cabecalho = { "Data", "Descrição", "Valor", "Pagamento", "Categoria" };
            writer.writeNext(cabecalho);
            for (MovimentacaoFinanceira mov : movimentacoes) {
                String[] linha = {
                        DataUtil.dataParaString(mov.getData()),
                        mov.getDescricao(),
                        mov.getValor().toString(),
                        mov.getTipoPagamento(),
                        mov.getCategoria()
                };
                writer.writeNext(linha);
            }
            System.out.println("Relatório exportado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao exportar relatório: " + e.getMessage());
        }
    }

    public Map<String, BigDecimal> criarResumoMensal() {
        Map<String, BigDecimal> resumoMensal = new HashMap<>();
        List<MovimentacaoFinanceira> movimentacoes = lerMovimentacoes();
        for (MovimentacaoFinanceira mov : movimentacoes) {
            String mesAno = DataUtil.dataParaString(mov.getData());
            resumoMensal.put(mesAno, resumoMensal.getOrDefault(mesAno, BigDecimal.ZERO).add(mov.getValor()));
        }
        return resumoMensal;
    }

    public void definirOrcamentoMensal(String mesAno, BigDecimal valor) {
        try (CSVWriter writer = new CSVWriter(new FileWriter("orcamentos.csv", true))) {
            String[] orcamento = { mesAno, valor.toString() };
            writer.writeNext(orcamento);
            System.out.println("Orçamento definido com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao definir orçamento: " + e.getMessage());
        }
    }
}