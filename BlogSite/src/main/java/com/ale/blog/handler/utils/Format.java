package com.ale.blog.handler.utils;

import com.ale.blog.entity.User;
import jakarta.annotation.Nonnull;

import java.text.Normalizer;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Format {
    public static String toUsername(@Nonnull List<User> actors) {
        return actors.size() < 3
                ? actors.get(0).getFullName()
                : actors.get(0).getFullName() + ", " + actors.get(1).getFullName() + " và " + (actors.size() - 2) + " người khác.";
    }

    public static String toNumber(Long number) {
        return number >= 1000000000
                ? String.format("%.1fB", 1.0 * number / 1000000000)
                : number >= 1000000
                ? String.format("%.1fM", 1.0 * number / 1000000)
                : number >= 1000
                ? String.format("%.1fK", 1.0 * number / 1000)
                : String.valueOf(number);
    }

    public static String toTimeDifference(Instant instant) {
        LocalDate atTime = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        LocalDate now = LocalDate.now();

        if (atTime.isEqual(now)) {
            Duration duration = Duration.between(instant, Instant.now());
            long hour = duration.getSeconds() / 3600;
            long minute = duration.getSeconds() / 60;
            if (hour > 0) {
                return hour + " giờ trước";
            }
            if (minute > 0) {
                return minute + " phút trước";
            }
            return "mới bình luận";
        }
        if (atTime.getYear() == now.getYear()) {
            return atTime.format(DateTimeFormatter.ofPattern("dd-MM"));
        }
        return atTime.format(DateTimeFormatter.ofPattern("dd-MM-yy"));
    }

    public static String toLocalDate(Instant instant) {
        if (instant == null) return "";
        LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.systemDefault());
        return localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public static LocalDateTime toLocalDateTime(Instant instant) {
        if (instant == null) return null;
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
                .replaceAll("\\s+", "-")
                .replaceAll("^[^a-z0-9]+", "")
                .replaceAll("[^a-z0-9]+$", "");
    }

    public static String toParamUrl(Set<Map.Entry<String, String>> entries, String key, String value) {
        return entries.stream()
                .map(entry -> {
                    if (entry.getKey().equals(key)) {
                        return entry.getKey() + "=" + value;
                    }
                    return entry.getKey() + "=" + entry.getValue();
                })
                .reduce((s, s2) -> s + "&" + s2)
                .orElse("?");
    }
}
