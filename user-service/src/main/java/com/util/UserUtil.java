package com.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserUtil {

    private static DateTimeFormatter dateFormatter = DateTimeFormatter.BASIC_ISO_DATE;

    public static boolean isValid(String dateStr) {
        try {
            LocalDate passedDate = LocalDate.parse(dateStr, dateFormatter);
            return checkFromCurrentDate(passedDate);
        } catch (DateTimeParseException e) {
            System.out.println("Error in parsing the Date!!!");
            return false;
        }
    }

    public static boolean checkFromCurrentDate(LocalDate passedDate) {
        LocalDate today = LocalDate.now();
        return !today.isAfter(passedDate);
    }

}
