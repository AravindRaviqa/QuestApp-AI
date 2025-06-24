package com.questapp.config;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Configuration {
    private static final String BASE_URL = "https://questapp.dev/admin";
    private static final String USERNAME = "akhilraj@focaloid.com";
    private static final String PASSWORD = "Quest@123";
    private static final String DEFAULT_PROGRAM = "Default Program"; // Configure this based on available programs
    
    public static WebDriver initializeDriver() {
        try {
            // First try with WebDriverManager
            WebDriverManager.chromedriver().setup();
        } catch (Exception e) {
            // If WebDriverManager fails, try with local ChromeDriver if available
            System.out.println("WebDriverManager failed. Trying with local ChromeDriver...");
            
            // Try to find chromedriver in common locations
            String[] possibleDriverPaths = {
                "chromedriver.exe",
                "drivers/chromedriver.exe",
                "src/test/resources/drivers/chromedriver.exe",
                System.getProperty("user.dir") + "/chromedriver.exe",
                System.getProperty("user.dir") + "/drivers/chromedriver.exe",
                System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe"
            };
            
            for (String driverPath : possibleDriverPaths) {
                File driverFile = new File(driverPath);
                if (driverFile.exists()) {
                    System.setProperty("webdriver.chrome.driver", driverFile.getAbsolutePath());
                    System.out.println("Using ChromeDriver from: " + driverFile.getAbsolutePath());
                    break;
                }
            }
        }
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        
        // Add additional options to help with stability
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        return new ChromeDriver(options);
    }
    
    public static String getBaseUrl() {
        return BASE_URL;
    }
    
    public static String getUsername() {
        return USERNAME;
    }
    
    public static String getPassword() {
        return PASSWORD;
    }

    public static String getDefaultProgram() {
        return DEFAULT_PROGRAM;
    }
}