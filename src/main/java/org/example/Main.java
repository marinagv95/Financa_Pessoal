package org.example;

import org.example.translator.TradutorCSV;

public class Main {
    public static void main(String[] args) {
        TradutorCSV tradutorCSV = new TradutorCSV();
        tradutorCSV.traduzirArquivo();
    }
}