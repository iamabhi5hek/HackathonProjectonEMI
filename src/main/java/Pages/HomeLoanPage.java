package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import BaseClass.BaseUi;

public class HomeLoanPage extends BaseUi{
	
	@FindBy(linkText = "Calculators")
	public WebElement dropDown;
	
	@FindBy(linkText = "Loan Calculator")
	public WebElement loanCalculator;
	
	public LoanCalculatorPage LoanCal() {
		
		dropDown.click();
		
		WebDriverWait wait=new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Loan Calculator")));
		
		return PageFactory.initElements(driver, LoanCalculatorPage.class);
	}
}
