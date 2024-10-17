package org.example;

import org.example.model.MovimentacaoFinanceira;
import org.example.service.GerenciadorCSV;
import org.example.service.ProcessadorMovimentacoes;
import org.example.service.LeitorCSV;
import org.example.translator.TradutorCSV;
import org.example.util.FormatarValor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Scanner scanner = new Scanner(System.in);

        TradutorCSV tradutor = new TradutorCSV();
        tradutor.traduzirArquivo();

        LeitorCSV leitor = new LeitorCSV("financas_pessoais.csv");
        List<MovimentacaoFinanceira> movimentacoes = leitor.lerMovimentacoes();

        ProcessadorMovimentacoes processador = new ProcessadorMovimentacoes(movimentacoes);

        while (true) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Exibir Total de Gastos");
            System.out.println("2. Exibir Média de Gastos");
            System.out.println("3. Exibir Maior Movimentação");
            System.out.println("4. Exibir Total por Categoria");
            System.out.println("5. Exibir Total por Tipo de Pagamento");
            System.out.println("6. Adicionar Movimentação");
            System.out.println("7. Remover Movimentação");
            System.out.println("8. Exportar todas as Movimentações para CSV");
            System.out.println("9. Filtrar Movimentações por Data");
            System.out.println("10. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    BigDecimal totalGastos = processador.calcularTotalDeGasto();
                    System.out.println("Total de Gastos: " + FormatarValor.formatarValor(totalGastos));
                    break;

                case 2:
                    BigDecimal mediaGastos = processador.calcularMediaDeGastos();
                    System.out.println("Média de Gastos: " + FormatarValor.formatarValor(mediaGastos));
                    break;

                case 3:
                    MovimentacaoFinanceira maiorMovimentacao = processador.encontrarMaiorMovimentacao();
                    if (maiorMovimentacao != null) {
                        System.out.println("Maior Movimentação: " + maiorMovimentacao.getDescricao() +
                                " - Valor: " + FormatarValor.formatarValor(maiorMovimentacao.getValor()));
                    } else {
                        System.out.println("Nenhuma movimentação encontrada.");
                    }
                    break;

                case 4:
                    System.out.println("Total por Categoria:");
                    processador.calcularTotalPorCategoria().forEach((categoria, total) ->
                            System.out.println(categoria + ": " + FormatarValor.formatarValor(total)));
                    break;

                case 5:
                    System.out.println("Total por Tipo de Pagamento:");
                    processador.calcularTotalPorTipoPagamento().forEach((tipoPagamento, total) ->
                            System.out.println(tipoPagamento + ": " + FormatarValor.formatarValor(total)));
                    break;

                case 6:
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


                        processador.adicionarMovimentacao(novaMovimentacao);
                        leitor.adicionarMovimentacao(novaMovimentacao);
                        System.out.println("Movimentação adicionada com sucesso!");

                    } catch (ParseException e) {
                        System.err.println("Erro ao inserir a data.");
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao inserir o valor.");
                    }
                    break;

                case 7:
                    System.out.println("Digite a descrição da movimentação a ser removida: ");
                    String descricaoRemover = scanner.nextLine().trim();
                    if (processador.removerMovimentacao(descricaoRemover)) {
                        GerenciadorCSV gerenciadorCSV = new GerenciadorCSV();
                        gerenciadorCSV.escreverMovimentacoes(processador.filtrarPorCategoria(""), "financas_pessoais.csv");
                        System.out.println("Movimentação removida com sucesso!");
                    } else {
                        System.out.println("Movimentação não encontrada.");
                    }
                    break;

                case 8:
                    GerenciadorCSV gerenciadorCSV = new GerenciadorCSV();
                    gerenciadorCSV.escreverMovimentacoes(movimentacoes, "todas_movimentacoes.csv");
                    System.out.println("Arquivo CSV gerado com sucesso: todas_movimentacoes.csv");
                    break;

                case 9:
                    try {
                        System.out.println("Digite a data inicial (dd/MM/yyyy): ");
                        String dataInicialStr = scanner.nextLine().trim();
                        Date dataInicial = sdf.parse(dataInicialStr);

                        System.out.println("Digite a data final (dd/MM/yyyy): ");
                        String dataFinalStr = scanner.nextLine().trim();
                        Date dataFinal = sdf.parse(dataFinalStr);

                        List<MovimentacaoFinanceira> movimentacoesPorData = processador.filtrarPorData(dataInicial, dataFinal);
                        if (movimentacoesPorData.isEmpty()) {
                            System.out.println("Nenhuma movimentação encontrada no intervalo de datas fornecido.");
                        } else {
                            System.out.println("Movimentações entre " + dataInicialStr + " e " + dataFinalStr + ":");
                            movimentacoesPorData.forEach(mov ->
                                    System.out.println(mov.getData() + " - " + mov.getDescricao() + " - " + FormatarValor.formatarValor(mov.getValor()))
                            );
                        }
                    } catch (ParseException e) {
                        System.out.println("Erro ao inserir as datas. Verifique o formato (dd/MM/yyyy).");
                    }
                    break;

                case 10: // Sair
                    System.out.println("Encerrando o programa.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }
}
