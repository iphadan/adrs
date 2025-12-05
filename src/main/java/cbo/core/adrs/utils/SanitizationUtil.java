package cbo.core.adrs.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public final class SanitizationUtil {

    private SanitizationUtil() {}

    /**
     * Clean input to prevent XSS. Uses a relaxed safelist that allows basic formatting
     * but removes scripts/styles. Use Safelist.none() for stricter removal.
     */
    public static String cleanHtml(String input) {
        if (input == null) return null;
        return Jsoup.clean(input, Safelist.basic());
    }

    /**
     * Clean plain text (strip tags)
     */
    public static String cleanText(String input) {
        if (input == null) return null;
        return Jsoup.clean(input, Safelist.none()).trim();
    }
}
