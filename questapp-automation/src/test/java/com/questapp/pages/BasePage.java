package com.questapp.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected FluentWait<WebDriver> wait;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)).pollingEvery(Duration.ofMillis(600));
        PageFactory.initElements(driver, this);
    }
    
    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    protected void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    protected void click(WebElement element) {
        waitForElementToBeClickable(element);
        element.click();
    }
    
    protected void sendKeys(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }
    
    protected String getText(WebElement element) {
        waitForElementToBeVisible(element);
        return element.getText();
    }
    
    protected void clickUsingJavaScript(WebElement element) {
        try {
            if (element != null) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }
        } catch (Exception e) {
            System.err.println("Error clicking element using JavaScript: " + e.getMessage());
            throw e;
        }
    }
    
    protected void closeAnyOpenOverlays() {
        try {
            // Look for overlay backdrops
            List<WebElement> overlays = driver.findElements(By.xpath("//div[contains(@class, 'cdk-overlay-backdrop-showing')]"));
            if (!overlays.isEmpty()) {
                System.out.println("Found " + overlays.size() + " open overlays. Attempting to close...");
                
                // Try ESC key to close dialogs
                Actions actions = new Actions(driver);
                actions.sendKeys(Keys.ESCAPE).perform();
                Thread.sleep(1000);
                
                // Try clicking away from modal
                clickUsingJavaScript(driver.findElement(By.tagName("body")));
                Thread.sleep(1000);
                
                // Check if overlays are still present
                overlays = driver.findElements(By.xpath("//div[contains(@class, 'cdk-overlay-backdrop-showing')]"));
                if (!overlays.isEmpty()) {
                    System.out.println("Overlays still present. Trying to click overlay directly...");
                    clickUsingJavaScript(overlays.get(0));
                    Thread.sleep(1000);
                }
                
                // If overlays are STILL present, remove them with JS
                overlays = driver.findElements(By.xpath("//div[contains(@class, 'cdk-overlay-backdrop-showing')]"));
                if (!overlays.isEmpty()) {
                    System.out.println("Overlays still present after all attempts. Removing with JS...");
                    ((JavascriptExecutor) driver).executeScript(
                        "document.querySelectorAll('.cdk-overlay-backdrop-showing').forEach(e => e.remove());"
                    );
                    Thread.sleep(500);
                }
            }
        } catch (Exception e) {
            System.err.println("Error closing overlays: " + e.getMessage());
        }
    }
    
    // Shared PrimeNG dropdown helpers
    protected By dropdownOptionLocator(String optionText) {
        return By.xpath("//li[contains(@class,'p-dropdown-item') and contains(.,'" + optionText + "')]");
    }

    protected void selectFromPrimeNgDropdown(By dropdownLocator, String optionText) {
        closeAnyOpenOverlays();
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        dropdown.click();
        By panelLocator = By.xpath("//div[contains(@class,'p-dropdown-panel') and not(contains(@style,'display: none'))]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(panelLocator));
        // Type into the filter input if present
        try {
            WebElement filterInput = driver.findElement(By.xpath("//input[contains(@class,'p-dropdown-filter')]"));
            filterInput.clear();
            filterInput.sendKeys(optionText);
            Thread.sleep(500); // Wait for filtering
        } catch (Exception e) {
            // No filter input present, continue
        }
        // Case-insensitive, trimmed match for option
        By optionLocator = By.xpath("//li[contains(@class,'p-dropdown-item') and translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = '" + optionText.trim().toLowerCase() + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
    }
}