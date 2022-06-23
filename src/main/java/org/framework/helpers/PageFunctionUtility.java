package org.framework.helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class PageFunctionUtility {

	public static String getUSPString(WebDriver driver) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.navigate().refresh();
		Thread.sleep(4000);
		// specifically for "https://www.dexerto.com/" domain
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//html"))).perform();

		js.executeScript("let s;");
		js.executeScript(
				"__uspapi('getUSPData', 1, function (vendorConsents, success) { s =  vendorConsents; });");
		String[] x = js.executeScript("return s").toString().split(",");
		String uspString = x[0].split("=")[1];
		return (uspString);
	}

	
}
