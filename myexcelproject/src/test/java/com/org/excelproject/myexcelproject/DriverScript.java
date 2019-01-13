package com.org.excelproject.myexcelproject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.collections4.functors.NotNullPredicate;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import framework.config.Settings;
import framework.util.AppUtil;
import framework.util.ExcelUtil;
import rough.Catch;

public class DriverScript extends BaseTest {

	private String gScreenName=null;
	private String gTestName;
	private int gCurrentStepNumber = 1;
	private int gLastStepNumber;
	private String gTestScriptPath  = null;
	private String gObjectRepoPath = null;
	private ExcelUtil gRunTimeTestScript = null;
	private String gCurrentScreen = null;
	private ExcelUtil gRunTimeOR = null;
	private WebElement gCurrentObject = null;
	private String gCurrentLocator = null;
	private String gCurrentObjectName = null;
	private HashMap<String,String> gRuntimeORTable = new HashMap<>();
	private String gCurrentAction=null;
	private String gCurrentInputVal = null;
	private String gCurrentMethodName = null;
	
	public static DriverScript getInstance(){
		return new DriverScript();
		
	}
	
	
	//Method Name: m_importTestData
	//Author: Krishna
	//Creation Date: 10/5/2018
	//*****Accesses the excel file with the file name same as the TestName****
	private String m_getScreenName(){
		return gRunTimeTestScript.readCell("Screen", gCurrentStepNumber);
	}
	
	private String m_getObjectName(){
		return gRunTimeTestScript.readCell("Object", gCurrentStepNumber);
	}
	
	
	private WebElement m_ObjectBuilder(){
		try {
			gCurrentLocator = gRuntimeORTable.get(gCurrentObjectName);
			System.out.println("Locator: "+gCurrentLocator);
			return DriverContext.Driver.findElement(By.xpath(gCurrentLocator));
		}
		catch(Exception e) {
			switch(e.getClass().getSimpleName()) {
				case "NoSuchElementException":{
					
					test.log(LogStatus.FAIL, "Unable to locate the Object: "+gCurrentObjectName+" using the locator provided.");
					
				}
			}
			return null;							
		}
				
	}
	
	
	public void m_PerformAction(){
		if(gCurrentObject!=null) {
			gCurrentAction = gRunTimeTestScript.readCell("Action", gCurrentStepNumber);
			System.out.println("Action: "+gCurrentAction);
			System.out.println(gCurrentStepNumber);
			switch(gCurrentAction.toLowerCase()){
			
				case "click" :{
					try{
						if(gCurrentObject.isDisplayed()&&gCurrentObject.isEnabled()) {
							gCurrentObject.click();
						}
						else {
							test.log(LogStatus.FAIL, "Object: "+gCurrentObjectName+" is either not displayed or enabled");
						}
						
					}
					catch(Exception e){
						test.log(LogStatus.FAIL, "Exception occured while trying to click on: "+gCurrentObjectName+"."+e.getMessage());
					}
					break;
				}
				
				case "enter" :{
					gCurrentInputVal = gRunTimeTestScript.readCell("UserInput", gCurrentStepNumber);
					if(gCurrentObject.isDisplayed()&&gCurrentObject.isEnabled()){
					gCurrentObject.sendKeys(gCurrentInputVal);
					}
					else{
						test.log(LogStatus.FAIL, "The object: "+gCurrentObjectName+" is either not present or enabled.");
					}
					gCurrentInputVal = null;
					break;
				}
				
				case "select" :{
					gCurrentInputVal = gRunTimeTestScript.readCell("UserInput", gCurrentStepNumber);
					try{
						Select select = new Select(gCurrentObject);
						select.selectByVisibleText(gCurrentInputVal);
					}
					catch (Exception e){
						test.log(LogStatus.FAIL, "Unable to find the value: "+gCurrentInputVal+" in the dropdown: "+gCurrentObjectName);
					}
					gCurrentInputVal = null;
					break;				
				}
				
				case "alert" :{
					
					try {
						Alert alt=null;
						alt = DriverContext.Driver.switchTo().alert();
						alt.accept();
						//DriverContext.Driver.switchTo().alert().accept();
						test.log(LogStatus.INFO, "Accepted the alert: "+alt.getText());
					}
					catch(Exception e) {
						test.log(LogStatus.FAIL, "Encountered the exception while trying to accept alert. "+e.getClass().getName());
					}
					break;
				}
				
				case "function" :{
					gCurrentInputVal = gRunTimeTestScript.readCell("UserInput", gCurrentStepNumber);
					gCurrentMethodName = gRunTimeTestScript.readCell("FunctionName", gCurrentStepNumber);
					if(!(gCurrentInputVal.equals("NA"))) {
						try {
							Method method = AppUtil.class.getMethod(gCurrentMethodName,String.class);
							try {
								method.invoke(new AppUtil(), gCurrentInputVal);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (NoSuchMethodException | SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						try {
							Method method = AppUtil.class.getMethod(gCurrentMethodName);
							try {
								method.invoke(new AppUtil());
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (NoSuchMethodException | SecurityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				}
			}
		}
		else {
			test.log(LogStatus.FAIL, "Unable to perform the action on the object: "+gCurrentObjectName+" as it could not be located on the application");
		}
	}
	
	
	
	
	//Method Name: m_mainDriver
	//Author: Krishna
	//Creation Date: 10/5/2018
	//Updated on: 10/7/2018
	//*****Main Driver script that is called while executing the tests****
	
	public void m_mainDriver(String TestScriptName){
		gTestName = TestScriptName;
		gTestScriptPath = Settings.TCExcelPath+"/"+gTestName+".xlsx";
		gRunTimeTestScript = ExcelUtil.getInstance(gTestScriptPath, gTestName);
		gLastStepNumber = gRunTimeTestScript.getRowCount();
		System.out.println(gLastStepNumber);
		gObjectRepoPath = Settings.ORPath;
		
		
		while(gCurrentStepNumber<=gLastStepNumber){
		
			System.out.println(m_getScreenName());
			if(m_getScreenName().trim()!="" && m_getScreenName()!=null){
				if(gCurrentScreen==null){
					gCurrentScreen = m_getScreenName();
					gRunTimeOR = ExcelUtil.getInstance(gObjectRepoPath, gCurrentScreen);
					gRuntimeORTable = gRunTimeOR.loadRunTimeORTable();
				}
				else if(!(gCurrentScreen.equalsIgnoreCase(m_getScreenName()))){
					gCurrentScreen = m_getScreenName();
					gRunTimeOR = ExcelUtil.getInstance(gObjectRepoPath, gCurrentScreen);
					gRuntimeORTable = gRunTimeOR.loadRunTimeORTable();
				}
			}
			gCurrentObjectName = m_getObjectName();
			System.out.println(gCurrentObjectName);
			
			if(!(gCurrentObjectName.equalsIgnoreCase("NA"))) {
				gCurrentObject = m_ObjectBuilder();
			}
						
			m_PerformAction();			
			
			gCurrentStepNumber++;

		}
	}
}
