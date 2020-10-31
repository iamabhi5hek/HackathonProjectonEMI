package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import BaseClass.BaseUi;

public class MainPage extends BaseUi{
	
	
	@FindBy(xpath = "//li[@id='car-loan']") 
	public WebElement carLoanBtn;
	
	public CarLoanPage clickCarLoan() {
		carLoanBtn.click();
		
		return PageFactory.initElements(driver, CarLoanPage.class);
	}
	
	@FindBy(xpath = "//li[@id='menu-item-3294']")
	public WebElement homeLoan;
	
	public HomeLoanPage clickHomeLoan() {
		homeLoan.click();
		return PageFactory.initElements(driver, HomeLoanPage.class);
	}
	
	
	@FindBy(xpath = "//a[@id='menu-item-dropdown-2696']")
	public WebElement dropDown;
	
	@FindBy(xpath = "//header/div[1]/nav[1]/div[2]/div[1]/ul[1]/li[1]/ul[1]/li[2]/a[1]")
	public WebElement loanCalculator;
	
	public LoanCalculatorPage LoanCal() {
		
		dropDown.click();

		
		loanCalculator.click();
		
		return PageFactory.initElements(driver, LoanCalculatorPage.class);
	}
}
