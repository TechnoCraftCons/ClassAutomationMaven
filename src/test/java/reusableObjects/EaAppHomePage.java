package reusableObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class EaAppHomePage {
	
	WebDriver driver;
	
	public EaAppHomePage(WebDriver driver) {
		
		this.driver = driver;
	}
	
	By LoginBtn = By.xpath("//a[@id='loginLink']");
	
	public WebElement LoginClick() {
		
	  return driver.findElement(LoginBtn);
	}

}
