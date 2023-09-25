package com.ale.blog.handler.utils;

import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Format {
    public static String toLocalDate(Instant instant) {
        if(instant == null) return "";
        LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static String toHref(String content) {
        return Normalizer.normalize(content, Normalizer.Form.NFKD)
                .replaceAll("[\\u0300-\\u036f]", "")
                .trim()
                .toLowerCase()
                .replaceAll("đ", "d")
                .replaceAll("[^a-z0-9 -]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("^[^a-z]+", "");
    }
}
