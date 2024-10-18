package org.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
    private static final String FORMATO_ENTRADA = "MM/dd/yyyy";
    private static final String FORMATO_SAIDA = "dd/MM/yyyy";

    public static Date stringParaData(String dataString) throws ParseException {
        SimpleDateFormat formatoEntrada = new SimpleDateFormat(FORMATO_ENTRADA);
        return formatoEntrada.parse(dataString);
    }

    public static String dataParaString(Date data) {
        SimpleDateFormat formatoSaida = new SimpleDateFormat(FORMATO_SAIDA);
        return formatoSaida.format(data);
    }
}
