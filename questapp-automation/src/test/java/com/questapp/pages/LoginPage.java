package com.questapp.pages;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {

    @FindBy(css = ".dashboard-header")
    private WebElement dashboardHeader;
    
    @FindBy(css = ".error-message")
    private WebElement errorMessage;
    
    // Username input using the formcontrolname attribute
    @FindBy(xpath = "//input[@formcontrolname='username']")
    private WebElement usernameInput;
    
    // Password input using the p-password component
    @FindBy(xpath = "//p-password//input[@type='password']")
    private WebElement passwordInput;
    
    // Updated login button using the current class structure
    @FindBy(xpath = "//button[contains(@class, 'mat-flat-button') and contains(@class, 'mat-primary')]")
    private WebElement loginButton;
    
    // Locator for the welcome message on the home page after login.
    @FindBy(xpath = "//*[contains(text(),'Welcome to Quest Admin App')]")
    private WebElement welcomeMessage;
    
    // Check if we're on the login page by looking for login form elements
    @FindBy(xpath = "//form[contains(@class,'ng-untouched')]")
    private WebElement loginForm;
    
    // Better indicators for successful login - look for navigation elements
    @FindBy(xpath = "//*[contains(text(),'Dashboard') or contains(text(),'Institutions') or contains(text(),'Programs') or contains(text(),'Centres')]")
    private WebElement navigationElement;
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    public void navigateToLoginPage(String baseUrl) {
        driver.get(baseUrl);
    }
    
    /**
     * Check if the user is already logged in by looking for navigation elements
     * that would only be present after successful login
     */
    public boolean isAlreadyLoggedIn() {
        try {
            // First check if we're still on the login page
            if (isOnLoginPage()) {
                return false;
            }
            
            // Then check for navigation elements that indicate successful login
            FluentWait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(600));
            wait.until(ExpectedConditions.visibilityOf(navigationElement));
            return navigationElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if we're on the login page by looking for login form elements
     */
    public boolean isOnLoginPage() {
        try {
            FluentWait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10)).pollingEvery(Duration.ofMillis(600));
            wait.until(ExpectedConditions.visibilityOf(usernameInput));
            return usernameInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void enterUsername(String username) {
        if (!isOnLoginPage()) {
            throw new IllegalStateException("Not on login page. User may already be logged in.");
        }
        waitForElementToBeVisible(usernameInput);
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }
    
    public void enterPassword(String password) {
        if (!isOnLoginPage()) {
            throw new IllegalStateException("Not on login page. User may already be logged in.");
        }
        waitForElementToBeVisible(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }
    
    public void clickLoginButton() {
        if (!isOnLoginPage()) {
            throw new IllegalStateException("Not on login page. User may already be logged in.");
        }
        waitForElementToBeClickable(loginButton);
        loginButton.click();
    }
    
    public void login(String username, String password) {
        // Check if already logged in
        if (isAlreadyLoggedIn()) {
            System.out.println("User is already logged in. Skipping login process.");
            return;
        }
        
        // Check if we're on login page
        if (!isOnLoginPage()) {
            throw new IllegalStateException("Not on login page and not already logged in. Current URL: " + driver.getCurrentUrl());
        }
        
        System.out.println("Starting login process...");
        
        // Clear and fill username
        waitForElementToBeVisible(usernameInput);
        usernameInput.clear();
        usernameInput.sendKeys(username);
        System.out.println("Username entered: " + username);
        
        // Clear and fill password
        waitForElementToBeVisible(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        System.out.println("Password entered");
        
        // Wait for form validation to complete and button to be enabled
        try {
            Thread.sleep(2000); // Wait for validation
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Check if button is enabled
        boolean isButtonEnabled = loginButton.isEnabled();
        System.out.println("Login button enabled: " + isButtonEnabled);
        if (!isButtonEnabled) {
            System.out.println("Login button is still disabled. Form may be invalid.");
            // Try clicking anyway
        }
        
        // Click login button
        loginButton.click();
        System.out.println("Login button clicked");
        
        // Wait for navigation to complete
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("After login URL: " + driver.getCurrentUrl());
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Waits for the URL to contain the query parameter "isRedirect=true" and verifies that 
     * it starts with the expected home path.
     */
    public boolean isRedirectedToHome() {
        FluentWait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(20)).pollingEvery(Duration.ofMillis(600));
        try {
            wait.until(ExpectedConditions.urlContains("isRedirect=true"));
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.startsWith("https://questapp.dev/admin/quest/home") 
                    && currentUrl.contains("isRedirect=true");
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Checks if the page displays the welcome message.
     */
    public boolean isWelcomeMessageDisplayed() {
        try {
            waitForElementToBeVisible(welcomeMessage);
            return welcomeMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isStillOnLoginPage() {
        return getCurrentUrl().contains("/login");
    }
    
    public boolean isDashboardDisplayed() {
        try {
            waitForElementToBeVisible(dashboardHeader);
            return dashboardHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getErrorMessage() {
        try {
            waitForElementToBeVisible(errorMessage);
            return errorMessage.getText();
        } catch (Exception e) {
            return "";
        }
    }
}
