package org.example;

import org.example.translator.TradutorCSV;
import org.example.translator.TradutorTXT;

public class Main {
    public static void main(String[] args) {
        TradutorCSV tradutorCSV = new TradutorCSV();
        tradutorCSV.traduzirArquivo();
    }
}