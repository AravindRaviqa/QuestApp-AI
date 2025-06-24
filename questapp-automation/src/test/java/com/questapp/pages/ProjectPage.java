package com.questapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProjectPage extends BasePage {
    private final String dialogRoot = "//mat-dialog-container";
    private final By projectsMenuLocator = By.xpath("//button[normalize-space()='Projects']");
    private final By addNewProjectButtonLocator = By.xpath("(//mat-icon[normalize-space()='add'])[1]");
    private final By projectNameInputLocator = By.xpath(dialogRoot + "//input[@formcontrolname='name']");
    private final By programDropdownLocator = By.xpath(dialogRoot + "//label[contains(text(),'Program')]/parent::span/p-dropdown//div[contains(@class,'p-dropdown')]");
    private final By submitButtonLocator = By.xpath("//span[normalize-space()='Submit']");
    // Locator for the search input (update as needed)
    private final By searchInputLocator = By.xpath("//input[@formcontrolname='search_value']");
    // Locator for the edit icon against a project row (update as needed)
    private By editIconLocatorForProject(String projectName) {
        return By.xpath("//tbody/tr[1]/td[5]/button[3]");
    }

    public ProjectPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToProjects() {
        WebElement projectsMenu = wait.until(ExpectedConditions.elementToBeClickable(projectsMenuLocator));
        projectsMenu.click();
    }

    public void clickAddNewProject() {
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        WebElement addNewProjectButton = wait.until(ExpectedConditions.elementToBeClickable(addNewProjectButtonLocator));
        addNewProjectButton.click();
        // Wait for the dialog to appear
        By dialogLocator = By.xpath("//mat-dialog-container");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialogLocator));
        // Debug: Take screenshot after dialog appears
        try {
            java.io.File scrFile = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            java.nio.file.Files.copy(scrFile.toPath(), java.nio.file.Paths.get("project_dialog_debug.png"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Screenshot error: " + e.getMessage());
        }
    }

    public void enterProjectName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(projectNameInputLocator));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void selectProgram(String programName) {
        selectFromPrimeNgDropdown(programDropdownLocator, programName);
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

    public void createNewProject(String projectName, String programName) {
        clickAddNewProject();
        selectProgram(programName);
        enterProjectName(projectName);       
        clickSubmit();
    }

    public void searchProject(String projectName) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
        searchInput.clear();
        searchInput.sendKeys(projectName);
        // Optionally, add a small wait for search results to update
        try { Thread.sleep(1000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

    public void clickEditProject(String projectName) {
        WebElement editIcon = wait.until(ExpectedConditions.elementToBeClickable(editIconLocatorForProject(projectName)));
        editIcon.click();
        // Wait for the dialog to appear
        By dialogLocator = By.xpath("//mat-dialog-container");
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialogLocator));
    }

    public void editProject(String newProjectName, String newProgramName) {
    	selectProgram(newProgramName);
    	enterProjectName(newProjectName);        
        clickSubmit();
    }
} 