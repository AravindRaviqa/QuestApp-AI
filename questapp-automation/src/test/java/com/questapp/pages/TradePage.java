package com.questapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TradePage extends BasePage {
    private final String dialogRoot = "//mat-dialog-container";
    private final By tradesMenuLocator = By.xpath("//button[normalize-space()='Trades']");
    private final By addNewTradeButtonLocator = By.xpath("(//mat-icon[normalize-space()='add'])[1]");
    private final By tradeNameInputLocator = By.xpath(dialogRoot + "//input[@formcontrolname='name']");
    private final By centreTypeDropdownLocator = By.xpath(dialogRoot + "//label[contains(text(),'Centre Type')]/parent::span/p-dropdown//div[contains(@class,'p-dropdown')]");
    private final By submitButtonLocator = By.xpath("//span[normalize-space()='Submit']");
    // Locator for the search input (update as needed)
    private final By searchInputLocator = By.xpath("//input[@formcontrolname='name']");
    // Locator for the edit icon against a trade row (update as needed)
    private By editIconLocatorForTrade(String tradeName) {
        return By.xpath("//tbody/tr[1]/td[7]/button[1]");
    }

    public TradePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToTrades() {
        WebElement tradesMenu = wait.until(ExpectedConditions.elementToBeClickable(tradesMenuLocator));
        tradesMenu.click();
    }

    public void clickAddNewTrade() {
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        WebElement addNewTradeButton = wait.until(ExpectedConditions.elementToBeClickable(addNewTradeButtonLocator));
        addNewTradeButton.click();
        // Wait for the dialog to appear
        By dialogLocator = By.xpath("//mat-dialog-container");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialogLocator));
        // Debug: Take screenshot after dialog appears
        try {
            java.io.File scrFile = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            java.nio.file.Files.copy(scrFile.toPath(), java.nio.file.Paths.get("trade_dialog_debug.png"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Screenshot error: " + e.getMessage());
        }
    }

    public void enterTradeName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(tradeNameInputLocator));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void selectCentreType(String centreType) {
        selectFromPrimeNgDropdown(centreTypeDropdownLocator, centreType);
    }

    public void clickSubmit() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
        submitButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void createNewTrade(String tradeName, String centreType) {
        clickAddNewTrade();
        enterTradeName(tradeName);
        selectCentreType(centreType);
        clickSubmit();
    }

    public void searchTrade(String tradeName) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
        searchInput.clear();
        searchInput.sendKeys(tradeName);
        // Optionally, add a small wait for search results to update
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public void clickEditTrade(String tradeName) {
        WebElement editIcon = wait.until(ExpectedConditions.elementToBeClickable(editIconLocatorForTrade(tradeName)));
        editIcon.click();
        // Wait for the dialog to appear
        By dialogLocator = By.xpath("//mat-dialog-container");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialogLocator));
    }

    public void editTrade(String newTradeName) {
        enterTradeName(newTradeName);
        clickSubmit();
    }
} 