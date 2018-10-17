package com.org.excelproject.myexcelproject;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import com.org.excelproject.myexcelproject.Browser;
import com.org.excelproject.myexcelproject.BrowserType;
import com.org.excelproject.myexcelproject.DriverContext;
import com.org.excelproject.myexcelproject.FrameworkInit;
import framework.config.ConfigReader;
import framework.config.Settings;
import framework.util.ExtentManager;

public class BaseTest extends FrameworkInit {
	
	public static ExtentReports rep;
	public static ExtentTest test;
	
	@BeforeSuite
	public void initiateSuite(){
		rep = ExtentManager.getExtentInstance();
	}
	
	@BeforeMethod
	public void setUp() throws FileNotFoundException, IOException{
		
		ConfigReader.populateSettings();
		FrameworkInit.InitFramework(BrowserType.valueOf(Settings.Browser));
		Browser.goToURL(Settings.URL);
	}
	

	@AfterMethod
	public void tearDown(){
		if(!(DriverContext.Driver==null)){
			DriverContext.Driver.close();
			DriverContext.Driver.quit();
		}
	}
	
	@AfterSuite
	public void closeSuite(){
		rep = null;
	}

}
