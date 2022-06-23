package org.framework.pageObjects;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CCPA_ConsentMessagePage {

	public static WebDriver driver;
	public static ExtentTest test;
	public static ExtentReports report;

	public CCPA_ConsentMessagePage(WebDriver driver) throws InterruptedException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	boolean check = false;

	@FindBy(xpath = "//div[starts-with(@id, 'sp_message_container')]")
	public WebElement message_container;

	@FindBy(css = "button.message-button")
	public List<WebElement> messageButons;

	@FindBy(xpath = "//a")
	public List<WebElement> PMLink;

	public boolean checkForMessage(ExtentTest test) throws Exception {
		try {
			Thread.sleep(8000); // sometimes message takes time to load for few sites
			Actions action = new Actions(driver);

			driver.switchTo().defaultContent();
			action.moveToElement(driver.findElement(By.xpath("//html"))).perform();

			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//iframe[starts-with(@id,'sp_message_iframe')]")));

			if (message_container.getAttribute("style").contains("display: block;")) {
				switchToFrame("message");

				if ((driver.findElements(By.xpath("//p[contains(@class, 'message-component')]")).size() > 0)
						|| (driver.findElements(By.xpath("//div[contains(@class, 'message-component')]")).size() > 0)) {
					check = true;
				} else {
					check = false;
				}
			} else {
				check = false;
			}
		} catch (Exception ex) {
			test.log(LogStatus.INFO, "Check no message displayed for the site");
			check = false;
		}
		return check;
	}

	public void switchToFrame(String type) throws InterruptedException {
		List<WebElement> iframeList = driver.findElements(By.xpath("//iframe[starts-with(@id,'sp_message_iframe')]"));
		for (WebElement iframe : iframeList) {
			if (iframe.getAttribute("src").contains(type)) {
				driver.switchTo().frame(iframe);
				break;
			}
		}
	}

	public void selectOption(String option) throws Exception {
		boolean foundButton = false;

		try {
			driver.switchTo().defaultContent();
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(By.xpath("//html"))).perform();
			Thread.sleep(8000); // sometimes message takes time to load for few sites

			if (message_container.getAttribute("style").contains("display: block;")) {
				switchToFrame("message");
				if ((driver.findElements(By.xpath("//p[contains(@class, 'message-component')]")).size() > 0)
						|| (driver.findElements(By.xpath("//div[contains(@class, 'message-component')]")).size() > 0)) {

					// Check if options/settings/privacy manager is as button with in message iframe
					for (WebElement button : messageButons) {
						// Remove special char present in button text
						String buttonTitle = button.getAttribute("title");
						if ((option.contains(buttonTitle) && !buttonTitle.equalsIgnoreCase(""))) {
							foundButton = true;
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", button);
							break;
						}
					}
					if (!foundButton) {
						for (WebElement link : PMLink) {
							if (link.getText().contains(option)) {
								foundButton = true;
								JavascriptExecutor executor = (JavascriptExecutor) driver;
								executor.executeScript("arguments[0].click();", link);
								break;
							}
						}
					}
					if (!foundButton) {
						driver.switchTo().defaultContent();
						WebElement ele = driver
								.findElement(By.xpath("//a[starts-with(@data-link-name,'privacy-settings')]"));

						if (option.contains(ele.getText())) {
							foundButton = true;
							JavascriptExecutor executor = (JavascriptExecutor) driver;
							executor.executeScript("arguments[0].click();", ele);
						}
					}
				} else if (!foundButton) {
					for (WebElement link : PMLink) {
						if (option.contains(link.getText())) {
							foundButton = true;
							link.click();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
//			Thread.sleep(3000);
			// specific for https://www.bloomberg.com/ domain
			try {
				driver.switchTo().defaultContent();
				((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
				WebElement link = driver.findElement(By.cssSelector("div.bb-global-footer__group button"));
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", link);

			} catch (Exception e1) {
				try {
					// specific for https://deadspin.com/ domain
					WebElement link = driver.findElement(By.linkText(option));
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", link);
				} catch (Exception e2) {
					try {
						// specific for https://www.vice.com/ domain
						WebElement link = driver.findElement(By.xpath("//li[starts-with(@id,'" + option + "')]"));
						JavascriptExecutor executor = (JavascriptExecutor) driver;
						executor.executeScript("arguments[0].click();", link);
					} catch (Exception e3) {
						WebElement button = driver.findElement(By.xpath("//button[contains(text(),'" + option + "')]"));
						JavascriptExecutor executor = (JavascriptExecutor) driver;
						executor.executeScript("arguments[0].click();", button);
					}
				}
			}
		}
		Thread.sleep(3000);
	}

	public boolean checkBrokenLinks(ExtentTest test) throws InterruptedException {
		String url = "";
		// Thread.sleep(3000);

		boolean check = false;
		List<WebElement> allLinks = driver.findElements(By.xpath("//a[starts-with(@href,'https://')]"));
		if (allLinks.size() > 0) {
			for (WebElement link : allLinks) {
				url = link.getAttribute("href");
				try {
					test.log(LogStatus.INFO, "Check " + link.getText() + " link");
					// link.click();
					// Get all Open Tabs
					if (foundBrokenLink(url)) {
						test.log(LogStatus.INFO, url + " is a valid link");
						check = true;
					} else {
						test.log(LogStatus.INFO, url + " is a broken link");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			test.log(LogStatus.INFO, "No links found");
			System.out.println("No links found");
			check = true;
		}
		return check;
	}

	public boolean foundBrokenLink(String url) {
		boolean found = false;
		HttpURLConnection huc = null;
		int respCode = 200;
		try {
			huc = (HttpURLConnection) (new URL(url).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();

			respCode = huc.getResponseCode();

			if (respCode == 404) {
				System.out.println(url + " is a broken link");
				found = false;
			} else {
				System.out.println(url + " is a valid link");
				found = true;
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return found;
	}
}
