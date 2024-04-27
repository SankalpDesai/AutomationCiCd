package SeleniumAutomationCourse.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import SeleniumAutomationCourse.TestComponents.BaseTest;
import SeleniumAutomationCourse.pageObjects.CartPage;
import SeleniumAutomationCourse.pageObjects.CheckoutPage;
import SeleniumAutomationCourse.pageObjects.ConfirmationPage;
import SeleniumAutomationCourse.pageObjects.LandingPage;
import SeleniumAutomationCourse.pageObjects.OrderPage;
import SeleniumAutomationCourse.pageObjects.ProductCatalogue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.bouncycastle.asn1.dvcs.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SubmitOrderTest extends BaseTest{

	String productName="ZARA COAT 3";
	
	@Test(dataProvider="getData", groups= {"Purchase","Smoke"})
	public void SubmitOrder(HashMap<String,String>input) throws IOException {
		
		ProductCatalogue productCatalogue=landingPage.loginApplication(input.get("email"),input.get("password"));
		
		//ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		List<WebElement> products=productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage=productCatalogue.goToCartPage();
		
		//CartPage cartPage=new CartPage(driver);
		Boolean match=cartPage.verifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage=cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage=checkoutPage.submitOrder();
		
		
		String confirmMessage=confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		
		
		}
	
	@Test(dataProvider="getData",dependsOnMethods={"SubmitOrder"},groups= {"OrderHistory"})
	public void OrderHistoryTest(HashMap<String,String>input) {
		ProductCatalogue productCatalogue=landingPage.loginApplication(input.get("email"),input.get("password"));
		OrderPage orderPage= productCatalogue.goToOrderPage();
		Assert.assertTrue(orderPage.VerifyOrderDisplay(input.get("product")));
		

	}
	
	
	
	/*@DataProvider
	public Object[][] getData() {
		return new Object[][] {{"manager@test.com","Test@123","ZARA COAT 3"},{"shetty@gmail.com","Iamking@000","Adidas Original"}};
	}*/
	
	/*@DataProvider
	public Object[][] getData() {
		
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("email", "manager@test.com");
		map.put("password", "Test@123");
		map.put("product", "ZARA COAT 3");
		
		HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email", "shetty@gmail.com");
		map1.put("password", "Iamking@000");
		map1.put("product", "Adidas Original");
		
		return new Object[][] {{map},{map1}};
	}*/
	// commented above code so I will get to know different ways to use DataProvider
	
	@DataProvider
	public Object[][] getData() throws IOException{
		List<HashMap<String,String>> data=getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\SeleniumAutomationCourse\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(1)}};
	}
	
	

}
