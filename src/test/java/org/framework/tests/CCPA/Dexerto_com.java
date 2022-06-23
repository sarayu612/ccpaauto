package org.framework.tests.CCPA;

import org.framework.driver.ConfigurationReader;
import org.framework.helpers.PageFunctionUtility;
import org.framework.pageObjects.BaseClass;
import org.framework.pageObjects.PageWrapper;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

public class Dexerto_com extends BaseClass {

	String siteName = "https://www.dexerto.com/";
	String param = ConfigurationReader.getProperty("param");
	String environment = System.getenv("environment");
	String version = System.getenv("version");
	String acceptOption = "Continue";
	String pmOption = "Do Not Sell My Info";
	String pmSaveAndExit = "Save & Exit/false";
	String pmCancel = "Cancel/true";

	@Test
	public void checkAcceptAllFromFirstLayerMessage() throws Exception {
		PageWrapper pageWrapper = new PageWrapper(driver);
		test = report.startTest("Check USPString returns `1YNN` with Accept All from first layer message: " + siteName);
		System.out.println("Start - checkAcceptAllFromFirstLayerMessage");

		test.assignCategory("Accept All - First Layer Message");
		test.log(LogStatus.INFO, "Launch site : " + siteName +" on " + environment + " with " + version);
		runOnEnvironmentWithVersion(siteName, environment, version);

		Assert.assertTrue(pageWrapper.ccpa_ConsentMessagePage.checkForMessage(test));
		test.log(LogStatus.INFO, "Choose '" + acceptOption.split("/")[0] + "' option from first layer message");
		pageWrapper.ccpa_ConsentMessagePage.selectOption(acceptOption);

		test.log(LogStatus.INFO, "Refresh/reload the page");
		driver.navigate().refresh();
		if (acceptOption.contains("true")) {
			Assert.assertTrue(pageWrapper.ccpa_ConsentMessagePage.checkForMessage(test));
		}
		test.log(LogStatus.INFO, "Check for broken links from Privacy Manager");
		Assert.assertTrue(PageFunctionUtility.getUSPString(driver).equalsIgnoreCase("1YNN"));
	}

	@Test
	public void checkBrokenLinksFromMessage() throws Exception {
		PageWrapper pageWrapper = new PageWrapper(driver);
		test = report.startTest("Check for broken links from first layer message displayed for site: " + siteName);
		test.assignCategory("Check Broken links: First layer message");
		System.out.println("Start - checkBrokenLinksFromMessage");

		test.log(LogStatus.INFO, "Launch site : " + siteName +" on " + environment + " with " + version);
		runOnEnvironmentWithVersion(siteName, environment, version);

		test.log(LogStatus.INFO, "Check for first layer message");
		Assert.assertTrue(pageWrapper.ccpa_ConsentMessagePage.checkForMessage(test));
		test.log(LogStatus.INFO, "Check for broken links from first layer message");
		Assert.assertTrue(pageWrapper.ccpa_ConsentMessagePage.checkBrokenLinks(test));
	}

	@Test
	public void checkBrokenLinksFromPM() throws Exception {
		PageWrapper pageWrapper = new PageWrapper(driver);
		test = report.startTest("Check broken links from privacy manager: " + siteName);
		System.out.println("Start - checkBrokenLinksFromPM");

		test.assignCategory("Check Broken links: Privacy manager");
		test.log(LogStatus.INFO, "Launch site : " + siteName +" on " + environment + " with " + version);
		runOnEnvironmentWithVersion(siteName, environment, version);

		test.log(LogStatus.INFO, "Choose '" + pmOption + "' option from first layer message");
		pageWrapper.ccpa_ConsentMessagePage.selectOption(pmOption);

		test.log(LogStatus.INFO, "Check for broken links from Privacy Manager");
		Assert.assertTrue(pageWrapper.ccpa_PrivacyManagerPage.checkBrokenLinks(test));
	}

