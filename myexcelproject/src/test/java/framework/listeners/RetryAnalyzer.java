package framework.listeners;


import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer extends CustomListeners implements IRetryAnalyzer {
	//Counter to keep track of retry attempts
		int retryAttemptsCounter = 0;
		
		//The max limit to retry running of failed test cases
		//Set the value to the number of times we want to retry
		int maxRetryLimit = 1;

		//Method to attempt retries for failure tests
		public boolean retry(ITestResult result) {
			if (!result.isSuccess()) {
				if(retryAttemptsCounter < maxRetryLimit){
					retryAttemptsCounter++;
					return true;
				}
			}
			return false;
		}	
}

