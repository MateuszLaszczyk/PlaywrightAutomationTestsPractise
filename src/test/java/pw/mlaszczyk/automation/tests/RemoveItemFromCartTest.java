package pw.mlaszczyk.automation.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pw.mlaszczyk.automation.config.ConfigLoader;
import pw.mlaszczyk.automation.pages.pages.LoginPage;
import pw.mlaszczyk.automation.pages.pages.ProductPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class RemoveItemFromCartTest extends BaseTest {

    private static String username;
    private static String password;
    private static String baseUrl;
    private LoginPage loginPage;
    private ProductPage productPage;


    @BeforeAll
    public static void cfg() {
        username = ConfigLoader.get("saucedemo.username");
        password = ConfigLoader.get("saucedemo.password");
        baseUrl = ConfigLoader.get("saucedemo.baseUrl");
    }

    @BeforeEach
    public void setupPages() {
        loginPage = new LoginPage(page);
        productPage = new ProductPage(page);

    }

    @Test
    public void addAndRemoveItemFromCartTest() {
        // 1. Navigate to login page
        page.navigate(baseUrl);
        // 2. Login with provided credentials
        loginPage.login(username, password);
        // 3. Add Bolt Tshirt to cart
        productPage.addSauceLabsBoltTshirtToCart();
        // 4. Assert that Remove button is displayed at Bolt Tshirt window
        assertThat(productPage.boltTshirtRemoveButton()).isVisible();
        // 5. Navigate to cart
        productPage.navigateToCart();
        // 6. Assert that Remove button is displayed in cart
        assertThat(productPage.boltTshirtRemoveButton()).isVisible();
        // 7. Remove the item from cart
        productPage.removeSauceLabsBoltTshirtFromCart();
        // 8 Assert that cart badge is not displayed if no item is in cart
        assertThat(productPage.cartBadge()).isHidden();
    }
}






