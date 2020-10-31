package TestCases;

import java.io.IOException;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import BaseClass.BaseUi;
import Pages.CarLoanPage;
import Pages.HomeLoanPage;
import Pages.LoanCalculatorPage;
import Pages.MainPage;

public class Scenario3 extends BaseUi {
	CarLoanPage carLoan;
	HomeLoanPage homeLoan;
	LoanCalculatorPage loanCalculator;
	MainPage mainPage;

	@BeforeTest
	public void open() {
		logger = report.createTest("Loan Calculator testing");
		invokeBrowser("chrome");


		mainPage = openApplication(prop.getProperty("URL"));
		loanCalculator = mainPage.LoanCal();
	}

	@Test(dataProvider = "loancalculator")
	public void VerifyloancalculatorData(String loan_amount, String interest_rate, String loan_tenure,
			String fees_charges, String expected_result) throws IOException {

		clearField(prop.getProperty("Loan_Amount_3"));
		addValues(prop.getProperty("Loan_Amount_3"), loan_amount);
		clearField(prop.getProperty("Interest_Rate_3"));
		addValues(prop.getProperty("Interest_Rate_3"), interest_rate);
		clearField(prop.getProperty("Loan_Tenure_3"));
		addValues(prop.getProperty("Loan_Tenure_3"), loan_tenure);
		clearField(prop.getProperty("Fees_Charges"));
		addValues(prop.getProperty("Fees_Charges"), fees_charges);

		String actual_result = TotalPayment(prop.getProperty("TotalPayment_3"));
		AssertJUnit.assertEquals(expected_result, actual_result);

	}

	@AfterTest
	public void close() throws IOException {
		writeExcelData(System.getProperty("user.dir") + "\\Resources\\Repositiries\\loancalculator.xlsx",
				prop.getProperty("Label_Xpath3"), prop.getProperty("Row_Xpath3"), prop.getProperty("Col_Xpath3"));
	}

	@DataProvider(name = "loancalculator")
	public Object[][] loancalculatorData() {
		Object[][] arrayObject = getExcelData(
				System.getProperty("user.dir") + "\\Resources\\Repositiries\\Testing.xlsx", "Scenario3", 4, 6);
		return arrayObject;
	}
}
