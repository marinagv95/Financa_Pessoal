package org.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public static Date stringParaData(String dataString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.parse(dataString);
    }

    public static String dataParaString(Date data) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        return format.format(data);
    }

}
