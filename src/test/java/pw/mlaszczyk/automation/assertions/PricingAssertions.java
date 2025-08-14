package pw.mlaszczyk.automation.assertions;

import com.microsoft.playwright.Locator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class PricingAssertions {
    private PricingAssertions() {}

    public static void assertTaxIsEightPercent(Locator itemTotalLabel, Locator taxLabel) {
        BigDecimal itemTotal = extractAmount(itemTotalLabel);
        BigDecimal expectedTax = itemTotal.multiply(new BigDecimal("0.08"))
                .setScale(2, RoundingMode.HALF_UP);

        String expectedText = String.format(Locale.US, "Tax: $%.2f", expectedTax);
        assertThat(taxLabel).hasText(expectedText);
    }

    public static void assertTotalPriceIsCorrect(Locator itemTotalLabel, Locator taxLabel, Locator totalLabel) {
        BigDecimal itemTotal = extractAmount(itemTotalLabel);
        BigDecimal tax       = extractAmount(taxLabel);

        BigDecimal expectedTotal = itemTotal.add(tax).setScale(2, RoundingMode.HALF_UP);
        String expectedText = String.format(Locale.US, "Total: $%.2f", expectedTotal);
        assertThat(totalLabel).hasText(expectedText);
    }

    private static BigDecimal extractAmount(Locator label) {
        String text = label.innerText().replace('\u00A0',' ').trim();
        String number = text.replaceAll(".*?\\$\\s*([0-9]+(?:\\.[0-9]{1,2})?).*", "$1");
        return new BigDecimal(number).setScale(2, RoundingMode.HALF_UP);
    }
}
