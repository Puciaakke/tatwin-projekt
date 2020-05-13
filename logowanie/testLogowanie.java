package logowanie;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class testLogowanie {
	WebDriver driver;
	
	@FindBy(xpath = "//input[@id='password']")
	WebElement passwordInput;
	
	@FindBy(xpath = "//input[@name='login']")	//input[@name='login']
	WebElement loginButton;

	@FindBy(xpath = "//input[@id='username']")  //input[@id='username']
	WebElement usernameInput;

	@FindBy(xpath = "//div[@class='error']")
	WebElement resultText;
	
	
	public testLogowanie(WebDriver driver){
		this.driver = driver;
	    PageFactory.initElements(driver, this);
	}
	
	public void enterUsername(String username){
		usernameInput.sendKeys(username);
	}
	 
	public void enterPassword(String password){
		passwordInput.sendKeys(password);
	}
	 
	public void login(){
		loginButton.click();
	}
	
	public String getResult(){
		return resultText.getText();
	}
	
}