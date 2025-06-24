package com.questapp.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.questapp.config.Configuration;
import com.questapp.pages.CentrePage;
import com.questapp.pages.LoginPage;
import com.questapp.pages.ProjectPage;

public class ProjectManagementTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private CentrePage centrePage;
    private ProjectPage projectPage;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        centrePage = new CentrePage(driver);
        projectPage = new ProjectPage(driver);
    }

    @Test
    public void testAddNewProject() {
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        loginPage.login(Configuration.getUsername(), Configuration.getPassword());
        centrePage.navigateToInstitutions();
        projectPage.navigateToProjects();
        String uniqueProjectName = "Test Project " + System.currentTimeMillis();
        projectPage.createNewProject(uniqueProjectName, "Android Program"); // Replace with actual program name if needed
        // Search for the added project
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get("project_search_debug_page_source.html"), driver.getPageSource().getBytes());
            java.io.File scrFile = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            java.nio.file.Files.copy(scrFile.toPath(), java.nio.file.Paths.get("project_search_debug.png"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Error capturing debug info before search: " + e.getMessage());
        }
        projectPage.searchProject(uniqueProjectName);
        // Click edit icon against the project
        try {
            java.nio.file.Files.write(java.nio.file.Paths.get("project_edit_debug_page_source.html"), driver.getPageSource().getBytes());
            java.io.File scrFile = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            java.nio.file.Files.copy(scrFile.toPath(), java.nio.file.Paths.get("project_edit_debug.png"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            System.err.println("Error capturing debug info before edit: " + e.getMessage());
        }
        projectPage.clickEditProject(uniqueProjectName);
        // Edit the program and project name and submit
        String editedProjectName = uniqueProjectName + " Edited";
        String editedProgramName = "Prod Program"; // Replace with another actual program name if needed
        projectPage.editProject(editedProjectName, editedProgramName);
        // Add assertions as needed to verify project edit
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 