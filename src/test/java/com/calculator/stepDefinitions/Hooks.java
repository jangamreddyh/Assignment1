package com.calculator.stepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

	private static AppiumDriver driver;
	private Properties props;
	private FileReader reader;
	
	@Before
	public void setup() throws IOException {
		System.out.println("Setting up Appium driver.");
		
		props=new Properties();
		reader=new FileReader("src/test/resources/config/config.properties");
		props.load(reader);
		
		if(driver == null) {
			try {
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("appium:platformName", props.getProperty("platformName"));
				caps.setCapability("appium:deviceName", props.getProperty("deviceName"));
				caps.setCapability("appium:platformVersion", props.getProperty("platformVersion"));
//				caps.setCapability("appium:appPackage", props.getProperty("appPackage"));
//				caps.setCapability("appium:appActivity", props.getProperty("appActivity"));
				caps.setCapability("appium:App", props.getProperty("app"));
				caps.setCapability("appium:automationName", props.getProperty("automationName"));
//				caps.setCapability("appium:fullReset", props.getProperty("fullReset"));
//				caps.setCapability("appium:noReset", props.getProperty("noReset"));

//				caps.setCapability("appium:deviceName", util.readProperty("Device_Name"));
//				caps.setCapability("appium:platformName", util.readProperty("Platform"));
//				caps.setCapability("appium:platformVersion", util.readProperty("Platform_Version"));
//				caps.setCapability("appium:app", util.readProperty("App"));
//				caps.setCapability("appium:automationName", util.readProperty("Automation_Name"));
				
				driver=new AndroidDriver(new URL(props.getProperty("appium.server.url")), caps);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				System.out.println("Appium driver initialized successfully.");
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to initialize Appium driver.");
			}
		}
	}
	
	@After
	public void tearDown() {
		System.out.println("Tearing down Appium driver...");
		if(driver != null) {
			driver.quit();
			driver=null;
			System.out.println("Appium driver quit successfully.");
		}
	}
	
	public static AppiumDriver getDriver() {
		System.out.println("Returning Appium driver instance...");
		return driver;
	}
	
	
}

