package com.questapp.tests;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import com.questapp.config.Configuration;
import com.questapp.pages.LoginPage;
import java.io.FileWriter;
import java.util.List;

public class LoginDebugTest extends BaseTest {
    
    @Test
    public void testLoginAndCaptureStructure() throws Exception {
        LoginPage loginPage = new LoginPage(driver);
        
        System.out.println("=== Login Debug Test ===");
        System.out.println("Base URL: " + Configuration.getBaseUrl());
        System.out.println("Username: " + Configuration.getUsername());
        System.out.println("Password: " + Configuration.getPassword());
        
        // Navigate to login page
        loginPage.navigateToLoginPage(Configuration.getBaseUrl());
        System.out.println("Initial URL: " + driver.getCurrentUrl());
        
        // Check if already logged in
        if (loginPage.isAlreadyLoggedIn()) {
            System.out.println("Already logged in!");
        } else {
            System.out.println("Not logged in, attempting login...");
            
            // Perform login
            loginPage.login(Configuration.getUsername(), Configuration.getPassword());
            
            // Wait for navigation
            Thread.sleep(5000);
            System.out.println("After login URL: " + driver.getCurrentUrl());
        }
        
        // Capture page structure after login
        System.out.println("\n=== Page Structure After Login ===");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());
        
        // Save page source
        String pageSource = driver.getPageSource();
        try (FileWriter writer = new FileWriter("after_login_page_source.html")) {
            writer.write(pageSource);
        }
        System.out.println("Page source saved to: after_login_page_source.html");
        
        // Find all buttons
        System.out.println("\n=== All Buttons ===");
        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        for (int i = 0; i < buttons.size(); i++) {
            try {
                String buttonText = buttons.get(i).getText().trim();
                String buttonClass = buttons.get(i).getAttribute("class");
                boolean isEnabled = !"true".equals(buttons.get(i).getAttribute("disabled"));
                System.out.println("Button " + i + ": Text='" + buttonText + "', Class='" + buttonClass + "', Enabled=" + isEnabled);
            } catch (Exception e) {
                System.out.println("Button " + i + ": Error reading - " + e.getMessage());
            }
        }
        
        // Find all spans with text
        System.out.println("\n=== All Spans with Text ===");
        List<WebElement> spans = driver.findElements(By.tagName("span"));
        for (int i = 0; i < Math.min(spans.size(), 50); i++) {
            try {
                String spanText = spans.get(i).getText().trim();
                String spanClass = spans.get(i).getAttribute("class");
                if (!spanText.isEmpty()) {
                    System.out.println("Span " + i + ": Text='" + spanText + "', Class='" + spanClass + "'");
                }
            } catch (Exception e) {
                // Ignore errors
            }
        }
        
        // Find navigation elements
        System.out.println("\n=== Navigation Elements ===");
        String[] navTexts = {"Institutions", "Programs", "Centres", "Content", "Subjects", "Lessons", "Projects", "Phases", "Organisations", "Trades", "Dashboard", "Home", "Menu", "Navigation"};
        for (String text : navTexts) {
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
        
        // Find all clickable elements
        System.out.println("\n=== All Clickable Elements ===");
        List<WebElement> clickableElements = driver.findElements(By.xpath("//button | //a | //*[@role='button'] | //*[@tabindex='0'] | //*[contains(@class, 'clickable')]"));
        for (int i = 0; i < Math.min(clickableElements.size(), 30); i++) {
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
        
        System.out.println("\n=== Login Debug Test Complete ===");
    }
} 