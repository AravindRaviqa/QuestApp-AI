package com.questapp.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.questapp.config.Configuration;
import com.questapp.pages.CentrePage;
import com.questapp.pages.LoginPage;

public class CentreManagementTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private CentrePage centrePage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        centrePage = new CentrePage(driver);
    }

    @Test
    public void testAddNewCentre() {
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        loginPage.login(Configuration.getUsername(), Configuration.getPassword());
        centrePage.navigateToInstitutions();
        centrePage.navigateToCentres();
        String uniqueCentreName = "Test Centre " + System.currentTimeMillis();
        centrePage.createNewCentre(
            uniqueCentreName,
            "AAMA Agnel Technical Institute", // Organisation name (first option)
            "Colleges",                       // Centre type (first option)
            "Learnpi",                        // Working mode (first option)
            "Knowledge Partnerships",// Type of partnership (first option)
            "Assam (AS)",                     // State (unchanged, update if needed)
            "Cachar",           // District (unchanged, update if needed)
            "Los Angeles"                     // City (unchanged, update if needed)
        );
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}