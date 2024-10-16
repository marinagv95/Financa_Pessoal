package org.example.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.model.MovimentacaoFinanceira;
import org.example.util.DataUtil;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
}
