package pw.mlaszczyk.automation.helpers;

import com.microsoft.playwright.Locator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/** Assertions related to product lists. */
public final class ProductAssertions {
    private ProductAssertions() { /* utility class */ }

    /**
     * Verifies that the prices from the given locator (e.g. ".inventory_item_price") are sorted high→low.
     */
    public static void assertPricesSortedDescending(Locator pricesLocator) {
        // Read all prices as text, strip "$", parse with US decimal dot
        List<String> priceTexts = pricesLocator.allInnerTexts();
        List<BigDecimal> prices = priceTexts.stream()
                .map(t -> new BigDecimal(t.replace("$", "").trim()))
                .collect(Collectors.toList());

        // Expected order: descending
        List<BigDecimal> expected = new ArrayList<>(prices);
        expected.sort(Comparator.reverseOrder());

        if (!expected.equals(prices)) {
            throw new AssertionError(
                    "Prices are not sorted high→low. Actual: " + prices + ", Expected: " + expected
            );
        }
    }

    public static void assertTaxIsEightPercent(Locator itemTotalLabel, Locator taxLabel) {
        BigDecimal itemTotal = readAmount(itemTotalLabel, "Item total: $");
        BigDecimal expectedTax = itemTotal.multiply(new BigDecimal("0.08"))
                .setScale(2, RoundingMode.HALF_UP);
        assertThat(taxLabel).hasText(String.format(Locale.US, "Tax: $%.2f", expectedTax));
    }

    public static void assertTotalPriceIsCorrect(Locator itemTotalLabel, Locator taxLabel, Locator totalLabel) {
        BigDecimal itemTotal = readAmount(itemTotalLabel, "Item total: $");
        BigDecimal tax = readAmount(taxLabel, "Tax: $");
        BigDecimal expected = itemTotal.add(tax).setScale(2, RoundingMode.HALF_UP);
        assertThat(totalLabel).hasText(String.format(Locale.US, "Total: $%.2f", expected));
    }

    private static BigDecimal readAmount(Locator label, String prefix) {
        String raw = label.innerText()
                .replace('\u00A0', ' ')
                .replace(prefix, "")
                .trim();
        return new BigDecimal(raw);
    }
}



