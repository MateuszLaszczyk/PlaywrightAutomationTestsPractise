package org.example;

import com.microsoft.playwright.Page;

public class TextBoxPage {
    private final Page page;

    public TextBoxPage(Page page) {
        this.page = page;
    }

    public void enterUserName(String name) {
        page.locator("#userName").fill(name);
    }

    public void enterEmail(String email) {
        page.locator("#userEmail").fill(email);
    }

    public void enterCurrentAddress(String address) {
        page.locator("#currentAddress").fill(address);
    }

    public void enterPermamentAddress(String address) {
        page.locator("#permanentAddress").fill(address);
    }

    public void clickSubmitButton() {
        page.locator("#submit").click();
    }

    public String getOutputText() {
        return page.locator("#output").textContent().trim();
    }
}
