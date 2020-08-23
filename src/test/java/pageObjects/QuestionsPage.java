package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;

import com.util.actions.init.Initializer;

public class QuestionsPage extends Initializer {

	@FindBy(id="nav-tags") WebElement tagsLink;
	@FindBy(id="nav-users") WebElement usersLink;
	@FindBy(xpath="//*[contains(@class,'headline') and contains(.,'All Questions')]") WebElement headerText;
	
	WebDriverWait wait;
	
	public QuestionsPage() {
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
		wait = new WebDriverWait(driver, 10);
	}
	
	public void clickOnTagsLink() {
		wait.until(ExpectedConditions.elementToBeClickable(tagsLink));
		tagsLink.click();
	}
	
	public void clickOnUsersLink() {
		wait.until(ExpectedConditions.elementToBeClickable(usersLink));
		usersLink.click();
	}

	public void verifyQuestionsPageLoadedOrNot() {
		wait.until(ExpectedConditions.visibilityOf(headerText));
		wait.until(ExpectedConditions.elementToBeClickable(headerText));
		assertEquals(true, headerText.isDisplayed());
	}

}
