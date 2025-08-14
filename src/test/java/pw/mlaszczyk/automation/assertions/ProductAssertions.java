package pw.mlaszczyk.automation.assertions;

import com.microsoft.playwright.Locator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import pw.mlaszczyk.automation.pages.pages.ProductPage;

public final class ProductAssertions {
    private ProductAssertions() {}

    /** Verify prices (from a list locator) are sorted in descending order. */
    public static void assertPricesSortedDescending(Locator pricesLocator) {
        List<String> priceTexts = pricesLocator.allInnerTexts();
        List<Double> prices = priceTexts.stream()
                .map(t -> Double.parseDouble(t.replace("$", "").trim()))
                .collect(Collectors.toList());

        List<Double> sorted = new ArrayList<>(prices);
        sorted.sort(Comparator.reverseOrder());

        if (!sorted.equals(prices)) {
            throw new AssertionError("Prices are not sorted descending: " + prices);
        }
    }

    /** Convenience overload (keeps old call sites working if needed). */
    public static void assertPricesSortedDescending(ProductPage page) {
        assertPricesSortedDescending(page.allPrices());
    }
}
