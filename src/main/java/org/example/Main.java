package org.example;

import org.example.model.MovimentacaoFinanceira;
import org.example.service.GerenciadorCSV;
import org.example.service.ProcessadorMovimentacoes;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Scanner scanner = new Scanner(System.in);
        GerenciadorCSV gerenciadorCSV = new GerenciadorCSV();
        String caminhoArquivo = "financas_pessoais.csv";  // Caminho do arquivo CSV
        ProcessadorMovimentacoes processador = new ProcessadorMovimentacoes(gerenciadorCSV.lerMovimentacoes(caminhoArquivo));

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Adicionar Movimentação");
            System.out.println("2. Remover Movimentação");
            System.out.println("3. Filtrar Movimentações por Data");
            System.out.println("4. Exportar Relatório");
            System.out.println("5. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir a nova linha

            switch (opcao) {
                case 1: // Adicionar Movimentação
                    try {
                        System.out.println("Digite a data (dd/MM/yyyy): ");
                        String dataStr = scanner.nextLine().trim();
                        Date data = sdf.parse(dataStr);

                        System.out.println("Digite a descrição: ");
                        String descricao = scanner.nextLine().trim();

                        System.out.println("Digite o valor: ");
                        BigDecimal valor = new BigDecimal(scanner.nextLine().trim());

                        System.out.println("Digite o tipo de pagamento: ");
                        String tipoPagamento = scanner.nextLine().trim();

                        System.out.println("Digite a categoria: ");
                        String categoria = scanner.nextLine().trim();

                        MovimentacaoFinanceira novaMovimentacao = new MovimentacaoFinanceira(data, descricao, valor, tipoPagamento, categoria);

                        // Adicionar a movimentação no processador e no CSV
                        processador.adicionarMovimentacao(novaMovimentacao);
                        gerenciadorCSV.adicionarMovimentacaoCSV(novaMovimentacao, caminhoArquivo);
                        System.out.println("Movimentação adicionada com sucesso!");

                    } catch (ParseException e) {
                        System.err.println("Erro ao inserir a data.");
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao inserir o valor.");
                    }
                    break;

                case 2: // Remover Movimentação
                    System.out.println("Digite a descrição da movimentação a ser removida: ");
                    String descricaoRemover = scanner.nextLine().trim();
                    if (processador.removerMovimentacao(descricaoRemover)) {
                        gerenciadorCSV.removerMovimentacaoCSV(descricaoRemover, caminhoArquivo);
                        System.out.println("Movimentação removida com sucesso!");
                    } else {
                        System.out.println("Movimentação não encontrada.");
                    }
                    break;


                case 3: // Filtrar Movimentações por Data
                    try {
                        System.out.println("Digite a data inicial (dd/MM/yyyy): ");
                        Date dataInicial = sdf.parse(scanner.nextLine());

                        System.out.println("Digite a data final (dd/MM/yyyy): ");
                        Date dataFinal = sdf.parse(scanner.nextLine());

                        processador.filtrarPorData(dataInicial, dataFinal).forEach(System.out::println);

                    } catch (ParseException e) {
                        System.out.println("Erro ao inserir as datas.");
                    }
                    break;

                case 4: // Exportar Relatório
                    System.out.println("Gerando relatório em CSV...");
                    gerenciadorCSV.escreverMovimentacoes(processador.filtrarPorCategoria(""), "new_relatorio_financas.csv");
                    System.out.println("Relatório gerado com sucesso: new_relatorio_financas.csv");
                    break;

                case 5: // Sair
                    System.out.println("Encerrando o programa.");
                    // Atualiza o arquivo CSV final com as movimentações
                    gerenciadorCSV.escreverMovimentacoes(processador.filtrarPorCategoria(""), caminhoArquivo);
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }

            // Após qualquer operação (adicionar/remover), sempre atualiza o arquivo CSV
            gerenciadorCSV.escreverMovimentacoes(processador.filtrarPorCategoria(""), caminhoArquivo);
        }
    }
}
