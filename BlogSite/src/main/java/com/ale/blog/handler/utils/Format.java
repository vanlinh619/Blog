package com.ale.blog.handler.utils;

import java.text.Normalizer;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Format {
    public static String toLocalDate(Instant instant) {
        if(instant == null) return "";
        LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static LocalDateTime toLocalDateTime(Instant instant) {
        if(instant == null) return null;
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return localDateTime;
    }

    public static String toHref(String content) {
        return Normalizer.normalize(content, Normalizer.Form.NFKD)
                .replaceAll("[\\u0300-\\u036f]", "")
                .trim()
                .toLowerCase()
                .replaceAll("đ", "d")
                .replaceAll("[^a-z0-9 -]", "")
                .replaceAll("\\s+", "-");
//                .replaceAll("^[^a-z]+", "");
    }
}
