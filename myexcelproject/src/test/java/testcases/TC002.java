package testcases;

import org.testng.annotations.Test;

import com.org.excelproject.myexcelproject.BaseTest;
import com.org.excelproject.myexcelproject.DriverScript;

public class TC002 extends BaseTest {
	private String TestScriptName = this.getClass().getSimpleName();
	
	@Test
	public void runTest(){
		DriverScript.getInstance().m_mainDriver(TestScriptName);
	}

}
