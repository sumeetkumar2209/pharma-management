package com.reify.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertorUtils {

    public static String eprocToDDMMYYYYY(long eprocTime) {
        Date date = new Date(eprocTime * 1000);
        String str = new SimpleDateFormat("dd/MM/yyyy").format(date);
        return str;
    }

}
