package framework.util;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.org.excelproject.myexcelproject.DriverContext;

public class WaitUtil {

	private static void until(Function<WebDriver, Boolean> waitCondition, Long timeoutInSeconds){
		WebDriverWait webDriverWait = new WebDriverWait(DriverContext.Driver, timeoutInSeconds);
		webDriverWait.withTimeout(timeoutInSeconds, TimeUnit.SECONDS);
		try{
			webDriverWait.until(waitCondition);
		}catch (Exception e){
			System.out.println(e.getMessage());
		}          
	}
	
	public static void untilJQueryIsDone(Long timeoutInSeconds) {
		until((d)->
		{Boolean jQueryCallDone = (Boolean)((JavascriptExecutor)DriverContext.Driver).executeScript("return jQuery.active==0");
		 return jQueryCallDone;
		},timeoutInSeconds);
	}
	
	public static void untilPageLoad(Long timeoutInSeconds) {
		until((d)->{
		Boolean isPageLoadComplete = (Boolean) ((JavascriptExecutor)DriverContext.Driver).executeScript("return document.readyState==\"complete\"");
		return isPageLoadComplete;}, timeoutInSeconds);
	}


}
