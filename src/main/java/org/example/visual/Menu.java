package org.example.visual;

import java.util.Scanner;

public class Menu {
    private Scanner leitura;

    public Menu() {
        this.leitura = new Scanner(System.in);
    }

    public int exibirMenuPrincipal() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Gerenciar Arquivos");
        System.out.println("2. Buscar Relatórios");
        System.out.println("3. Sair");

        return leitura.nextInt();
    }

    public int exibirMenuGerenciamento() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Adicionar Movimentação");
        System.out.println("2. Remover Movimentação");
        System.out.println("3. Exportar todas as Movimentações para CSV");
        System.out.println("4. Voltar ao Menu Principal");

        return leitura.nextInt();
    }

    public int exibirMenuRelatorios() {
        System.out.println("Escolha uma opção:");
        System.out.println("1. Exibir Total de Gastos");
        System.out.println("2. Exibir Média de Gastos");
        System.out.println("3. Exibir Maior Movimentação");
        System.out.println("4. Exibir Total por Categoria");
        System.out.println("5. Exibir Total por Tipo de Pagamento");
        System.out.println("6. Filtrar Movimentações por Data");
        System.out.println("7. Filtrar Movimentações Recorrentes");
        System.out.println("8. Voltar ao Menu Principal");

        return leitura.nextInt();
    }

    public static String ler(Scanner entrada, String texto) {
        if (entrada == null) {
            System.err.println("Erro: O Scanner está nulo.");
        }
        System.out.println(texto);
        return entrada.nextLine();
    }

    public static void aguardarContinuacao(Scanner entrada) {
        System.out.println("\nPressione Enter para continuar...");
        ler(entrada, "");
    }
}
