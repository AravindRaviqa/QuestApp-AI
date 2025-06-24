package com.questapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.File;

public class CoursePage extends BasePage {
    private final By contentMenuLocator = By.xpath("//button[6]//span[1]");
    private final By coursesMenuLocator = By.xpath("//button[normalize-space()='Courses']");
    private final By addNewCourseButtonLocator = By.xpath("(//mat-icon[normalize-space()='add'])[1]");
    private final String dialogRoot = "//mat-dialog-container";
    private final By courseNameInputLocator = By.xpath(dialogRoot + "//input[@formcontrolname='name']");
    private final By courseDescriptionInputLocator = By.xpath(dialogRoot + "//textarea[@formcontrolname='description']");
    private final By courseTagInputLocator = By.xpath(dialogRoot + "//input[@formcontrolname='tag']");
    private final By subjectDropdownLocator = By.xpath(dialogRoot + "//p-dropdown[@formcontrolname='subject']//div[contains(@class,'p-dropdown')]");
    private final By imageUploadInputLocator = By.xpath(dialogRoot + "//input[@type='file']");
    private final By submitButtonLocator = By.xpath("//span[normalize-space()='Submit']");
    private final By searchInputLocator = By.xpath("//input[@formcontrolname='search_value']");
    private final By courseListLocator = By.xpath("//td[contains(text(), '%s')]");
    private final By editButtonLocator = By.xpath("//td[contains(text(), '%s')]/..//following-sibling::td//*[name()='svg'][1]");
    private final By snackBarLocator = By.xpath("//snack-bar-container");

    public CoursePage(WebDriver driver) {
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

    public void navigateToCourses() {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            // Wait for the courses menu to be clickable
            WebElement coursesMenu = wait.until(ExpectedConditions.elementToBeClickable(coursesMenuLocator));
            
            // Use JavaScript executor to click if regular click fails
            try {
                coursesMenu.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", coursesMenu);
            }
            
            // Wait a bit for the navigation to complete
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickAddNewCourse() {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            // Wait for the add button to be clickable
            WebElement addNewCourseButton = wait.until(ExpectedConditions.elementToBeClickable(addNewCourseButtonLocator));
            
            // Use JavaScript executor to click if regular click fails
            try {
                addNewCourseButton.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", addNewCourseButton);
            }
        } catch (Exception e) {
            // If click fails, try again after a short wait
            try {
                Thread.sleep(1000);
                WebElement addNewCourseButton = wait.until(ExpectedConditions.elementToBeClickable(addNewCourseButtonLocator));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", addNewCourseButton);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void enterCourseName(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(courseNameInputLocator));
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    public void enterCourseDescription(String description) {
        WebElement descriptionInput = wait.until(ExpectedConditions.visibilityOfElementLocated(courseDescriptionInputLocator));
        descriptionInput.clear();
        descriptionInput.sendKeys(description);
    }

    public void enterCourseTag(String tag) {
        WebElement tagInput = wait.until(ExpectedConditions.visibilityOfElementLocated(courseTagInputLocator));
        tagInput.clear();
        tagInput.sendKeys(tag);
    }

    public void selectSubject(String subjectName) {
        selectFromPrimeNgDropdown(subjectDropdownLocator, subjectName);
    }

    public void uploadCourseImage(String imagePath) {
        try {
            // Wait for the file input to be present
            WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(imageUploadInputLocator));
            
            // Get the absolute path of the image file
            File imageFile = new File(imagePath);
            String absolutePath = imageFile.getAbsolutePath();
            
            // Send the file path to the input element
            fileInput.sendKeys(absolutePath);
            
            // Wait for the image to be uploaded
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

    public void createNewCourse(String name, String description, String subjectName, String imagePath) {
        clickAddNewCourse();
        enterCourseName(name);
        enterCourseDescription(description);
        enterCourseTag(name); // Using the same value as name for tag
        selectSubject(subjectName);
        uploadCourseImage(imagePath);
        clickSubmit();
    }

    public void searchCourse(String courseName) {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputLocator));
            searchInput.clear();
            searchInput.sendKeys(courseName);
            
            // Wait for search results to update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean isCourseVisible(String courseName) {
        String xpathExpression = String.format(courseListLocator.toString().replace("By.xpath: ", ""), courseName);
        By locator = By.xpath(xpathExpression);
        try {
            WebElement courseElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return courseElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickEditCourse(String courseName) {
        try {
            // Wait for any snack bar to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(snackBarLocator));
            
            String xpathExpression = String.format(editButtonLocator.toString().replace("By.xpath: ", ""), courseName);
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
                String xpathExpression = String.format(editButtonLocator.toString().replace("By.xpath: ", ""), courseName);
                By locator = By.xpath(xpathExpression);
                WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
} 