package com.questapp.tests;

import com.questapp.config.Configuration;
import com.questapp.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    
    @BeforeMethod
    public void setUp() {
        driver = Configuration.initializeDriver();
        loginPage = new LoginPage(driver);
        
        // Navigate to the application
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        
        // Check if already logged in, if not, perform login
        if (!loginPage.isAlreadyLoggedIn()) {
            loginPage.login(Configuration.getUsername(), Configuration.getPassword());
        }
        
        // Verify we're on the home page (either already logged in or just logged in)
        org.testng.Assert.assertTrue(loginPage.isWelcomeMessageDisplayed(), 
            "User should be on the home page with welcome message displayed");
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}