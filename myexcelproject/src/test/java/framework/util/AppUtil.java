package framework.util;

import org.openqa.selenium.By;

import com.org.excelproject.myexcelproject.BaseTest;
import com.org.excelproject.myexcelproject.DriverContext;
import com.relevantcodes.extentreports.LogStatus;

public class AppUtil extends BaseTest {

	public void m_DeleteCustomer(String FirstName) {
		try {
			String xp = "//table//td[.='"+FirstName+"']//ancestor::tr//button[.='Delete']";
			DriverContext.Driver.findElement(By.xpath(xp)).click();
			test.log(LogStatus.INFO, "Deleted the customer: "+FirstName);
		}
		catch(Exception e) {
			test.log(LogStatus.FAIL, "Unable to find customer with the firstname provided: "+FirstName+". Exception: "+e.getClass().getName());
		}
	}
}
