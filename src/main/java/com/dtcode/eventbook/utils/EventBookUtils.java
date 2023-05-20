package com.dtcode.eventbook.utils;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EventBookUtils {

    public final static String DefaultDateFormat = "yyyy-MM-dd";

    public static boolean isValidYear(String yearString) {
        try {
            Year.parse(yearString);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DefaultDateFormat);
            LocalDate.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate getDate(String dateString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DefaultDateFormat);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
