package com.org.excelproject.myexcelproject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Browser extends DriverContext {
	
	public static void goToURL(String URL){
		Driver.navigate().to(URL);
	}
	
	public static void maximize(){
		Driver.manage().window().maximize();
	}
	
	public static void selectFrmDropdown(WebElement element, String VisibleText){
		Select select = new Select(element);
		select.selectByVisibleText(VisibleText);
	}
	
}
