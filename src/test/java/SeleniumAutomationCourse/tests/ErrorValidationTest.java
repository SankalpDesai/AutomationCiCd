package SeleniumAutomationCourse.tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.Test;

import com.sun.net.httpserver.Authenticator.Retry;

import SeleniumAutomationCourse.TestComponents.BaseTest;
import SeleniumAutomationCourse.pageObjects.CartPage;
import SeleniumAutomationCourse.pageObjects.ProductCatalogue;

public class ErrorValidationTest extends BaseTest{
	
	@Test(groups= {"ErrorHandling"},retryAnalyzer=SeleniumAutomationCourse.TestComponents.Retry.class)
	public void LoginErrorValidation() {
		ProductCatalogue productCatalogue=landingPage.loginApplication("manager@tes.com", "Test@1");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());
	}
	
	@Test
	public void ProductErrorValidation() throws IOException {
		// TODO Auto-generated method stub
		
		String productName="ZARA COAT 3";
		ProductCatalogue productCatalogue=landingPage.loginApplication("manager@test.com", "Test@123");
		
		//ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		List<WebElement> products=productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage=productCatalogue.goToCartPage();
		
		//CartPage cartPage=new CartPage(driver);
		Boolean match=cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
	}

}
