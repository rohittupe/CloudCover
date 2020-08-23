package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;

import com.util.actions.init.Initializer;

public class HomePage extends Initializer {

	@FindBy(xpath="//*[contains(@class,'logo') and contains(.,'Stack Overflow')]") WebElement homepageLogo;
	
	@FindBy(xpath="//*[@href='/questions' and contains(text(),'Browse questions')]") WebElement browseQuestionsLink;
	String closeLink = "//*[contains(@class,'js-notice-close')]";

	WebDriverWait wait;
	
	public HomePage() {
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
		wait = new WebDriverWait(driver, 10);
	}

	public void clickOnBrowseQuestionsLink() {
		wait.until(ExpectedConditions.visibilityOf(browseQuestionsLink));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", browseQuestionsLink);
		wait.until(ExpectedConditions.elementToBeClickable(browseQuestionsLink));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", browseQuestionsLink);
	}

	public void verifyHomePageLoadedOrNot() {
		wait.until(ExpectedConditions.visibilityOf(homepageLogo));
		wait.until(ExpectedConditions.elementToBeClickable(homepageLogo));
		assertEquals(true, homepageLogo.isDisplayed());
	}
	
	public void clickCloseForAcceptCookiesIfExists() {
		try {
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(closeLink))));
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath(closeLink))));
			driver.findElement(By.xpath(closeLink)).click();
		}catch (Exception e) {}
	}
}
