package com.example.VirtualHall.utils;

public class PhoneUtil {

    public static String normalizePhone(String phone) {
        if (phone == null) return null;

        // Оставляем только цифры
        String digits = phone.replaceAll("[^0-9]", "");

        // Если начинается с 8 или 7, заменяем 8 на 7 для единообразия
        if (digits.length() == 11) {
            if (digits.startsWith("8")) {
                digits = "7" + digits.substring(1);
            }
        } else if (digits.length() == 10) {
            // Если ввели 10 цифр без кода страны, добавляем 7
            digits = "7" + digits;
        }

        return digits;
    }

    public static String formatPhone(String phone) {
        if (phone == null || phone.length() != 11) return phone;

        return String.format("+%s (%s) %s-%s-%s",
                phone.charAt(0),
                phone.substring(1, 4),
                phone.substring(4, 7),
                phone.substring(7, 9),
                phone.substring(9, 11)
        );
    }
}