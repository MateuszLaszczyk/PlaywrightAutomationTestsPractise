package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Page;

public class DropDownPage {
    private final Page page;

    public DropDownPage(Page page) {
        this.page = page;
    }

    // Setters
    public void selectOptionOne() {
        page.selectOption("#dropdown", "1");
    }

    public void selectElementsPerPage() {
        page.selectOption("#elementsPerPageSelect", "50");
    }

    public void selectCountry() {
        page.selectOption("#country", "Poland");
    }

    // Getters
    public String getSelectedDropdownValue() {
        return page.locator("#dropdown").inputValue();
    }

    public String getSelectedElementsPerPageValue() {
        return page.locator("#elementsPerPageSelect").inputValue();
    }

    public String getSelectedCountryValue() {
        return page.locator("#country").inputValue();
    }
}
