package BaseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import Pages.MainPage;
import Utilities.DateUtil;
import Utilities.ExtentReportManager;

public class BaseUi {
	public WebDriver driver;
	public Properties prop;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;
	public DesiredCapabilities cap = null;

	/******************************************
	 * Invoking Browser and Reading Property file
	 *****************************************************/

	public void invokeBrowser(String browserName) {
		try {
			if (browserName.equalsIgnoreCase("edge")) {
				cap = DesiredCapabilities.edge();
			} else if (browserName.equalsIgnoreCase("chrome")) {
				cap = DesiredCapabilities.chrome();
			} else {
				cap = DesiredCapabilities.firefox();
			}
			reportPass("Browser invoked is : " + browserName);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		try {
			driver = new RemoteWebDriver(new URL("http://172.17.135.49:4444/wd/hub"), cap);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(
						System.getProperty("user.dir") + "\\Resources\\Repositiries\\Config.properties");
				prop.load(file);
			} catch (Exception e) {
				reportFail(e.getMessage());
			}
		}
	}

	/******************************************
	 * Opening URL
	 *****************************************************/

	public MainPage openApplication(String url) {
		try {
			driver.get(url);
			reportPass("website opened with url : " + url);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

		return PageFactory.initElements(driver, MainPage.class);
	}

	/******************************************
	 * Writing values in excel
	 *****************************************************/

	public void writeExcelData(String fileName, String Label_Xpath, String Row_Xpath, String Col_Xpath)
			throws IOException {

		int Row_count = driver.findElements(By.xpath(Row_Xpath)).size();
		int Col_count = driver.findElements(By.xpath(Col_Xpath)).size();

		String first_part = Row_Xpath + "[";
		String second_part = "]/td[";
		String third_part = "]";

		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Data");

		Row row = sheet.createRow(0);
		for (int i = 1; i <= Col_count; i++) {
			row.createCell(i - 1).setCellValue(driver.findElement(By.xpath(Label_Xpath + "[" + i + "]")).getText());
		}

		for (int i = 1; i <= Row_count; i++) {
			row = sheet.createRow(i);
			for (int j = 1; j <= Col_count; j++) {
				String final_xpath = first_part + i + second_part + j + third_part;

				String Table_data = driver.findElement(By.xpath(final_xpath)).getText();
				row.createCell(j - 1).setCellValue(Table_data);

			}

		}

		FileOutputStream fos = new FileOutputStream(new File(fileName));

		workbook.write(fos);

		fos.close();

		// print message
		System.err.println("");
		System.err.println("************************************************************");
		System.err.println("Data has been stored");
		System.err.println("************************************************************");

	}

	/******************************************
	 * Functions for HTML report
	 *****************************************************/

	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	public void takeScreenShot() {
		TakesScreenshot ss = (TakesScreenshot) driver;
		File src = ss.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + DateUtil.getTimeStamp() + ".png");

		try {
			FileUtils.copyFile(src, dest);
			logger.addScreenCaptureFromPath(System.getProperty("user.dir") + DateUtil.getTimeStamp() + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShot();
		Assert.fail();
	}

	/******************************************
	 * Reading values from excel
	 *****************************************************/

	public String[][] getExcelData(String fileName, String sheetName, int totalNoOfRows, int totalNoOfCols) {
		String[][] arrayExcelData = null;
		try {
			FileInputStream fs = new FileInputStream(fileName);
			@SuppressWarnings("resource")
			XSSFWorkbook wb = new XSSFWorkbook(fs);
			XSSFSheet sh = wb.getSheet(sheetName);

			arrayExcelData = new String[totalNoOfRows - 1][totalNoOfCols - 1];

			for (int i = 1; i < totalNoOfRows; i++) {

				for (int j = 1; j < totalNoOfCols; j++) {
					arrayExcelData[i - 1][j - 1] = String.valueOf(sh.getRow(i).getCell(j));

				}

			}
			fs.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace();
		}

		return arrayExcelData;
	}

	/******************************************
	 * Global function for explicit wait
	 *****************************************************/

	public void ExplicitWait(String LocatorValue) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		if (LocatorValue.endsWith("_Id")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		} else if (LocatorValue.endsWith("_XPath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		} else if (LocatorValue.endsWith("_ClassName")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(LocatorValue)));
		} else if (LocatorValue.endsWith("_TagName")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(LocatorValue)));
		} else if (LocatorValue.endsWith("_LinkText")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(LocatorValue)));
		}

	}

	/******************************************
	 * Global function for clicking web element
	 *****************************************************/

	public void clickElement(String LocatorValue) {

		if (LocatorValue.endsWith("_Id")) {
			driver.findElement(By.id(LocatorValue)).click();
		} else if (LocatorValue.endsWith("_XPath")) {
			driver.findElement(By.xpath(LocatorValue)).click();
		} else if (LocatorValue.endsWith("_ClassName")) {
			driver.findElement(By.className(LocatorValue)).click();
		} else if (LocatorValue.endsWith("_TagName")) {
			driver.findElement(By.tagName(LocatorValue)).click();
		} else if (LocatorValue.endsWith("_LinkText")) {
			driver.findElement(By.linkText(LocatorValue)).click();
		}

	}

	/******************************************
	 * Global function for clearing web element
	 *****************************************************/

	public void clearField(String LocatorValue) {
		WebElement element = driver.findElement(By.xpath(LocatorValue));
		element.sendKeys(Keys.CONTROL + "a");
		element.sendKeys(Keys.DELETE);

	}

	/******************************************
	 * Global function for sending value in web element
	 *****************************************************/

	public void addValues(String LocatorValue, String value) {
		try {
			driver.findElement(By.xpath(LocatorValue)).sendKeys(value);
			driver.findElement(By.xpath(LocatorValue)).sendKeys(Keys.ENTER);

			reportPass("Value entered is : " + value);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	public void LoanEMI(String LocatorValue) {
		try {
			WebElement element = driver.findElement(By.xpath(LocatorValue));
			String EMI = element.getText();
			System.out.println("EMI amount is : " + EMI);
			reportPass("Loan EMI is : " + EMI);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public void Interest(String LocatorValue) {
		try {
			WebElement element = driver.findElement(By.xpath(LocatorValue));
			String Interest = element.getText();
			System.out.println("Interest payable is : " + Interest);
			reportPass("Interest amount is : " + Interest);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	public String TotalPayment(String LocatorValue) {
		String Payment = null;
		try {
			WebElement element = driver.findElement(By.xpath(LocatorValue));
			Payment = element.getText();
			System.out.println("Total amount is : " + Payment);
			reportPass("Total amount will be : " + Payment);

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return Payment;

	}

	@AfterSuite
	public void flushReports() {
		report.flush();
		driver.close();
	}

}
