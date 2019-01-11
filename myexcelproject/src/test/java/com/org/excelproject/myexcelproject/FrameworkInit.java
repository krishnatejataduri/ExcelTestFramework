package com.org.excelproject.myexcelproject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import framework.listeners.EventListener;

public class FrameworkInit {
	
	public static void InitFramework(BrowserType Browser){
		switch(Browser){
			case Chrome:{
				System.setProperty("webdriver.chrome.driver", ".\\resources\\drivers\\chromedriver.exe");
				//DesiredCapabilities dc = new DesiredCapabilities();
				//dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
				DriverContext.Driver = new EventFiringWebDriver(new ChromeDriver());
				DriverContext.eventListener = new EventListener();
				DriverContext.Driver.register(DriverContext.eventListener);
				break;
			}
			case Firefox:{
				break;
			}
			case IE:{
				break;
			}
			default:{
				System.setProperty("webdriver.chrome.driver", ".\\resources\\drivers\\chromedriver.exe");
				DriverContext.Driver = new EventFiringWebDriver(new ChromeDriver());
				DriverContext.eventListener = new EventListener();
				DriverContext.Driver.register(DriverContext.eventListener);
				break;
			}
		}
		
		DriverContext.Driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		Browser browser = new Browser();
	}
}
