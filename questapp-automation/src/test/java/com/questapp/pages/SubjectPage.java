package com.questapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;

public class SubjectPage extends BasePage {
    private final By contentMenuLocator = By.xpath("//button[6]//span[1]");
    private final By subjectsMenuLocator = By.xpath("//button[normalize-space()='Subjects']");
    private final By addNewSubjectButtonLocator = By.xpath("(//mat-icon[normalize-space()='add'])[1]");
    private final String dialogRoot = "//mat-dialog-container";
    private final By subjectNameInputLocator = By.xpath(dialogRoot + "//input[@formcontrolname='name']");
    private final By subjectDescriptionInputLocator = By.xpath(dialogRoot + "//textarea[@formcontrolname='description']");
    private final By subjectTagInputLocator = By.xpath(dialogRoot + "//input[@formcontrolname='tag']");
    private final By imageUploadInputLocator = By.xpath(dialogRoot + "//input[@type='file']");
    private final By submitButtonLocator = By.xpath("//span[normalize-space()='Submit']");
    private final By searchInputLocator = By.xpath("//input[@formcontrolname='search_value']");
    private final By subjectListLocator = By.xpath("//td[contains(text(), '%s')]");
    private final By editButtonLocator = By.xpath("//td[contains(text(), '%s')]/..//following-sibling::td//*[name()='svg'][1]");
    private final By snackBarLocator = By.xpath("//snack-bar-container");

    public SubjectPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToContent() {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            // Wait for the content menu to be clickable
            WebElement contentMenu = wait.until(ExpectedConditions.elementToBeClickable(contentMenuLocator));
            
            // Use JavaScript executor to click if regular click fails
            try {
                contentMenu.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", contentMenu);
            }
            
            // Wait a bit for the navigation to complete
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void navigateToSubjects() {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            // Wait for the subjects menu to be clickable
            WebElement subjectsMenu = wait.until(ExpectedConditions.elementToBeClickable(subjectsMenuLocator));
            
            // Use JavaScript executor to click if regular click fails
            try {
                subjectsMenu.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", subjectsMenu);
            }
            
            // Wait a bit for the navigation to complete
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickAddNewSubject() {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            // Wait for the add button to be clickable
            WebElement addNewSubjectButton = wait.until(ExpectedConditions.elementToBeClickable(addNewSubjectButtonLocator));
            
            // Use JavaScript executor to click if regular click fails
            try {
                addNewSubjectButton.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", addNewSubjectButton);
            }
        } catch (Exception e) {
            // If click fails, try again after a short wait
            try {
                Thread.sleep(1000);
                WebElement addNewSubjectButton = wait.until(ExpectedConditions.elementToBeClickable(addNewSubjectButtonLocator));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", addNewSubjectButton);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void enterSubjectName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(subjectNameInputLocator));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterSubjectDescription(String description) {
        WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(subjectDescriptionInputLocator));
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
    }

    public void enterSubjectTag(String tag) {
        WebElement tagInput = wait.until(ExpectedConditions.visibilityOfElementLocated(subjectTagInputLocator));
        tagInput.clear();
        tagInput.sendKeys(tag);
    }

    public void uploadSubjectImage(String imagePath) {
        try {
            // Wait for the file input to be present
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(imageUploadInputLocator));
            
            // Get the absolute path of the image file
            File imageFile = new File(imagePath);
            String absolutePath = imageFile.getAbsolutePath();
            
            // Send the file path to the input element
            fileInput.sendKeys(absolutePath);
            
            // Wait for the image to be uploaded (you might need to adjust this based on your application)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickSubmit() {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            // Wait for the submit button to be clickable
            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
            
            // Use JavaScript executor to click if regular click fails
            try {
                submitButton.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
            }
            
            // Wait for the snack bar to appear and then disappear
            wait.until(ExpectedConditions.visibilityOfElementLocated(snackBarLocator));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
        } catch (Exception e) {
            // If click fails, try again after a short wait
            try {
                Thread.sleep(1000);
                WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submitButtonLocator));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void createNewSubject(String name, String description, String imagePath) {
        clickAddNewSubject();
        enterSubjectName(name);
        enterSubjectDescription(description);
        enterSubjectTag(name);
        uploadSubjectImage(imagePath);
        clickSubmit();
    }

    public void searchSubject(String subjectName) {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
            searchInput.clear();
            searchInput.sendKeys(subjectName);
            
            // Wait for search results to update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isSubjectVisible(String subjectName) {
        String xpathExpression = String.format(subjectListLocator.toString().replace("By.xpath: ", ""), subjectName);
        By locator = By.xpath(xpathExpression);
        try {
            WebElement subjectElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return subjectElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickEditSubject(String subjectName) {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            String xpathExpression = String.format(editButtonLocator.toString().replace("By.xpath: ", ""), subjectName);
            By locator = By.xpath(xpathExpression);
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
            
            // Use JavaScript executor to click if regular click fails
            try {
                editButton.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
            }
        } catch (Exception e) {
            // If click fails, try again after a short wait
            try {
                Thread.sleep(1000);
                String xpathExpression = String.format(editButtonLocator.toString().replace("By.xpath: ", ""), subjectName);
                By locator = By.xpath(xpathExpression);
                WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
} 