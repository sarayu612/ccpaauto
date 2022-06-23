package org.framework.pageObjects;

import org.openqa.selenium.WebDriver;

public class PageWrapper {
	
  	public CCPA_PrivacyManagerPage ccpa_PrivacyManagerPage;
  	public CCPA_ConsentMessagePage ccpa_ConsentMessagePage;
  	
  	public PageWrapper(WebDriver driver) throws InterruptedException {
		ccpa_PrivacyManagerPage = new CCPA_PrivacyManagerPage(driver);
		ccpa_ConsentMessagePage = new CCPA_ConsentMessagePage(driver);
	}
}

