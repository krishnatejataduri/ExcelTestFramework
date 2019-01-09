package com.org.excelproject.myexcelproject;

import java.util.HashMap;

import org.apache.commons.collections4.functors.NotNullPredicate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import framework.config.Settings;
import framework.util.ExcelUtil;

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
		gCurrentLocator = gRuntimeORTable.get(gCurrentObjectName);
		System.out.println(gCurrentLocator);
		return DriverContext.Driver.findElement(By.xpath(gCurrentLocator));
	}
	
	public void m_PerformAction(){
		gCurrentAction = gRunTimeTestScript.readCell("Action", gCurrentStepNumber);
		switch(gCurrentAction.toLowerCase()){
		
			case "click" :{
				try{
					gCurrentObject.click();
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
				break;
							
			}
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
			gCurrentObject = m_ObjectBuilder();
			m_PerformAction();
			
			
			
			gCurrentStepNumber++;
		}
		
	}
	
	
}
