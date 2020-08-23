package stepDefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import cucumber.api.junit.Cucumber;
import pageObjects.TagsPage;
import pageObjects.HomePage;
import pageObjects.QuestionsPage;
import org.junit.runner.RunWith;
import com.util.actions.init.Initializer;

@RunWith(Cucumber.class)
public class StepDefinitions extends Initializer {

	HomePage homePage;
	TagsPage tagsPage;
	QuestionsPage questionsPage;

	@Given("^I am a new user and access the Stackoverflow site$")
	public void i_am_a_new_user_and_access_the_Stackoverflow_site() throws Throwable {
		driver = Initializer.getDriver();
		driver.get(Initializer.getAppURL());
	}

	@When("^I click on \"([^\"]*)\" section on Homepage$")
	public void i_click_on_section_on_homepage(String link) throws Throwable {
		homePage = new HomePage();
		homePage.clickCloseForAcceptCookiesIfExists();
		if(link.equalsIgnoreCase("browse questions")) {
			homePage.verifyHomePageLoadedOrNot();
			homePage.clickOnBrowseQuestionsLink();
		}	
	}

	@When("^I select \"([^\"]*)\" filter$")
	public void i_select_filter(String filterBy) throws Throwable {
		tagsPage = new TagsPage();
		tagsPage.verifyTagsPageLoadedOrNot();
		if(filterBy.equalsIgnoreCase("name"))
			tagsPage.clickOnFilterByNameLink();
	}

	@When("^I am on page number \"([^\"]*)\" of result$")
	public void i_am_on_page_number_of_result(String pageNumber) throws Throwable {
		tagsPage = new TagsPage();
		tagsPage.verifyPageSelected(pageNumber);
	}

	@When("^I get the tag having maximum questions$")
	public void i_get_the_tag_having_maximum_questions() throws Throwable {
		tagsPage = new TagsPage();
		tagsPage.getTagWithMaxQuestionCount();
	}

	@Then("^I should able to see the Tag (.+) with maximum questions$")
	public void i_should_able_to_see_the_Tag_htaccess_with_maximum_questions(String tag) throws Throwable {
		tagsPage = new TagsPage();
		String message = tagsPage.getTagAndCountMessage();
		System.out.println("message="+message);
		assert message.contains(tag) == true;
		if(message.matches("Tag.* \\[[."+tag+"|,."+tag+"]+\\] have.*"))
			assert true;
		else
			assert false;
	}

	@When("^I click on \"([^\"]*)\" section on Questions page$")
	public void i_click_on_section_on_Questions_page(String link) throws Throwable {
		questionsPage = new QuestionsPage();
		questionsPage.verifyQuestionsPageLoadedOrNot();
		if(link.equalsIgnoreCase("tags"))
			questionsPage.clickOnTagsLink();
		else if(link.equalsIgnoreCase("users"))
			questionsPage.clickOnUsersLink();
	}

}