package com.ale.blog.handler.utils;

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
}
