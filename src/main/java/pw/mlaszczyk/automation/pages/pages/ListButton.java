package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ListButton {
    private final Page page;

    public ListButton(Page page) {
        this.page = page;
    }

    public void expandList (){
        Locator expandList = page.locator(".rct-icon.rct-icon-expand-all");
        expandList.click();

        }
    }

