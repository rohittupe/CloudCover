package pageObjects;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertEquals;

import com.util.actions.init.Initializer;


public class TagsPage extends Initializer {

	@FindBy(xpath="//*[contains(@class,'headline') and contains(.,'Tags')]") WebElement headerText;

	@FindBy(xpath="//*[contains(@class,'js-filter-btn')]//*[contains(.,'Name')]") WebElement filterByNameLink;

	@FindBys(@FindBy(xpath="//*[@id='tags-browser']//*[contains(@class, 's-card')]")) List<WebElement> tags;
	
	@FindBys(@FindBy(xpath="//*[contains(@class,'pagination')]//*")) List<WebElement> pagesLinks;
	
	private String tagNamesXpath = "//*[contains(@class,'jc-space-between') and not(contains(@class,'fs-caption'))]//*[@class='post-tag']";
	
	private String questionNamesXpath = "//*[contains(@class,'jc-space-between') and contains(@class,'fs-caption')]//*[contains(text(),' question')]";
	
	private static String tagAndCountMessage = ""; 

	WebDriverWait wait;
	
	public TagsPage() {
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 20), this);
		wait = new WebDriverWait(driver, 10);
	}

	public void verifyTagsPageLoadedOrNot() {
		wait.until(ExpectedConditions.visibilityOf(headerText));
		wait.until(ExpectedConditions.elementToBeClickable(headerText));
		assertEquals(true, headerText.isDisplayed());
	}

	public void clickOnFilterByNameLink() {
		wait.until(ExpectedConditions.elementToBeClickable(filterByNameLink));
		filterByNameLink.click();
	}

	public void verifyPageSelected(String pageNumber) throws Exception {
		boolean flag = false;
		wait.until(ExpectedConditions.visibilityOfAllElements(pagesLinks));
		System.out.println(pagesLinks.size());
		if(pagesLinks.size() > 0) {
			for(int i=0; i<pagesLinks.size(); i++) {
				WebElement firstPage = pagesLinks.get(i);
				String displayText = String.valueOf(firstPage.getText());
				String displayClass = String.valueOf(firstPage.getAttribute("class"));
				if(!displayText.equalsIgnoreCase("null") && !String.valueOf(pageNumber).equalsIgnoreCase("null") 
						&& displayText.toLowerCase().trim().equalsIgnoreCase(pageNumber.trim()) 
						&& !displayClass.equalsIgnoreCase("null") && displayClass.toLowerCase().trim().contains("is-selected")) {
					flag = true;
					break;
				}
			}
		}
		assertEquals(true, flag);
	}
	
	public String getTagWithMaxQuestionCount() throws Exception{
		File f = new File("C:\\Rohit_Data\\MySpace\\workspace_data\\CloudCover\\src\\test\\resources\\logs.log");
		BufferedWriter writer = new BufferedWriter(new FileWriter(f, false));
		Map<Integer, List<String>> questionTagMap = new HashMap<Integer, List<String>>();
		String message = "";
		wait.until(ExpectedConditions.visibilityOfAllElements(tags));
		writer.write("\n tags size="+tags.size());
		System.out.println("\n tags size="+tags.size());
		
		if(tags.size() > 0) {
			List<String> tempList = new ArrayList<String>();
			for(int i=0; i<tags.size(); i++) {
				int j=(i+1);
				WebElement tagElement = tags.get(i);
				tempList = new ArrayList<String>();
				String tagText = String.valueOf(tagElement.findElement(By.xpath("//*[@id='tags-browser']//*[contains(@class, 's-card')]["+j+"]"+tagNamesXpath)).getText());
				String wholeQuestionsText = String.valueOf(tagElement.findElement(By.xpath("//*[@id='tags-browser']//*[contains(@class, 's-card')]["+j+"]"+questionNamesXpath)).getText());
				System.out.println("tagText="+tagText+" :: wholeQuestionsText="+wholeQuestionsText);
				String questionsText = wholeQuestionsText;
				if(questionsText.contains(" "))
					questionsText = questionsText.split(" ")[0];
				System.out.println("questionsText="+questionsText);
				writer.write("\n tagText="+tagText);
				writer.write("\t :: questionsText="+questionsText);
				if(!tagText.equalsIgnoreCase("null") && !questionsText.equalsIgnoreCase("null")) {
					int count = Integer.parseInt(questionsText);
					if(questionTagMap.containsKey(count)) {
						tempList.addAll(questionTagMap.get(count));
						tempList.add(tagText);
					}else {
						tempList.add(tagText);
					}
					questionTagMap.put(count, tempList);
				}
				System.out.println("i="+i + "  questionTagMap="+questionTagMap);
			}
			System.out.println("questionTagMap="+questionTagMap);
			writer.write("\n questionTagMap size="+questionTagMap.size());
			
			int max = Collections.max(questionTagMap.keySet());
			System.out.println("Max Key="+max);
			writer.write("\n max="+max);
			message = "Tag(s) "+questionTagMap.get(max)+" have "+max+" questions.";
			System.out.println(message);
			writer.write("\nmessage="+message);
		}
		setTagAndCountMessage(message);
		writer.close();
		return message;
	}
	
	public String getTagAndCountMessage() {
		return tagAndCountMessage;
	}
	
	public void setTagAndCountMessage(String message) {
		tagAndCountMessage = message;
	}
}
