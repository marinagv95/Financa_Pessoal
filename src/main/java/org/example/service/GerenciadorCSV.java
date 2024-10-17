package org.example.service;


import org.example.model.MovimentacaoFinanceira;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorCSV {

    public List<MovimentacaoFinanceira> lerMovimentacoes(String caminhoArquivo) {
        List<MovimentacaoFinanceira> movimentacoes = new ArrayList<>();
        String linha;
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                MovimentacaoFinanceira mov = new MovimentacaoFinanceira(
                        dados[0],
                        dados[1],
                        dados[2],
                        new BigDecimal(dados[3]),
                        dados[4]
                );
                movimentacoes.add(mov);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movimentacoes;
    }

    public void escreverMovimentacoes(List<MovimentacaoFinanceira> movimentacoes, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write("Descrição,Categoria,Tipo de Pagamento,Valor,Data");
            writer.newLine();
            for (MovimentacaoFinanceira mov : movimentacoes) {
                writer.write(mov.getDescricao() + "," + mov.getCategoria() + "," +
                        mov.getTipoPagamento() + "," + mov.getValor() + "," + mov.getData());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gerarCSVOrganizado(List<MovimentacaoFinanceira> movimentacoes, String caminhoArquivo, BigDecimal valorMinimo) {
        List<MovimentacaoFinanceira> movimentacoesFiltradas = new ArrayList<>();
        for (MovimentacaoFinanceira mov : movimentacoes) {
            if (mov.getValor().compareTo(valorMinimo) > 0) {
                movimentacoesFiltradas.add(mov);
            }
        }
        escreverMovimentacoes(movimentacoesFiltradas, caminhoArquivo);
    }
}
