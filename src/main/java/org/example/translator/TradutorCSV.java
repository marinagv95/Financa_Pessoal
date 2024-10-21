package org.example.translator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.example.util.DataUtil;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

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

                    Date data = DataUtil.stringParaData(nextLine[0]);
                    translatedLine[0] = DataUtil.dataParaString(data);
                    translatedLine[1] = Tradutor.traduzir(nextLine[1]);
                    translatedLine[2] = nextLine[2];
                    translatedLine[3] = Tradutor.traduzir(nextLine[3]);
                    translatedLine[4] = Tradutor.traduzir(nextLine[4]);

                    writer.writeNext(translatedLine);
                }
            }
            System.out.println("Arquivo traduzido com sucesso!");
        } catch (IOException | ParseException e) {
            System.err.println("Erro ao ler ou escrever no arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}
