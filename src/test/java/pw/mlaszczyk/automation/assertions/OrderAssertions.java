package pw.mlaszczyk.automation.assertions;

import com.microsoft.playwright.Locator;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import pw.mlaszczyk.automation.pages.pages.ProductPage;

public final class OrderAssertions {
    private OrderAssertions() {}

    /** Verify that the completion header is visible (locator-based). */
    public static void assertOrderIsCompleted(Locator completeHeader) {
        assertThat(completeHeader).isVisible();
    }

    /** Convenience overload (keeps old call sites working if needed). */
    public static void assertOrderIsCompleted(ProductPage page) {
        assertOrderIsCompleted(page.orderCompletedHeader());
    }
}
