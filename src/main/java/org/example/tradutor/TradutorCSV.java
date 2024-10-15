package org.example.tradutor;

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
                            "Data", "Descrição", "Valor", "Tipo de Pagamento", "Categoria", "Conta"
                    };
                    writer.writeNext(translatedHeader);
                    isFirstLine = false;
                } else {
                    String[] translatedLine = new String[nextLine.length];
                    translatedLine[0] = Tradutor.traduzir(nextLine[0]);
                    translatedLine[1] = Tradutor.traduzir(nextLine[1]);
                    translatedLine[2] = Tradutor.traduzir(nextLine[2]);
                    translatedLine[3] = Tradutor.traduzir(nextLine[3]);
                    translatedLine[4] = Tradutor.traduzir(nextLine[4]);
                    translatedLine[5] = Tradutor.traduzir(nextLine[5]);

                    writer.writeNext(translatedLine);
                }
            }
            System.out.println("Arquivo traduzido com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
