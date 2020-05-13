package logowanie;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.util.logging.Level;
import java.util.logging.Logger;

public class logowanie {
	public ExtentHtmlReporter htmlReport;
	public ExtentReports extent;
	public ExtentTest testPositive;
	public ExtentTest testNegative;
	public ExtentTest testSkipped;
	public String driverPath;
	public WebDriver driver;
	public testLogowanie pageObject;
		@BeforeTest
		public void startTest()
		{
			
			driverPath = "D:\\Prace\\chromedriver_win32 (1)\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", driverPath);
			driver = new ChromeDriver();
	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	        driver.get("https://lowcygier.pl/forum/ucp.php?mode=login");
	        pageObject = new testLogowanie(driver);
	        
			
			htmlReport = new ExtentHtmlReporter("test-output//testLowcy.html");
			htmlReport.config().setDocumentTitle("Raport logowania lowcygier");
			htmlReport.config().setReportName("Automatic reports generation");
			htmlReport.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReport.config().setTheme(Theme.STANDARD);
			
			extent = new ExtentReports();
			extent.attachReporter(htmlReport);
			extent.setSystemInfo("UserName", "Wojciech Nowak");
			
		}

		@AfterMethod
		public void getResult(ITestResult result) {
			Logger logger = Logger.getLogger(logowanie.class.getName());
			if (result.getStatus() == ITestResult.SUCCESS) {
				logger.log(Status.PASS, "Affirmative \""+ result.getName()+ "\"");
			} 
			else if (result.getStatus() == ITestResult.FAILURE) {
				logger.log(Status.FAIL, "Failed \""+result.getName()+"\" Reason:"+ result.getThrowable());
			}
			else if (result.getStatus() == ITestResult.SKIP) {
				logger.log(Status.SKIP, "Skipped test \""+ result.getName());
			}
		}
		
		/*@AfterMethod
		public void afterFail()
		{
			File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				FileWriter writer = new FileWriter(srcFile);
				writer.write("File is writing");
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		@AfterTest
		public void endTest()
		{
			testPositive.log(Status.PASS, "Passed");
			testNegative.log(Status.FAIL, "Failed");
			testSkipped.log(Status.SKIP, "Skipped");
			extent.flush();
		}
		
		@Test
		public void TestSkip()
		{
			testSkipped = extent.createTest("test pomienięty");			
			pageObject.login();
			
			if(pageObject.getResult().contains("Nie można zalogować się bez podania hasła."))
			{
				throw new SkipException("Testing skip.");
			}
			else {
				Assert.assertTrue(false);
			}
		}
		
		@Test
		public void TestPositive()
		{
			testPositive = extent.createTest("test pozytywny");
			
			String correctUsername = "testowy123qwe";
		    String correctPassword = "test010203";
		 
		    pageObject.enterUsername(correctUsername);
		    pageObject.enterPassword(correctPassword);
		    pageObject.login();
		 
		    Assert.assertTrue(true); 
		}

		@Test
		public void TestNegative()
		{
			testNegative = extent.createTest("test negatywny");
			
			String correctUsername = "123qwe";
		    String incorrectPassword = "123qwe";
		    
		   
		    pageObject.enterUsername(correctUsername);
		    pageObject.enterPassword(incorrectPassword);
		    pageObject.login();
		 
		    Assert.assertTrue(false);
		}
		
}