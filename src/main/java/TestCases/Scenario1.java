package TestCases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseClass.BaseUi;
import Pages.CarLoanPage;
import Pages.HomeLoanPage;
import Pages.LoanCalculatorPage;
import Pages.MainPage;

public class Scenario1 extends BaseUi {

	CarLoanPage carLoan;
	HomeLoanPage homeLoan;
	LoanCalculatorPage loanCalculator;
	MainPage mainPage;

	@BeforeTest
	public void open() {
		logger = report.createTest("Car Loan Testing_1");
		invokeBrowser("chrome");

		mainPage = openApplication(prop.getProperty("URL"));
		carLoan = mainPage.clickCarLoan();
	}

	@Test(dataProvider = "carloan")
	public void VerifycarloanData(String loan_amount, String interest_rate, String tenure, String loan_tenure,
			String expected_result) throws IOException {

		clearField(prop.getProperty("LoanAmount_XPath"));
		addValues(prop.getProperty("LoanAmount_XPath"), loan_amount);

		clearField(prop.getProperty("Rate_XPath"));
		addValues(prop.getProperty("Rate_XPath"), interest_rate);

		if (tenure.equals("M")) {
			driver.findElement(By.xpath(
					"/html/body/div[1]/div/main/article/div[3]/div/div[1]/div[1]/div[2]/form/div/div[7]/div/div/div/div/div/label[2]"))
					.click();

		}

		if (tenure.equals("Y")) {
			driver.findElement(By.xpath(
					"/html/body/div[1]/div/main/article/div[3]/div/div[1]/div[1]/div[2]/form/div/div[7]/div/div/div/div/div/label[1]"))
					.click();

		}

		clearField(prop.getProperty("Tenure_XPath"));
		addValues(prop.getProperty("Tenure_XPath"), loan_tenure);

		String actual_result = TotalPayment(prop.getProperty("TotalPayment"));
		if (actual_result.equals("0")) {
			actual_result = "0.0";
		}
		Assert.assertEquals(expected_result, actual_result);
	}

	@AfterTest
	public void close() throws IOException {
		writeExcelData(System.getProperty("user.dir") + "\\Resources\\Repositiries\\carloan.xlsx",
				prop.getProperty("Label_Xpath1"), prop.getProperty("Row_Xpath1"), prop.getProperty("Col_Xpath1"));
	}

	@DataProvider(name = "carloan")
	public Object[][] carloanData() {
		Object[][] arrayObject = getExcelData(
				System.getProperty("user.dir") + "\\Resources\\Repositiries\\Testing.xlsx", "Scenario1", 9, 6);
		return arrayObject;
	}
}
