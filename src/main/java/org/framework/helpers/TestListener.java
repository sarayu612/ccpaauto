package org.framework.helpers;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener{

	public void onTestStart(ITestResult result) {
	
		System.out.println("Test case start "+result.getName());
		
	}

	public void onTestSuccess(ITestResult result) {
	
		System.out.println("Test case pass "+result.getName());
	}

	public void onTestFailure(ITestResult result) {
	
		System.out.println("Test case fail "+result.getName());
	}

	public void onTestSkipped(ITestResult result) {
		
		System.out.println("Test case Skiped "+result.getName());
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	
	}

	public void onStart(ITestContext context) {
		
	}

	public void onFinish(ITestContext context) {
	
	}

}
