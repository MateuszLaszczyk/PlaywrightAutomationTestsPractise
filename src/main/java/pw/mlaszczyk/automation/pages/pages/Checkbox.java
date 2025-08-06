package pw.mlaszczyk.automation.pages.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Checkbox {
    private final Page page;

    public Checkbox(Page page) {
        this.page = page;
    }

    public void selectHomeCheckbox() {
        Locator homeCheckbox = page.locator("svg.rct-icon-expand-close path[d^='M10 6L8.59']");
        homeCheckbox.click();
    }
    public void selectDownloadCheckbox(){
        Locator downloadCheckbox = page.locator("label[for='tree-node-downloads'] span[class='rct-title']");
        downloadCheckbox.click();
    }

    public void selectFilesToDownload(){
        Locator wordFileCheckbox = page.locator("label[for='tree-node-wordFile'] span[class='rct-title']");
        Locator excelFile = page.locator("label[for='tree-node-excelFile'] span[class='rct-title']");
        wordFileCheckbox.click();
        excelFile.click();
    }

}