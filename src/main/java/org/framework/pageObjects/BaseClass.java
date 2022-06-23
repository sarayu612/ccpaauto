package org.framework.pageObjects;

import org.framework.driver.ConfigurationReader;
import org.framework.driver.Driver;
import org.framework.enums.PlatformName;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class BaseClass {
	public WebDriver driver;
	XSSFWorkbook excelWorkbook = null;
	XSSFSheet excelSheet = null;
	XSSFRow row = null;
	XSSFCell cell = null;

	public static ExtentReports report;
	public static ExtentTest test;
	public static String dest;
	public static String time;

	@BeforeMethod
	public void setupMethod() {
		driver = Driver.getDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@BeforeTest
	public void Reportsetup() {
		try {

			String thisYear = new SimpleDateFormat("yyyy").format(new Date());
			String thisMonth = new SimpleDateFormat("MMMM").format(new Date());
			String day = new SimpleDateFormat("dd").format(new Date());

			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
			Date date = new Date();
			time = dateFormat.format(date);

			report = new ExtentReports(System.getProperty("user.dir") + "//reports"+ "//Report_" + time + ".html", true);
			report.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));
//			report.setSystemInfo("Browser", ConfigurationReader.getProperty("browser"));
			
		} catch (Exception ex) {
			System.out.println("Issue is" + ex.getMessage());
		}
	}

	@AfterMethod
	public void teardownDriver(ITestResult result) throws IOException {
		try {
			if (result.getStatus() == ITestResult.FAILURE) {
				String screnshotpath = takeScreenshot(driver);
				test.log(LogStatus.FAIL, "Below is the screen shot:-" + test.addScreenCapture(screnshotpath));
				test.log(LogStatus.FAIL, "Test Case Fail is:- " + result.getName());
				// test.log(LogStatus.FAIL, result.getThrowable());

			} else if (result.getStatus() == ITestResult.SUCCESS) {
				test.log(LogStatus.PASS, "Test Case pass is:- " + result.getName());

			} else if (result.getStatus() == ITestResult.SKIP) {
				test.log(LogStatus.SKIP, "test Case skip is:- " + result.getName());

			} else if (result.getStatus() == ITestResult.STARTED) {
				test.log(LogStatus.INFO, "Test Case started");
			}
			report.endTest(test);
		} catch (Exception es) {
			System.out.println(" Report genration Excepion is:- " + es.getMessage());
		}

//		driver.quit();
		Driver.closeDriver();

//		logMessage("Driver has been quit from execution");
		// report.close();

	}

	String param = ConfigurationReader.getProperty("param");

	public void runOnEnvironmentWithVersion(String siteName, String environment, String version) {
		System.out.println("Running on " + environment+" with version " + version);

		// Run tests on preprod with the latest version

		if (environment.equals("preProd") && (version.equals("master-wrapperScript"))) {
			test.log(LogStatus.INFO, "Running test on preprod environment with the latest version of the script.");
			siteName = siteName + param;

			// Run tests on preprod with the versioned script

		} else if (environment.equals("preProd") && (!version.equals("master-wrapperScript"))) {
			test.log(LogStatus.INFO, "Running test on preprod environment with the versioned script: " + version);
			siteName = siteName + param + "&_sp_version=" + version;
		}

		// Run tests on prod
		System.out.println("Launch site :" + siteName);
		test.log(LogStatus.INFO, "Launch site :" + siteName);
		driver.navigate().to(siteName);
	}
	@AfterTest
	public void endTest() {
		report.flush();
	}

	public static String takeScreenshot(WebDriver driver) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");
			Date date = new Date();
			// System.out.println(dateFormat.format(date)); // 2016/11/16 12:08:43
			time = dateFormat.format(date);
			// System.out.println("Time is" + time);
			TakesScreenshot tc = (TakesScreenshot) driver;
			File src = tc.getScreenshotAs(OutputType.FILE);

			dest = System.getProperty("user.dir") + "//screenshots//" + time + ".png";
			File destination = new File(dest);
			FileUtils.copyFile(src, destination);
			// System.out.println("image destination" + dest);
			System.out.println("Screen shot taken");

			// return dest;
		} catch (Exception ex) {
			System.out.println("Screenshot error is" + ex.getMessage());
		}
		return dest;
	}
	
}