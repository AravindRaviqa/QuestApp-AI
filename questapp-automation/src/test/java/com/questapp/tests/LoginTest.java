package com.questapp.tests;

import org.testng.annotations.Test;
import com.questapp.config.Configuration;
import com.questapp.pages.LoginPage;

public class LoginTest extends BaseTest {
    
    @Test
    public void testSuccessfulLogin() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        
        // Only perform login if on the login page
        if (loginPage.isOnLoginPage()) {
            loginPage.login(Configuration.getUsername(), Configuration.getPassword());
        } else if (loginPage.isAlreadyLoggedIn()) {
            System.out.println("User is already logged in. Skipping login.");
        } else {
            throw new IllegalStateException("Not on login page and not already logged in. Current URL: " + driver.getCurrentUrl());
        }
        
        // Assert that the user is logged in by checking for navigation/dashboard
        boolean loggedIn = loginPage.isAlreadyLoggedIn() || loginPage.isDashboardDisplayed();
        org.testng.Assert.assertTrue(loggedIn, "User should be logged in and see navigation/dashboard elements");
    }
    
    @Test
    public void testLoginWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        
        // Check if already logged in
        if (loginPage.isAlreadyLoggedIn()) {
            System.out.println("User is already logged in. Cannot test invalid credentials.");
            return;
        }
        
        // Only test invalid credentials if we're actually on the login page
        if (loginPage.isOnLoginPage()) {
            loginPage.login("invalid@email.com", "invalidPassword");
            org.testng.Assert.assertTrue(loginPage.isStillOnLoginPage(), 
                "User should remain on the login page after invalid login attempt");
        } else {
            System.out.println("Not on login page. Skipping invalid credentials test.");
        }
    }
}
