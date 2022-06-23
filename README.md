# Web-Automation

This is web automation framework, implemented using Java, Selenium/Webdriver, TestNG & Maven. Page Object Model (POM) is used to make the code more readable, maintainable, and reusable.

This framework able to run smoke test suites for and CCPA domains.

# Prerequisite
1. Java jdk-1.8
    https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
2. Apache Maven 3 or above
    https://maven.apache.org/install.html
3. Browsers (Firefox, Chrome)

# Set environmental variable 
1. Open terminal
2. Open bash_profile. Type below command and press enter 
    `nano ~/.bash_profile`
3. Add below lines

    `export environment=prod`
    
    `launchctl setenv environment $environment`
    
    `export version=master-wrapperScript`
    
    `launchctl setenv version $version`
    
4. Save and exit
5. Type command in terminal
    `source ~/.bash_profile`
6. Check once if variable is set properly or not

    `echo $environment`
    
    `echo $version`
    
# Run test suite 
1. Get the code on your machine
2. Go to project root directoty from Terminal
3. Run command to start execution of test suite
    
    To run all tests for CCPA smoke test suite, use below command
    
      `mvn clean test -DsuiteXmlFile=ccpa_testngSuite.xml`
      
    It will start running all test cases for all mentioned sites in 'CCPA_SitesData.xlsx' on Chrome browser 

# Check Test Execution Report
1. On finish of test suite Extent Report will get gererate in `ExtentReport` folder created in project root directory
2. Open generated html report in browser



