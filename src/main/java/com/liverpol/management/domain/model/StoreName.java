package com.liverpol.management.domain.model;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public record StoreName(String value) {

    private static final Pattern MULTIPLE_SPACES = Pattern.compile("\\s+");
    private static final Pattern DIACRITICS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    private static final Locale SPANISH_MX = Locale.of("es", "MX");

    public StoreName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("storeName no puede estar vacío");
        }
        value = normalize(value);
    }

    public static StoreName of(String rawStoreName) {
        return new StoreName(rawStoreName);
    }

    public static String normalize(String raw) {
        if (raw == null) {
            return "";
        }
        String withoutAccents = Normalizer.normalize(raw.strip(), Normalizer.Form.NFD);
        String strippedDiacritics = DIACRITICS.matcher(withoutAccents).replaceAll("");
        return MULTIPLE_SPACES
                .matcher(strippedDiacritics)
                .replaceAll(" ")
                .toUpperCase(SPANISH_MX);
    }

    /**
     * Devuelve true si el nombre normalizado de la tienda contiene
     * la subcadena buscada (también normalizada).
     */
    public boolean containsIgnoreCaseAndSpaces(String rawQuery) {
        if (rawQuery == null || rawQuery.isBlank()) {
            return true;
        }
        String normalizedQuery = normalize(rawQuery);
        return this.value.contains(normalizedQuery);
    }
}