package com.questapp.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.questapp.config.Configuration;
import com.questapp.pages.LoginPage;
import com.questapp.pages.ProgramManagementPage;
import com.questapp.pages.OrganisationPage;
import java.util.Random;

public class OrganisationManagementTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProgramManagementPage programManagementPage;
    private OrganisationPage organisationPage;

    @BeforeMethod
    public void setup() {
        driver = Configuration.initializeDriver();
        loginPage = new LoginPage(driver);
        programManagementPage = new ProgramManagementPage(driver);
        organisationPage = new OrganisationPage(driver);
    }

    @Test
    public void testAddNewOrganisation() {
        // Login
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        loginPage.login(Configuration.getUsername(), Configuration.getPassword());

        // Navigate to Institutions and Organisations
        programManagementPage.navigateToInstitutions();
        organisationPage.navigateToOrganisations();

        // Create new organisation
        String orgName = "Test Org " + System.currentTimeMillis();
        String orgCode = "ORG" + System.currentTimeMillis();
        String address = "123 Main St";
        String phone = String.valueOf(1000000000L + new Random().nextInt(900000000));
        String email = "testorg" + System.currentTimeMillis() + "@example.com";
        organisationPage.createNewOrganisation(orgName, orgCode, address, phone, email);

        // Search for the created organisation
        organisationPage.searchOrganisation(orgName);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Click organisation name to open details page
        organisationPage.clickOrganisationName(orgName);
        // Click edit button on details page
        organisationPage.clickEditButtonOnDetailsPage();
        // Update organisation details
        String updatedOrgName = "Updated " + orgName;
        
        String updatedAddress = "456 Updated St";
        String updatedPhone = String.valueOf(2000000000L + new Random().nextInt(900000000));
        String updatedEmail = "updated" + System.currentTimeMillis() + "@example.com";
        // After updating organisation details
        organisationPage.updateOrganisationDetails(updatedOrgName, updatedAddress, updatedPhone, updatedEmail);
        organisationPage.clickGoBackButton();
        organisationPage.searchOrganisation(updatedOrgName);
        // Optionally, search and verify update
        try {
            Thread.sleep(2000);
            organisationPage.searchOrganisation(updatedOrgName);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}