package org.example.visual;

import org.example.model.MovimentacaoFinanceira;
import org.example.service.GerenciadorCSV;
import org.example.service.ProcessadorMovimentacoes;
import org.example.util.FormatarValor;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VisualMenu {
    private Scanner leitura;
    private SimpleDateFormat sdf;
    private ProcessadorMovimentacoes processador;
    private GerenciadorCSV gerenciadorCSV;
    private List<MovimentacaoFinanceira> movimentacoes;
    private Menu menu;

    public VisualMenu(ProcessadorMovimentacoes processador, GerenciadorCSV gerenciadorCSV, List<MovimentacaoFinanceira> movimentacoes) {
        this.leitura = new Scanner(System.in);
        this.sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.processador = processador;
        this.gerenciadorCSV = gerenciadorCSV;
        this.movimentacoes = movimentacoes;
        this.menu = new Menu();
    }

    public void exibirMenu() {
        while (true) {
            int opcao = menu.exibirMenuPrincipal();

            switch (opcao) {
                case 1:
                    exibirMenuGerenciamento();
                    break;
                case 2:
                    exibirMenuRelatorios();
                    break;
                case 3:
                    System.out.println("Encerrando o programa.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void exibirMenuGerenciamento() {
        while (true) {
            int opcao = menu.exibirMenuGerenciamento();

            switch (opcao) {
                case 1:
                    adicionarMovimentacao();
                    break;
                case 2:
                    removerMovimentacao();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void exibirMenuRelatorios() {
        while (true) {
            int opcao = menu.exibirMenuRelatorios();

            switch (opcao) {
                case 1:
                    exibirTotalDeGastos();
                    break;
                case 2:
                    resumoMensal();
                    break;
                case 3:
                    exibirMaiorMovimentacao();
                    break;
                case 4:
                    exibirTotalPorCategoria();
                    break;
                case 5:
                    exibirTotalPorTipoPagamento();
                    break;
                case 6:
                    filtrarMovimentacoesPorData();
                    break;
                case 7:
                    filtrarMovimentacoesRecorrentes();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    private void exibirTotalDeGastos() {
        BigDecimal totalGastos = processador.calcularTotalDeGasto();
        System.out.println("Total de Gastos: " + FormatarValor.formatarValor(totalGastos));
        Menu.aguardarContinuacao(leitura);
    }

    private void exibirMaiorMovimentacao() {
        MovimentacaoFinanceira maiorMovimentacao = processador.encontrarMaiorMovimentacao();
        if (maiorMovimentacao != null) {
            System.out.println("Maior Movimentação: " + maiorMovimentacao.getDescricao() +
                    " - Valor: " + FormatarValor.formatarValor(maiorMovimentacao.getValor()));
        } else {
            System.out.println("Nenhuma movimentação encontrada.");
        }
        Menu.aguardarContinuacao(leitura);
    }

    private void exibirTotalPorCategoria() {
        System.out.println("Total por Categoria:");
        processador.calcularTotalPorCategoria().forEach((categoria, total) ->
                System.out.println(categoria + ": " + FormatarValor.formatarValor(total)));

        Menu.aguardarContinuacao(leitura);
    }


    private void exibirTotalPorTipoPagamento() {
        System.out.println("Total por Tipo de Pagamento:");
        processador.calcularTotalPorTipoPagamento().forEach((tipoPagamento, total) ->
                System.out.println(tipoPagamento + ": " + FormatarValor.formatarValor(total)));

        Menu.aguardarContinuacao(leitura);
    }

    private void adicionarMovimentacao() {
        try {
            System.out.println("Digite a data (dd/MM/yyyy): ");
            String dataStr = leitura.nextLine().trim();
            Date data = sdf.parse(dataStr);

            System.out.println("Digite a descrição: ");
            String descricao = leitura.nextLine().trim();

            System.out.println("Digite o valor: ");
            BigDecimal valor = new BigDecimal(leitura.nextLine().trim());

            System.out.println("Digite o tipo de pagamento: ");
            String tipoPagamento = leitura.nextLine().trim();

            System.out.println("Digite a categoria: ");
            String categoria = leitura.nextLine().trim();

            MovimentacaoFinanceira novaMovimentacao = new MovimentacaoFinanceira(data, descricao, valor, tipoPagamento, categoria);
            processador.adicionarMovimentacao(novaMovimentacao);
            gerenciadorCSV.adicionarMovimentacao(novaMovimentacao);

            System.out.println("Movimentação adicionada com sucesso!");
            Menu.aguardarContinuacao(leitura);
        } catch (ParseException e) {
            System.err.println("Erro ao inserir a data.");
        } catch (NumberFormatException e) {
            System.err.println("Erro ao inserir o valor.");
        }
    }

    private void removerMovimentacao() {
        try {
            System.out.println("Digite a data da movimentação a ser removida (dd/MM/yyyy): ");
            String dataStr = leitura.nextLine().trim();
            Date data = sdf.parse(dataStr);

            System.out.println("Digite a descrição da movimentação a ser removida: ");
            String descricaoRemover = leitura.nextLine().trim();

            if (processador.removerMovimentacao(data, descricaoRemover)) {
                gerenciadorCSV.escreverMovimentacoes(movimentacoes, "financas_pessoais.csv");
                System.out.println("Movimentação removida com sucesso!");
            } else {
                System.out.println("Movimentação não encontrada.");
            }
            Menu.aguardarContinuacao(leitura);
        } catch (ParseException e) {
            System.err.println("Erro ao inserir a data. Verifique o formato (dd/MM/yyyy).");
        }
    }

    private void filtrarMovimentacoesPorData() {
        try {
            System.out.println("Digite a data inicial (dd/MM/yyyy): ");
            String dataInicialStr = leitura.nextLine().trim();
            Date dataInicial = sdf.parse(dataInicialStr);

            System.out.println("Digite a data final (dd/MM/yyyy): ");
            String dataFinalStr = leitura.nextLine().trim();
            Date dataFinal = sdf.parse(dataFinalStr);
            if (dataInicial.after(dataFinal)) {
                System.out.println("A data inicial não pode ser posterior à data final.");
                return;
            }
            gerenciadorCSV.gerarRelatorioPorData(dataInicial, dataFinal, dataInicialStr, dataFinalStr);

            Menu.aguardarContinuacao(leitura);
        } catch (ParseException e) {
            System.out.println("Erro ao inserir as datas. Verifique o formato (dd/MM/yyyy).");
        }
    }

    private void filtrarMovimentacoesRecorrentes() {
        gerenciadorCSV.movimentacoesRecorrentes();
        Menu.aguardarContinuacao(leitura);
    }

    private void resumoMensal() {
        Map<String, BigDecimal> resumo = processador.criarResumoMensal();
        gerenciadorCSV.gerarResumoMensalTXT(resumo);
        Menu.aguardarContinuacao(leitura);
    }

}
