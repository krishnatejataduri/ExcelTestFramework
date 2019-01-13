package framework.listeners;

import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.org.excelproject.myexcelproject.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

import framework.util.GenericUtil;


public class CustomListeners extends BaseTest implements ITestListener{

	public void onFinish(ISuite arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}
	

	public void onTestFailure(ITestResult arg0) {
		System.out.println("Inside Failure");
		GenericUtil.captureScreenshot(arg0.getName().toUpperCase());
		test.log(LogStatus.FAIL, "TEST: "+arg0.getName().toUpperCase()+" - FAILED");
		test.log(LogStatus.INFO, test.addScreenCapture(GenericUtil.ScreenshotPath));
		rep.endTest(test);
		rep.flush();
	}

	public void onTestSkipped(ITestResult arg0) {
		test.log(LogStatus.SKIP, "Test skipped due to a failure. Check for the retry result");
		
	}

	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		String testName = arg0.getInstance().getClass().getName().substring(10).toUpperCase();
		test = rep.startTest(testName);
	}

	public void onTestSuccess(ITestResult arg0) {
		test.log(LogStatus.PASS, "TEST: "+arg0.getName().toUpperCase()+" - PASSED");
		rep.endTest(test);
		rep.flush();
	}

}
