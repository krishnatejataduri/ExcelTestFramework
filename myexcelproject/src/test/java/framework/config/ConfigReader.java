package framework.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	public static void populateSettings() throws FileNotFoundException, IOException{
		Properties prop = new Properties();
		prop.load(new FileInputStream(".\\src\\test\\java\\framework\\config\\GlobalConfig.properties"));
		Settings.URL = prop.getProperty("URL");
		Settings.Browser = prop.getProperty("Browser");
		Settings.TCExcelPath = prop.getProperty("TCExcelPath");
		Settings.ORPath = prop.getProperty("ORPath");
	}
	
}
