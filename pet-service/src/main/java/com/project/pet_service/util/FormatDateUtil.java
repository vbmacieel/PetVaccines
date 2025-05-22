package com.project.pet_service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class FormatDateUtil {

    private FormatDateUtil() {
        throw new UnsupportedOperationException("This is an utility class and cannot be instantiated!");
    }

    public static LocalDate format(String date) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            return LocalDate.parse(date, pattern);
        } catch (DateTimeParseException exception) {
            System.out.println("Error to convert to LocalDate: " + exception.getMessage());
            return null;
        }
    }
}
