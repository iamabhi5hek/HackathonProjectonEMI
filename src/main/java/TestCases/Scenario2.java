package TestCases;

import java.io.IOException;

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

public class Scenario2 extends BaseUi {
	CarLoanPage carLoan;
	HomeLoanPage homeLoan;
	LoanCalculatorPage loanCalculator;
	MainPage mainPage;

	@BeforeTest
	public void open() {
		logger = report.createTest("Home Loan EMI testing_1");
		invokeBrowser("chrome");


		mainPage = openApplication(prop.getProperty("URL"));
		homeLoan = mainPage.clickHomeLoan();
	}

	@Test(dataProvider = "homeloanemi")
	public void VerifyhomeloanemiData(String home_value, String down_payment, String loan_insurance,
			String interest_rate, String loan_tenure, String one_time_expenses, String property_taxes,
			String home_insurance, String maintenance_expenses, String expected_result) throws IOException {

		clearField(prop.getProperty("Home_Value"));
		addValues(prop.getProperty("Home_Value"), home_value);
		clearField(prop.getProperty("Down_Payment"));
		addValues(prop.getProperty("Down_Payment"), down_payment);
		clearField(prop.getProperty("Loan_Insurance"));
		addValues(prop.getProperty("Loan_Insurance"), loan_insurance);
		clearField(prop.getProperty("Interest_Rate"));
		addValues(prop.getProperty("Interest_Rate"), interest_rate);
		clearField(prop.getProperty("Loan_Tenure"));
		addValues(prop.getProperty("Loan_Tenure"), loan_tenure);
		clearField(prop.getProperty("One_Time_Expenses"));
		addValues(prop.getProperty("One_Time_Expenses"), one_time_expenses);
		clearField(prop.getProperty("Property_Taxes"));
		addValues(prop.getProperty("Property_Taxes"), property_taxes);
		clearField(prop.getProperty("Home_Insurance"));
		addValues(prop.getProperty("Home_Insurance"), home_insurance);
		clearField(prop.getProperty("Maintenance_Expenses"));
		addValues(prop.getProperty("Maintenance_Expenses"), maintenance_expenses);

		String actual_result = TotalPayment(prop.getProperty("TotalPayment_2"));
		Assert.assertEquals(expected_result, actual_result);

	}

	@AfterTest
	public void close() throws IOException {
		writeExcelData(System.getProperty("user.dir") + "\\Resources\\Repositiries\\homeloanemi.xlsx",
				prop.getProperty("Label_Xpath2"), prop.getProperty("Row_Xpath2"), prop.getProperty("Col_Xpath2"));
	}

	@DataProvider(name = "homeloanemi")
	public Object[][] homeloanemiData() {
		Object[][] arrayObject = getExcelData(
				System.getProperty("user.dir") + "\\Resources\\Repositiries\\Testing.xlsx", "Scenario2", 11, 11);
		return arrayObject;
	}
}
