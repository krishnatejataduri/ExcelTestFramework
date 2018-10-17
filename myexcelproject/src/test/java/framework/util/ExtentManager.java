package framework.util;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	
	private static ExtentReports extent;
	
	public static ExtentReports getExtentInstance(){
		if(extent==null){
			System.out.println("Inside Extent");
			extent = new ExtentReports(System.getProperty("user.dir")+"\\target\\extent\\extent.html");
			extent.loadConfig(new File(System.getProperty("user.dir")+"\\resources\\extentconfig\\ReportsConfig.xml"));
		}
		
		return extent;
	}

}
