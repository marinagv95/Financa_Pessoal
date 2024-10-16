package org.example.translator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TradutorCSV {
    private String inputFile = "personal_transactions.csv";
    private String outputFile = "financas_pessoais.csv";

    public void traduzirArquivo() {
        try (CSVReader reader = new CSVReader(new FileReader(inputFile));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {

            String[] nextLine;
            boolean isFirstLine = true;

            while ((nextLine = reader.readNext()) != null) {
                if (isFirstLine) {
                    String[] translatedHeader = {
                            "Data", "Descrição", "Valor", "Pagamento", "Categoria"
                    };
                    writer.writeNext(translatedHeader);
                    isFirstLine = false;
                } else {
                    String[] translatedLine = new String[5];
                    translatedLine[0] = nextLine[0];
                    translatedLine[1] = Tradutor.traduzir(nextLine[1]);
                    translatedLine[2] = nextLine[2];
                    translatedLine[3] = Tradutor.traduzir(nextLine[3]);
                    translatedLine[4] = Tradutor.traduzir(nextLine[4]);
                    writer.writeNext(translatedLine);
                }
            }
            System.out.println("Arquivo traduzido com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao ler ou escrever no arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (CsvValidationException e) {
            System.err.println("Erro na validação do CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
