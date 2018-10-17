package framework.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.org.excelproject.myexcelproject.DriverContext;



public class GenericUtil {
	
	public static String ScreenshotName;
	public static String ScreenshotPath;
	
	public static void captureScreenshot(String testName) {
		Date d = new Date();
		ScreenshotName = testName+"_"+d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
		ScreenshotPath = System.getProperty("user.dir")+"\\target\\extent\\screenshots\\"+ScreenshotName;
		File scrShot = ((TakesScreenshot)DriverContext.Driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrShot, new File(System.getProperty("user.dir")+"\\target\\extent\\screenshots\\"+ScreenshotName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
