package com.questapp.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.questapp.config.Configuration;
import com.questapp.pages.CentrePage;
import com.questapp.pages.LoginPage;
import com.questapp.pages.TradePage;

public class TradeManagementTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private CentrePage centrePage;
    private TradePage tradePage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        centrePage = new CentrePage(driver);
        tradePage = new TradePage(driver);
    }

    @Test
    public void testAddNewTrade() {
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        loginPage.login(Configuration.getUsername(), Configuration.getPassword());
        centrePage.navigateToInstitutions();
        tradePage.navigateToTrades();
        String uniqueTradeName = "Test Trade " + System.currentTimeMillis();
        tradePage.createNewTrade(uniqueTradeName, "ITI");
        // Search for the added trade
        tradePage.searchTrade(uniqueTradeName);
        // Click edit icon against the trade
        tradePage.clickEditTrade(uniqueTradeName);
        // Edit the trade name and submit
        String editedTradeName = uniqueTradeName + " Edited";
        tradePage.editTrade(editedTradeName);
        // Add assertions as needed to verify trade edit
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 