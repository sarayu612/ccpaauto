package org.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.framework.driver.ConfigurationReader;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

public class Driver {
    public Driver() {
    }

    private static WebDriver driver;
    static ChromeOptions chromeOptions = new ChromeOptions();
    static FirefoxOptions firefoxOptions = new FirefoxOptions();

    public static WebDriver getDriver() {
        if (driver == null) {
			switch (ConfigurationReader.getProperty("browser")) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
				driver = new ChromeDriver(chromeOptions);
				break;

			case "chrome-headless":
				WebDriverManager.chromedriver().setup();
				chromeOptions.setHeadless(true); // enable headless
				chromeOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
				driver = new ChromeDriver(chromeOptions);
				break;

			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
				driver = new FirefoxDriver(firefoxOptions);
				break;

			case "firefox-headless":
				WebDriverManager.firefoxdriver().setup();
				firefoxOptions.setHeadless(true); // enable headless
				firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NONE);
				driver = new FirefoxDriver(firefoxOptions);
				break;

			case "safari":
				WebDriverManager.getInstance(SafariDriver.class).setup();
				driver = new SafariDriver();
				break;
			}
		}

        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.close();
            driver = null;
        }
    }
}
