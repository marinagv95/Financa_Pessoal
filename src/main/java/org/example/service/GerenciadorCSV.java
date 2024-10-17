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

    // Método para ler movimentações de um arquivo CSV
    public List<MovimentacaoFinanceira> lerMovimentacoes(String caminhoArquivo) {
        List<MovimentacaoFinanceira> movimentacoes = new ArrayList<>();
        String linha;
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            br.readLine(); // Ignora a linha de cabeçalho
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");
                try {
                    // Remover aspas duplas extras, se existirem
                    String dataStr = dados[0].replace("\"", "").trim();
                    MovimentacaoFinanceira mov = new MovimentacaoFinanceira(
                            sdf.parse(dataStr), // Data
                            dados[1].replace("\"", "").trim(),  // Descrição
                            new BigDecimal(dados[2].replace("\"", "").trim()),  // Valor
                            dados[3].replace("\"", "").trim(),  // Tipo de pagamento
                            dados[4].replace("\"", "").trim()   // Categoria
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

    // Método para escrever movimentações em um arquivo CSV
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

    // Método para adicionar uma nova movimentação ao CSV
    public void adicionarMovimentacaoCSV(MovimentacaoFinanceira movimentacao, String caminhoArquivo) {
        List<MovimentacaoFinanceira> movimentacoes = lerMovimentacoes(caminhoArquivo);
        movimentacoes.add(movimentacao);
        escreverMovimentacoes(movimentacoes, caminhoArquivo);
    }

    // Método para remover uma movimentação do CSV com base na descrição
    public void removerMovimentacaoCSV(String descricao, String caminhoArquivo) {
        List<MovimentacaoFinanceira> movimentacoes = lerMovimentacoes(caminhoArquivo);
        // Remover movimentação pela descrição (ignorando maiúsculas e minúsculas)
        movimentacoes.removeIf(mov -> mov.getDescricao().equalsIgnoreCase(descricao));
        escreverMovimentacoes(movimentacoes, caminhoArquivo);
    }
}
