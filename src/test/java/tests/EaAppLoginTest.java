package tests;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import reusableObjects.EaAppHomePage;
import reusableObjects.EaAppLoginPage;

public class EaAppLoginTest {
	
	WebDriver driver = null;
	Properties prop = null;
	FileInputStream fis= null;
	
	@BeforeTest
	public void StartUp() throws Throwable   {
		
		//System.setProperty("webdriver.chrome.driver", "C:\\Users\\nebiy\\Desktop\\ChromeExc\\chromedriver.exe");
		///System.setProperty("webdriver.gecko.driver", "C:\\Users\\nebiy\\Desktop\\FirefoxExc\\geckodriver.exe");
		WebDriverManager.chromedriver().version("77.0.3865.40").setup();
		prop = new Properties();
		fis = new FileInputStream("C:\\Users\\nebiy\\Desktop\\eclipse-workspace\\PomFramework\\src\\data.properties");
		prop.load(fis);
		if(prop.getProperty("browser").contains("chrome")) {
			driver = new ChromeDriver();
		}
		else if(prop.getProperty("browser").contains("firefox")) {
			driver = new FirefoxDriver();
		}
		
		driver.get(prop.getProperty("url"));	
		driver.manage().window().maximize();
	}
	
	@DataProvider
	public Object[][] GetData() {
		Object[][] data = new Object[3][2];
		
		data[0][0] = "FisrtUserName";
		data[0][1] = "FirstPassword";
		
		data[1][0]= "SeconedUserName";
		data[1][1]="SecondPassword";
		
		data[2][0]="ThirdUserName";
		data[2][1]="ThirdPassword";
		
		return data;
		
	}
	
	@Test
	public void LoginClick() throws IOException {
		
		EaAppHomePage hp = new EaAppHomePage(driver);
		hp.LoginClick().click();
		String url = driver.getCurrentUrl();
		Assert.assertEquals(url, "http://eaapp.somee.com/Account/Login");
	}
	
	@Test(dependsOnMethods="LoginClick",dataProvider="GetData")
	public void InvalidLoginEA(String un, String pwd) throws InterruptedException {
		
		EaAppLoginPage lp = new EaAppLoginPage(driver);
		lp.UserName().sendKeys(un);
		lp.PassWord().sendKeys(pwd);
		lp.RememberMe().click();
		boolean IsSelected = lp.RememberMe().isSelected();
		lp.LoginButton().click();
		lp.UserName().clear();
		lp.PassWord().clear();
		Assert.assertEquals(IsSelected, true);
		String InvalidLoginAttempt =lp.InvalidLoginMessage().getText();
		Assert.assertEquals(InvalidLoginAttempt, "Invalid login attempt.");
		if(IsSelected = true) {
			lp.RememberMe().click();
		}
	}

}
