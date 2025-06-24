package com.questapp.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;

public class LessonPage {
    private WebDriver driver;
    private FluentWait<WebDriver> wait;

    @FindBy(xpath = "//span[contains(text(),'Content')]")
    private WebElement contentMenu;

    @FindBy(xpath = "//button[normalize-space()='Lessons']")
    private WebElement lessonsMenu;

    @FindBy(xpath = "//div[@class='content-title-row']//button[1]")
    private WebElement addNewLessonButton;

    // Subject dropdown
    @FindBy(xpath = "//p-dropdown[@formcontrolname='subject']//div[contains(@class,'p-dropdown')]")
    private WebElement subjectDropdown;
    
    @FindBy(xpath = "//input[@placeholder='Search']")
    private WebElement subjectSearchInput;

    // Lesson form fields
    @FindBy(xpath = "//input[@formcontrolname='name']")
    private WebElement lessonNameInput;

    @FindBy(xpath = "//input[@formcontrolname='category']")
    private WebElement lessonCategoryInput;

    @FindBy(xpath = "//p-dropdown[@formcontrolname='type']//div[contains(@class,'p-dropdown')]")
    private WebElement lessonTypeDropdown;

    // Access checkboxes
    @FindBy(xpath = "//input[@formcontrolname='studentAccess']")
    private WebElement studentAccessCheckbox;

    @FindBy(xpath = "//input[@formcontrolname='facilitatorAccess']")
    private WebElement facilitatorAccessCheckbox;

    @FindBy(xpath = "//input[@formcontrolname='masterTrainerAccess']")
    private WebElement masterTrainerAccessCheckbox;

    @FindBy(xpath = "//input[@formcontrolname='webAccess']")
    private WebElement webAccessCheckbox;

    @FindBy(xpath = "//input[@formcontrolname='mobileAccess']")
    private WebElement mobileAccessCheckbox;

    @FindBy(xpath = "//button[contains(text(),'Submit')]")
    private WebElement submitButton;

    public LessonPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20)).pollingEvery(Duration.ofMillis(600));
        PageFactory.initElements(driver, this);
    }

    private void waitForSnackBarToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                org.openqa.selenium.By.className("mat-snack-bar-container")));
        } catch (Exception e) {
            // If no snack bar is present, continue
        }
    }

    public void navigateToContent() {
        try {
            waitForSnackBarToDisappear();
            WebElement contentMenuElement = wait.until(ExpectedConditions.elementToBeClickable(contentMenu));
            try {
                contentMenuElement.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", contentMenuElement);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void navigateToLessons() {
        try {
            waitForSnackBarToDisappear();
            WebElement lessonsMenuElement = wait.until(ExpectedConditions.elementToBeClickable(lessonsMenu));
            try {
                lessonsMenuElement.click();
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", lessonsMenuElement);
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickAddNewLesson() {
        waitForSnackBarToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(addNewLessonButton)).click();
    }

    public void selectSubject(String subjectName) {
        try {
            // Click the subject dropdown
            wait.until(ExpectedConditions.elementToBeClickable(subjectDropdown)).click();
            Thread.sleep(500);

            // Enter subject name in search
            wait.until(ExpectedConditions.visibilityOf(subjectSearchInput)).sendKeys(subjectName);
            Thread.sleep(500);

            // Click the matching option
            String optionXPath = String.format("//p-dropdownitem[contains(.,'%s')]", subjectName);
            wait.until(ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath(optionXPath))).click();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void enterLessonName(String name) {
        wait.until(ExpectedConditions.visibilityOf(lessonNameInput)).sendKeys(name);
    }

    public void enterLessonCategory(String category) {
        wait.until(ExpectedConditions.visibilityOf(lessonCategoryInput)).sendKeys(category);
    }

    public void selectLessonType(String type) {
        try {
            // Click the lesson type dropdown
            wait.until(ExpectedConditions.elementToBeClickable(lessonTypeDropdown)).click();
            Thread.sleep(500);

            // Click the matching option
            String optionXPath = String.format("//p-dropdownitem[contains(.,'%s')]", type);
            wait.until(ExpectedConditions.elementToBeClickable(
                org.openqa.selenium.By.xpath(optionXPath))).click();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void setStudentAccess(boolean enabled) {
        setCheckbox(studentAccessCheckbox, enabled);
    }

    public void setFacilitatorAccess(boolean enabled) {
        setCheckbox(facilitatorAccessCheckbox, enabled);
    }

    public void setMasterTrainerAccess(boolean enabled) {
        setCheckbox(masterTrainerAccessCheckbox, enabled);
    }

    public void setWebAccess(boolean enabled) {
        setCheckbox(webAccessCheckbox, enabled);
    }

    public void setMobileAccess(boolean enabled) {
        setCheckbox(mobileAccessCheckbox, enabled);
    }

    private void setCheckbox(WebElement checkbox, boolean enabled) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(checkbox));
        if (element.isSelected() != enabled) {
            element.click();
        }
    }

    public void clickSubmit() {
        waitForSnackBarToDisappear();
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public void createNewLesson(String name, String category, String type, String subjectName, 
                              boolean studentAccess, boolean facilitatorAccess, 
                              boolean masterTrainerAccess, boolean webAccess, 
                              boolean mobileAccess) {
        clickAddNewLesson();
        selectSubject(subjectName);
        enterLessonName(name);
        enterLessonCategory(category);
        selectLessonType(type);
        setStudentAccess(studentAccess);
        setFacilitatorAccess(facilitatorAccess);
        setMasterTrainerAccess(masterTrainerAccess);
        setWebAccess(webAccess);
        setMobileAccess(mobileAccess);
        clickSubmit();
    }
} 