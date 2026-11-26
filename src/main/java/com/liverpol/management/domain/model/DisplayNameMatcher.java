package com.liverpol.management.domain.model;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Pattern;

public final class DisplayNameMatcher {

    private static final Pattern DIACRITICS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    private static final Pattern PUNCTUATION_AND_COMMAS =
            Pattern.compile("[,.;:!?()\\[\\]{}\"'`´’\\-]+");
    private static final Pattern MULTIPLE_SPACES = Pattern.compile("\\s+");
    private static final Locale SPANISH_MX = Locale.of("es", "MX");

    private DisplayNameMatcher() {
    }

    public static String normalize(String raw) {
        if (raw == null || raw.isBlank()) {
            return "";
        }
        String decomposed = Normalizer.normalize(raw.strip(), Normalizer.Form.NFD);
        String withoutAccents = DIACRITICS.matcher(decomposed).replaceAll("");
        String withoutPunctuation = PUNCTUATION_AND_COMMAS.matcher(withoutAccents).replaceAll(" ");
        return MULTIPLE_SPACES
                .matcher(withoutPunctuation)
                .replaceAll(" ")
                .trim()
                .toUpperCase(SPANISH_MX);
    }

    public static boolean matchesTypeAhead(String displayName, String rawQuery) {
        if (rawQuery == null || rawQuery.isBlank()) {
            return true;
        }
        if (displayName == null || displayName.isBlank()) {
            return false;
        }

        String normalizedTarget = normalize(displayName);
        String normalizedQuery = normalize(rawQuery);

        if (normalizedQuery.isEmpty()) {
            return true;
        }

        // 1. Coincidencia directa por subcadena (type ahead estándar)
        if (normalizedTarget.contains(normalizedQuery)) {
            return true;
        }

        // 2. Tolerancia a errores mínimos de ortografía por token o ventana de palabras
        int maxTypos = allowedTypos(normalizedQuery.length());
        if (maxTypos == 0) {
            return false;
        }

        String[] targetTokens = normalizedTarget.split(" ");
        String[] queryTokens = normalizedQuery.split(" ");

        // Si el filtro es una sola palabra, comparar contra cada palabra del displayName
        if (queryTokens.length == 1) {
            String q = queryTokens[0];
            for (String token : targetTokens) {
                String prefixOrToken = token.length() > q.length() ? token.substring(0, q.length()) : token;
                if (levenshteinDistance(token, q) <= maxTypos
                        || levenshteinDistance(prefixOrToken, q) <= maxTypos) {
                    return true;
                }
            }
            return false;
        }

        // Si el filtro tiene varias palabras, evaluar ventanas contiguas de tokens
        int windowSize = queryTokens.length;
        for (int i = 0; i <= targetTokens.length - windowSize; i++) {
            String candidateWindow = String.join(" ",
                    Arrays.copyOfRange(targetTokens, i, i + windowSize));
            if (levenshteinDistance(candidateWindow, normalizedQuery) <= maxTypos) {
                return true;
            }
        }

        return false;
    }

    private static int allowedTypos(int queryLength) {
        if (queryLength <= 3) {
            return 0; // Evita falsos positivos en prefijos muy cortos de type ahead
        }
        if (queryLength <= 6) {
            return 1;
        }
        return 2;
    }

    public static int levenshteinDistance(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 0; i <= a.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= b.length(); j++) {
            dp[0][j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[a.length()][b.length()];
    }
}