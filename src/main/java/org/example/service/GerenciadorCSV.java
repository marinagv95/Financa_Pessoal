package org.example.service;

import org.example.model.MovimentacaoFinanceira;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorCSV {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public List<MovimentacaoFinanceira> lerMovimentacoes(String caminhoArquivo) {
        List<MovimentacaoFinanceira> movimentacoes = new ArrayList<>();
        String linha;
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine();
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                try {

                    String dataStr = dados[0].replace("\"", "").trim();
                    MovimentacaoFinanceira mov = new MovimentacaoFinanceira(
                            sdf.parse(dataStr),
                            dados[1].replace("\"", "").trim(),
                            new BigDecimal(dados[2].replace("\"", "").trim()),
                            dados[3].replace("\"", "").trim(),
                            dados[4].replace("\"", "").trim()
                    );
                    movimentacoes.add(mov);
                } catch (ParseException e) {
                    System.err.println("Erro ao converter data: " + dados[0]);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movimentacoes;
    }

    public void adicionarMovimentacaoCSV(MovimentacaoFinanceira movimentacao, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, true))) {
            writer.write(sdf.format(movimentacao.getData()) + "," +
                    movimentacao.getDescricao() + "," +
                    movimentacao.getValor() + "," +
                    movimentacao.getTipoPagamento() + "," +
                    movimentacao.getCategoria());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
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
