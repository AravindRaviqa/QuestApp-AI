package com.questapp.tests;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.questapp.config.Configuration;
import com.questapp.pages.LoginPage;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class UIDebugTest extends BaseTest {
    
    @Test
    public void debugCurrentPageStructure() throws Exception {
        // First, ensure we're logged in
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        
        if (!loginPage.isAlreadyLoggedIn()) {
            System.out.println("Attempting to log in...");
            loginPage.login(Configuration.getUsername(), Configuration.getPassword());
            System.out.println("Login attempt completed.");
        }
        
        // Wait for page to load
        Thread.sleep(5000);
        
        System.out.println("=== UI Structure Debug ===");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
        
        // Capture page source for analysis
        String pageSource = driver.getPageSource();
        try (FileWriter writer = new FileWriter("current_page_source.html")) {
            writer.write(pageSource);
        }
        
        // Find all buttons and their text
        System.out.println("\n=== All Buttons ===");
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        for (int i = 0; i < buttons.size(); i++) {
            try {
                String buttonText = buttons.get(i).getText().trim();
                String buttonClass = buttons.get(i).getAttribute("class");
                boolean isEnabled = !buttons.get(i).getAttribute("disabled").equals("true");
                System.out.println("Button " + i + ": Text='" + buttonText + "', Class='" + buttonClass + "', Enabled=" + isEnabled);
            } catch (Exception e) {
                System.out.println("Button " + i + ": Error reading - " + e.getMessage());
            }
        }
        
        // Find all spans and their text
        System.out.println("\n=== All Spans ===");
        List<WebElement> spans = driver.findElements(By.tagName("span"));
        for (int i = 0; i < Math.min(spans.size(), 30); i++) { // Limit to first 30 spans
            try {
                String spanText = spans.get(i).getText().trim();
                String spanClass = spans.get(i).getAttribute("class");
                if (!spanText.isEmpty()) {
                    System.out.println("Span " + i + ": Text='" + spanText + "', Class='" + spanClass + "'");
                }
            } catch (Exception e) {
                // Ignore errors for spans
            }
        }
        
        // Find all navigation-related elements
        System.out.println("\n=== Navigation Elements ===");
        List<WebElement> navElements = driver.findElements(By.xpath("//*[contains(@class, 'nav') or contains(@class, 'menu') or contains(@class, 'sidebar') or contains(@class, 'toolbar')]"));
        for (int i = 0; i < navElements.size(); i++) {
            try {
                String elementText = navElements.get(i).getText().trim();
                String elementClass = navElements.get(i).getAttribute("class");
                String elementTag = navElements.get(i).getTagName();
                System.out.println("Nav Element " + i + ": Tag='" + elementTag + "', Text='" + elementText + "', Class='" + elementClass + "'");
            } catch (Exception e) {
                System.out.println("Nav Element " + i + ": Error reading - " + e.getMessage());
            }
        }
        
        // Find all elements with mat-button-wrapper class
        System.out.println("\n=== Mat Button Wrapper Elements ===");
        List<WebElement> matButtons = driver.findElements(By.className("mat-button-wrapper"));
        for (int i = 0; i < matButtons.size(); i++) {
            try {
                String buttonText = matButtons.get(i).getText().trim();
                String parentClass = matButtons.get(i).findElement(By.xpath("..")).getAttribute("class");
                System.out.println("Mat Button " + i + ": Text='" + buttonText + "', Parent Class='" + parentClass + "'");
            } catch (Exception e) {
                System.out.println("Mat Button " + i + ": Error reading - " + e.getMessage());
            }
        }
        
        // Find all clickable elements with specific text patterns
        System.out.println("\n=== Elements with Navigation Text ===");
        String[] navigationTexts = {"Institutions", "Programs", "Centres", "Content", "Subjects", "Lessons", "Projects", "Phases", "Organisations", "Trades", "Dashboard", "Home"};
        for (String text : navigationTexts) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(), '" + text + "')]"));
                for (WebElement element : elements) {
                    String elementText = element.getText().trim();
                    String elementClass = element.getAttribute("class");
                    String elementTag = element.getTagName();
                    System.out.println("Found '" + text + "': Tag='" + elementTag + "', Text='" + elementText + "', Class='" + elementClass + "'");
                }
            } catch (Exception e) {
                // Ignore errors
            }
        }
        
        // Find all elements with specific classes that might be navigation
        System.out.println("\n=== Elements with Navigation Classes ===");
        String[] navigationClasses = {"sidebar", "menu", "nav", "toolbar", "header", "navigation", "sidenav", "drawer"};
        for (String className : navigationClasses) {
            try {
                List<WebElement> elements = driver.findElements(By.xpath("//*[contains(@class, '" + className + "')]"));
                for (WebElement element : elements) {
                    String elementClass = element.getAttribute("class");
                    String elementTag = element.getTagName();
                    System.out.println("Found '" + className + "': Tag='" + elementTag + "', Class='" + elementClass + "'");
                }
            } catch (Exception e) {
                // Ignore errors
            }
        }
        
        // Find all clickable elements (buttons, links, etc.)
        System.out.println("\n=== All Clickable Elements ===");
        List<WebElement> clickableElements = driver.findElements(By.xpath("//button | //a | //*[@role='button'] | //*[@tabindex='0']"));
        for (int i = 0; i < Math.min(clickableElements.size(), 20); i++) {
            try {
                WebElement element = clickableElements.get(i);
                String elementText = element.getText().trim();
                String elementClass = element.getAttribute("class");
                String elementTag = element.getTagName();
                if (!elementText.isEmpty()) {
                    System.out.println("Clickable " + i + ": Tag='" + elementTag + "', Text='" + elementText + "', Class='" + elementClass + "'");
                }
            } catch (Exception e) {
                // Ignore errors
            }
        }
        
        System.out.println("\n=== Debug Complete ===");
        System.out.println("Page source saved to: current_page_source.html");
    }
} 