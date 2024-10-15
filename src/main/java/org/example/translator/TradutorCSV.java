package org.example.translator;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TradutorCSV {
    private String inputFile = "personal_transactions.csv";
    private String outputFile = "financas_pessoais.csv";

    public void traduzirArquivo() {
        try (CSVReader reader = new CSVReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String[] nextLine;
            boolean isFirstLine = true;

            while ((nextLine = reader.readNext()) != null) {
                if (isFirstLine) {
                    String header = String.join(",", "Data", "Descrição", "Valor", "Pagamento", "Categoria", "Conta");
                    writer.write(header);
                    writer.newLine();
                    isFirstLine = false;
                }

                String data = nextLine[0];
                String descricao = Tradutor.traduzir(nextLine[1]);
                String valor = nextLine[2];
                String tipoPagamento = Tradutor.traduzir(nextLine[3]);
                String categoria = Tradutor.traduzir(nextLine[4]);
                String conta = Tradutor.traduzir(nextLine[5]);

                String formattedLine = String.join(",",
                        data, descricao, valor, tipoPagamento, categoria, conta);

                writer.write(formattedLine);
                writer.newLine();
            }
            System.out.println("Arquivo traduzido com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
