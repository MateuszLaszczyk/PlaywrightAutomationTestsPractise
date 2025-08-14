package pw.mlaszczyk.automation.assertions;

import com.microsoft.playwright.Locator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public final class CartAssertions {
    private CartAssertions() {}

    public static void assertCartItemCount(Locator cartBadge, int expectedCount) {
        assertThat(cartBadge).hasText(String.valueOf(expectedCount));
    }

    public static void assertProductNameInCart(Locator productName, String expectedName) {
        assertThat(productName).hasText(expectedName);
    }
}