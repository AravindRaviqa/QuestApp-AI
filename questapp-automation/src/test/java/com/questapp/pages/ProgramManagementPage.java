package com.questapp.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;

public class ProgramManagementPage extends BasePage {
    // Locators
    private final By institutionsMenuLocator = By.xpath("((//span[@class='mat-button-wrapper'])[2])");
    private final By programsMenuLocator = By.xpath("//button[normalize-space()='Programs']");
    private final By addNewProgramButtonLocator = By.xpath("(//mat-icon[normalize-space()='add'])[1]");
    private final By programNameInputLocator = By.xpath("//input[@class='p-inputtext p-component p-element ng-untouched ng-pristine ng-invalid']");
    private final By submitButtonLocator = By.xpath("//span[normalize-space()='Submit']");
    private final By searchInputLocator = By.xpath("//input[@id='mat-input-0']");
    private final By programListLocator = By.xpath("//td[contains(text(), '%s')]");
    private final By editButtonLocator = By.xpath("//td[contains(text(), '%s')]/..//following-sibling::td//*[name()='svg'][1]");
    private final By editProgramNameInputLocator = By.xpath("//input[contains(@class, 'p-inputtext') and contains(@class, 'p-component')]");

    public ProgramManagementPage(WebDriver driver) {
        super(driver);
    }

	public void navigateToInstitutions() {
        try {
            Thread.sleep(1000); // Wait for page to be fully loaded
            WebElement institutionsMenu = wait.until(ExpectedConditions.elementToBeClickable(institutionsMenuLocator));
            institutionsMenu.click();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
	//driver.findElement(institutionsMenuLocator).click();
	}

    public void navigateToPrograms() {
        WebElement programsMenu = wait.until(ExpectedConditions.elementToBeClickable(programsMenuLocator));
        programsMenu.click();
    }

    public void clickAddNewProgram() {
        WebElement addNewProgramButton = wait.until(ExpectedConditions.elementToBeClickable(addNewProgramButtonLocator));
        addNewProgramButton.click();
    }

    public void createNewProgram(String programName) {
        WebElement programNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(programNameInputLocator));
        programNameInput.sendKeys(programName);

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
        submitButton.click();
        
        // Wait for program creation success and page refresh
        try {
            Thread.sleep(2000); // Wait for backend processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void searchProgram(String programName) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
        searchInput.clear();
        searchInput.sendKeys(programName);
        
        try {
            Thread.sleep(1000); // Wait for search results to update
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isProgramVisible(String programName) {
        String xpathExpression = String.format(programListLocator.toString().replace("By.xpath: ", ""), programName);
        By locator = By.xpath(xpathExpression);
        try {
            // Wait for search results to be updated
            Thread.sleep(2000);
            // Wait for program element with increased timeout
            wait = new WebDriverWait(driver, Duration.ofSeconds(20)).pollingEvery(Duration.ofMillis(600));
            WebElement programElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String displayedProgramName = programElement.getText().trim();
            // Reset wait timeout to default
            wait = new WebDriverWait(driver, Duration.ofSeconds(20)).pollingEvery(Duration.ofMillis(600));
            return displayedProgramName.equals(programName);
        } catch (Exception e) {
            // Reset wait timeout to default
            wait = new WebDriverWait(driver, Duration.ofSeconds(20)).pollingEvery(Duration.ofMillis(600));
            return false;
        }
    }

    public void clickEditProgram(String programName) {
        String xpathExpression = String.format(editButtonLocator.toString().replace("By.xpath: ", ""), programName);
        By locator = By.xpath(xpathExpression);
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
        editButton.click();
    }

    public void updateProgramName(String newProgramName) {
        try {
            Thread.sleep(1000); // Wait for edit form to be fully loaded
            WebElement programNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(editProgramNameInputLocator));
            programNameInput.clear();
            Thread.sleep(500); // Wait after clearing
            programNameInput.sendKeys(newProgramName);

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
            submitButton.click();
            
            // Wait for program update success and page refresh
            Thread.sleep(2000); // Wait for backend processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}