package com.org.excelproject.myexcelproject;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;

import framework.config.Settings;
import framework.util.AppUtil;
import framework.util.DebugUtil;
import framework.util.ExcelUtil;

public class DriverScript extends BaseTest {
	private String gScreenName = null;
	private String gTestName;
	private int gCurrentStepNumber = 1;
	private int gLastStepNumber;
	private String gTestScriptPath = null;
	private String gObjectRepoPath = null;
	private ExcelUtil gRunTimeTestScript = null;
	private String gCurrentScreen = null;
	private ExcelUtil gRunTimeOR = null;
	private WebElement gCurrentObject = null;
	private String gCurrentLocator = null;
	private String gCurrentObjectName = null;
	private HashMap<String, String> gRuntimeORTable = new HashMap<>();
	private String gCurrentAction = null;
	private String gCurrentInputVal = null;
	private String gCurrentMethodName = null;
	private String gStepExecutionFlag = null;

	public static DriverScript getInstance() {
		return new DriverScript();
	}

	// Method Name: m_importTestData
	// Author: Krishna
	// Creation Date: 10/5/2018
	// *****Accesses the excel file with the file name same as the TestName****
	private String m_getScreenName() {
		return gRunTimeTestScript.readCell("Screen", gCurrentStepNumber);
	}

	private String m_getObjectName() {
		return gRunTimeTestScript.readCell("Object", gCurrentStepNumber);
	}

	private WebElement m_ObjectBuilder() throws Exception {
		try {
			gCurrentLocator = gRuntimeORTable.get(gCurrentObjectName);
			System.out.println("Locator: " + gCurrentLocator);
			return DriverContext.Driver.findElement(By.xpath(gCurrentLocator));
		} catch (Exception e) {
			switch (e.getClass().getSimpleName()) {
			case "NoSuchElementException": {
				test.log(LogStatus.FAIL,
						"Unable to locate the Object: " + gCurrentObjectName + " using the locator provided.");
			}
			}
			return null;
		}
	}

	public void m_PerformAction() throws Exception {
		gCurrentAction = gRunTimeTestScript.readCell("Action", gCurrentStepNumber);
		System.out.println("Action: " + gCurrentAction);
		System.out.println(gCurrentStepNumber);
		switch (gCurrentAction.toLowerCase()) {
		case "click": {
			gCurrentObject.click();
			break;
		}
		case "enter": {
			gCurrentInputVal = gRunTimeTestScript.readCell("UserInput", gCurrentStepNumber);
			gCurrentObject.sendKeys(gCurrentInputVal);
			gCurrentInputVal = null;
			break;
		}
		case "select": {
			gCurrentInputVal = gRunTimeTestScript.readCell("UserInput", gCurrentStepNumber);
			Select select = new Select(gCurrentObject);
			select.selectByVisibleText(gCurrentInputVal);
			gCurrentInputVal = null;
			break;
		}
		case "alert": {
			Alert alt = null;
			alt = DriverContext.Driver.switchTo().alert();
			alt.accept();
			// DriverContext.Driver.switchTo().alert().accept();
			test.log(LogStatus.INFO, "Accepted the alert: " + alt.getText());
			break;
		}
		case "function": {
			gCurrentInputVal = gRunTimeTestScript.readCell("UserInput", gCurrentStepNumber);
			gCurrentMethodName = gRunTimeTestScript.readCell("FunctionName", gCurrentStepNumber);
			if (!(gCurrentInputVal.equals("NA"))) {
				Method method = AppUtil.class.getMethod(gCurrentMethodName, String.class);
				method.invoke(new AppUtil(), gCurrentInputVal);
			} else {
				Method method = AppUtil.class.getMethod(gCurrentMethodName);
				method.invoke(new AppUtil());
			}
			break;
		}
		}
	}

	// Method Name: m_mainDriver
	// Author: Krishna
	// Creation Date: 10/5/2018
	// Updated on: 10/7/2018
	// *****Main Driver script that is called while executing the tests****
	public void m_mainDriver(String TestScriptName) {
		gTestName = TestScriptName;
		gTestScriptPath = Settings.TCExcelPath + "/" + gTestName + ".xlsx";
		gRunTimeTestScript = ExcelUtil.getInstance(gTestScriptPath, gTestName);
		gLastStepNumber = gRunTimeTestScript.getRowCount();
		System.out.println(gLastStepNumber);
		gObjectRepoPath = Settings.ORPath;
		loop: while (gCurrentStepNumber <= gLastStepNumber) {
			try {
				gStepExecutionFlag = gRunTimeTestScript.readCell("ExecutionFlag", gCurrentStepNumber);
				if (gStepExecutionFlag.equalsIgnoreCase("Y")) {
					System.out.println(m_getScreenName());
					if (m_getScreenName().trim() != "" && m_getScreenName() != null) {
						if (gCurrentScreen == null) {
							gCurrentScreen = m_getScreenName();
							gRunTimeOR = ExcelUtil.getInstance(gObjectRepoPath, gCurrentScreen);
							gRuntimeORTable = gRunTimeOR.loadRunTimeORTable();
						} else if (!(gCurrentScreen.equalsIgnoreCase(m_getScreenName()))) {
							gCurrentScreen = m_getScreenName();
							gRunTimeOR = ExcelUtil.getInstance(gObjectRepoPath, gCurrentScreen);
							gRuntimeORTable = gRunTimeOR.loadRunTimeORTable();
						}
					}
					gCurrentObjectName = m_getObjectName();
					System.out.println(gCurrentObjectName);
					if (!(gCurrentObjectName.equalsIgnoreCase("NA"))) {
						gCurrentObject = m_ObjectBuilder();
					}
					m_PerformAction();
				}
				gCurrentStepNumber++;
			} catch (Exception e) {
				String exceptionName = e.getClass().getSimpleName().toUpperCase();
				e.printStackTrace();
				int selectedCode = DebugUtil.showMessage(exceptionName, gCurrentObjectName, gCurrentAction);
				System.out.println("Debug Code is!!!:    " + selectedCode);
				switch (selectedCode) {
				case 0: {
					System.out.println("inside case 0");
					continue loop;
				}
				case 1: {
					// Fail the step and increment
					System.out.println("inside case 1");
					gCurrentStepNumber++;
					continue loop;
				}
				case 2: {
					System.out.println("inside case 2");
					break loop;
				}
				}
			}
		}
	}
}