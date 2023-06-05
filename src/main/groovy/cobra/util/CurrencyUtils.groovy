package cobra.util

import java.text.NumberFormat

class CurrencyUtils {

    private static Locale locale = new Locale("pt", "BR")

    public static String format(BigDecimal value) {
        NumberFormat currencyFormatter = NumberFormat.getInstance(locale)
        currencyFormatter.setMinimumFractionDigits(2)

        return currencyFormatter.format(value)
    }
}
