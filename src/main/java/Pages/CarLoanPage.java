package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import BaseClass.BaseUi;

public class CarLoanPage extends BaseUi{
	
	@FindBy(xpath = "//li[@id='menu-item-3294']")
	public WebElement homeLoan;
	
	public HomeLoanPage clickHomeLoan() {
		homeLoan.click();
		return PageFactory.initElements(driver, HomeLoanPage.class);
	}
	
}