	@Test
	public void checkCancelFromPM() throws Exception {
		PageWrapper pageWrapper = new PageWrapper(driver);
		test = report.startTest("Check Cancel from privacy manager: " + siteName);
		System.out.println("Start - checkCancelFromPM");

		test.assignCategory("Cancel - Privacy Manager");
		test.log(LogStatus.INFO, "Launch site : " + siteName +" on " + environment + " with " + version);
		runOnEnvironmentWithVersion(siteName, environment, version);

		test.log(LogStatus.INFO, "Choose '" + pmOption + "' option from first layer message");
		pageWrapper.ccpa_ConsentMessagePage.selectOption(pmOption);

		test.log(LogStatus.INFO, "Choose '" + pmCancel.split("/")[0] + "' option from Privacy Manager");
		pageWrapper.ccpa_PrivacyManagerPage.chooseButton(pmCancel);
		Assert.assertTrue(pageWrapper.ccpa_PrivacyManagerPage.disappearPM(test));

		if (pmCancel.contains("true")) {
			Assert.assertTrue(pageWrapper.ccpa_ConsentMessagePage.checkForMessage(test));
		}
	}

	@Test
	public void checkDefaultSelection() throws Exception {
		PageWrapper pageWrapper = new PageWrapper(driver);

		test = report
				.startTest("Check default selection of Vendors and Purposes as true from privacy manager: " + siteName);
		System.out.println("Start - checkDefaultSelection");

		test.assignCategory("Default choose - Privacy Manager");
		test.log(LogStatus.INFO, "Launch site : " + siteName +" on " + environment + " with " + version);
		runOnEnvironmentWithVersion(siteName, environment, version);

		test.log(LogStatus.INFO, "Choose '" + pmOption + "' option from first layer message");
		pageWrapper.ccpa_ConsentMessagePage.selectOption(pmOption);

		test.log(LogStatus.INFO, "Check defult selection as true for vendors and purposes");
		Assert.assertTrue(pageWrapper.ccpa_PrivacyManagerPage.defaultSelection("on"));
	}

	@Test
	public void checkSaveAndExitFromPM() throws Exception {
		PageWrapper pageWrapper = new PageWrapper(driver);

		test = report.startTest(
				"Check USPString returns 1YNN with defualt selection(true) from Privacy Manager: " + siteName);
		System.out.println("Start - checkSaveAndExitFromPM");

		test.assignCategory("Save & Exit - Privacy Manager");
		test.log(LogStatus.INFO, "Launch site : " + siteName +" on " + environment + " with " + version);
		runOnEnvironmentWithVersion(siteName, environment, version);

		test.log(LogStatus.INFO, "Choose '" + pmOption + "' option from first layer message");
		pageWrapper.ccpa_ConsentMessagePage.selectOption(pmOption);

		test.log(LogStatus.INFO, "Choose '" + pmSaveAndExit.split("/")[0] + "' option from Privacy Manager");
		pageWrapper.ccpa_PrivacyManagerPage.chooseButton(pmSaveAndExit);

		test.log(LogStatus.INFO, "Refresh/reload the page");
		driver.navigate().refresh();
		if (pmSaveAndExit.contains("false")) {
			Assert.assertFalse(pageWrapper.ccpa_ConsentMessagePage.checkForMessage(test));
		}
		test.log(LogStatus.INFO, "Check USPString returns `1YNN`");
		Assert.assertTrue(PageFunctionUtility.getUSPString(driver).equalsIgnoreCase("1YNN"));
	}

	@Test
	public void checkSaveAndExitWithDataChange() throws Exception {
		PageWrapper pageWrapper = new PageWrapper(driver);

		test = report.startTest("Check USPString returns 1YYN with opt-out from Privacy Manager: " + siteName);
		System.out.println("Start - checkSaveAndExitWithDataChange");

		test.assignCategory("Save & Exit - Privacy Manager");
		test.log(LogStatus.INFO, "Launch site : " + siteName +" on " + environment + " with " + version);
		runOnEnvironmentWithVersion(siteName, environment, version);

		test.log(LogStatus.INFO, "Choose '" + pmOption + "' option from first layer message");
		pageWrapper.ccpa_ConsentMessagePage.selectOption(pmOption);

		test.log(LogStatus.INFO, "Opt-out by de-selecting all buttons and choose Save & Exit");
		pageWrapper.ccpa_PrivacyManagerPage.deselectVendorsAndPurposes("off", pmSaveAndExit);

		test.log(LogStatus.INFO, "Refresh/reload the page");
		driver.navigate().refresh();
		test.log(LogStatus.INFO, "Check USPString returns `1YYN`");
		Assert.assertTrue(PageFunctionUtility.getUSPString(driver).equalsIgnoreCase("1YYN"));
	}
}
