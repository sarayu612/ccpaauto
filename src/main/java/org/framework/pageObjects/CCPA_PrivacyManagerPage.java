package org.framework.pageObjects;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CCPA_PrivacyManagerPage {
	WebDriver driver;
	private static final int DEFAULT_ELEMENT_TIMEOUT = 30;

	public CCPA_PrivacyManagerPage(WebDriver driver) throws InterruptedException {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	boolean checkthis = false;

	public void deselectVendorsAndPurposes(String value, String option) throws InterruptedException {
		Thread.sleep(3000); // For few of the sites Privacy Manager frame takes time to load completely

		driver.switchTo().defaultContent();
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tabs.size() > 1) {
			driver.switchTo().window(tabs.get(1));
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Do Not Sell My Data')]"));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", button);
		}
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("sp_privacy_manager_iframe"))));

		driver.switchTo().frame("sp_privacy_manager_iframe");
		deselect(value);
		List<WebElement> pmButtons = driver.findElements(By.cssSelector("button.w-button"));
		List<WebElement> pmLinks = driver.findElements(By.cssSelector("div.priv-main a.w-inline-block"));
		pmButtons.addAll(pmLinks);

		for (WebElement pmButton : pmButtons) {
			if (option.contains(pmButton.getText())) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", pmButton);
				break;
			}
		}
		Thread.sleep(3000);

	}

	List<WebElement> ccpa_pmSwitches;
	List<WebElement> ccpa_pmToggels;

	public void deselect(String value) {
		ccpa_pmToggels = driver.findElements(By.xpath("//a[starts-with(@class,'switch-bg')]"));

		if (ccpa_pmToggels.size() == 0) {
			ccpa_pmSwitches = driver.findElements(
					By.xpath("//div[starts-with(@class,'sp-switch-arrow-block')]/a[starts-with(@class, 'neutral')]"));
		}
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		try {
			if (ccpa_pmToggels.size() > 0) {
				for (WebElement ccpa_pmToggel : ccpa_pmToggels) {
					WebElement ele = ccpa_pmToggel.findElement(By.cssSelector("div.inner-switch"));
					executor.executeScript("arguments[0].click();", ele);
				}
			} else if (ccpa_pmSwitches.size() > 0) {
				for (WebElement ccpa_pmSwitche : ccpa_pmSwitches) {
					WebElement ele = ccpa_pmSwitche.findElement(By.cssSelector("div.right"));
					executor.executeScript("arguments[0].click();", ele);
				}
			}
		} catch (Exception e) {
			driver.findElement(By.cssSelector("a.priv-reject-btn")).click();
		}
	}

	public boolean defaultSelection(String value) throws InterruptedException {
		Thread.sleep(3000); // For few of the sites Privacy Manager frame takes time to load completely

		driver.switchTo().defaultContent();
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tabs.size() > 1) {
			driver.switchTo().window(tabs.get(1));
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Do Not Sell My Data')]"));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", button);
		}

		driver.switchTo().frame("sp_privacy_manager_iframe");
	//	List<WebElement> allCCPATabs = driver.findElements(By.cssSelector("div.tab-container li.tab"));
		checkDefaultValue(value);
		return checkthis;
	}

	public void checkDefaultValue(String value) {
		ccpa_pmToggels = driver.findElements(By.xpath("//a[starts-with(@class,'switch-bg')]"));
		if (ccpa_pmToggels.size() == 0) {
			ccpa_pmSwitches = driver.findElements(
					By.xpath("//div[starts-with(@class,'sp-switch-arrow-block')]/a[starts-with(@class, 'neutral')]"));
		}
		if (ccpa_pmToggels.size() > 0) {
			for (WebElement ccpa_pmToggel : ccpa_pmToggels) {
				if (ccpa_pmToggel.getAttribute("class").contains(value)) {
					checkthis = true;
				} else {
					checkthis = false;
				}
			}
		} else if (ccpa_pmSwitches.size() > 0) {
			for (WebElement ccpa_pmSwitche : ccpa_pmSwitches) {
				if (ccpa_pmSwitche.getAttribute("class").contains(value)) {
					checkthis = true;
				} else {
					checkthis = false;
				}
			}
		}
	}

	public boolean disappearPM(ExtentTest test) {
		driver.switchTo().defaultContent();
		test.log(LogStatus.INFO, "Privacy Manager disappears");
		if (driver.findElements(By.cssSelector("iframe#sp_privacy_manager_iframe")).size() == 0) {
			return true;
		} else {
			return false;
		}

	}

	public void chooseButton(String option) throws InterruptedException {
//		Thread.sleep(6000); // For few of the sites Privacy Manager frame takes time
		// to load completely
		// Get all Open Tabs
		// specific for https://www.macworld.com/ domain
		driver.switchTo().defaultContent();

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tabs.size() > 1) {
			driver.switchTo().window(tabs.get(1));
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

			WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Do Not Sell My Data')]"));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", button);

		}

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("sp_privacy_manager_iframe"))));

		driver.switchTo().frame("sp_privacy_manager_iframe");
		List<WebElement> pmButtons = driver.findElements(By.cssSelector("button.w-button"));
		List<WebElement> pmLinks = driver.findElements(By.cssSelector("div.priv-main a.w-inline-block"));
		pmButtons.addAll(pmLinks);

		for (WebElement pmButton : pmButtons) {
			if (option.contains(pmButton.getText())) {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", pmButton);
				break;
			}
		}
		Thread.sleep(3000);
		ArrayList<String> afterTabs = new ArrayList<String>(driver.getWindowHandles());
		if (afterTabs.size() > 1) {
			driver.close();
			driver.switchTo().window(afterTabs.get(0));
		}
	}

	public boolean checkBrokenLinks(ExtentTest test) throws InterruptedException {
		String url = "";

		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		if (tabs.size() > 1) {
			driver.switchTo().window(tabs.get(1));
			((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
			WebElement button = driver.findElement(By.xpath("//button[contains(text(),'Do Not Sell My Data')]"));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", button);
		}

		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//iframe[starts-with(@id,'sp_privacy_manager_iframe')]")));

		driver.switchTo().frame("sp_privacy_manager_iframe");
		boolean check = false;
		List<WebElement> allLinks = driver
				.findElements(By.xpath("//div[starts-with(@class,'priv-main')]/a[starts-with(@href,'https://')]"));
		List<WebElement> allLinks1 = driver.findElements(
				By.xpath("//div[starts-with(@class,'priv-ft-container')]/a[starts-with(@href,'https://')]"));
		allLinks.addAll(allLinks1);

		if (allLinks.size() > 0) {
			for (WebElement link : allLinks) {
				url = link.getAttribute("href");
				try {
					test.log(LogStatus.INFO, "Check " + link.getText() + " link");
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
