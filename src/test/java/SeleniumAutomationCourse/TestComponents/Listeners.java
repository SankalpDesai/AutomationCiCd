package SeleniumAutomationCourse.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import SeleniumAutomationCourse.resourses.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener{

	ExtentTest test;
	ExtentReports extent= ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	
	public void onTestStart(ITestResult result) {
		test=extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test); //unique thread id (ErrorValidationTest) -> test
	}
	
	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed"); //you can write or skip this part
	}
	
	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable()); //for printing error message
		
		//code for sending a driver to getScreenshot() method
		try {
			driver=(WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// screenshot attach to report
	
		String filePath=null;
		try {
			filePath=getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		extentTest.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		
	}
	
	public void onTestSkipped(ITestResult result) {
		//TODO Auto-generated method stub
	}
	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		//TODO Auto-generated method stub
	}
	
	public void onStart(ITestContext context) {
		//TODO Auto-generated method stub
	}
	
	public void onFinish(ITestContext context) {
		//TODO Auto-generated method stub //This is the last method which will run
		extent.flush();
	}
}
