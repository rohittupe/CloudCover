package com.util.actions.init;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Initializer {

	public static WebDriver driver;
	public static Properties prop;
	public static RequestSpecification requestSpec;

	public Initializer() {
		prop = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/config.properties");
			prop.load(fis);
		} catch (IOException e) {}
	}

	public static WebDriver getDriver() {
		String browser = prop.getProperty("browser");
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}else if(browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}else if(browser.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(2, TimeUnit.MINUTES);
		return driver;
	}

	public static String getAppURL() {
		String returnURL = "";
		if(prop!=null)
			returnURL = prop.getProperty("app_url");
		return returnURL;
	}

	public static String getBaseURL() {
		String returnURL = "";
		if(prop!=null)
			returnURL = prop.getProperty("baseUrl");
		return returnURL;
	}
	
	public RequestSpecification requestSpecsification() throws IOException
	{
		if(requestSpec==null) {
			PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
			requestSpec=new RequestSpecBuilder().addHeader("Authorization", "Bearer "+prop.getProperty("api_token")).setBaseUri(prop.getProperty("baseUrl"))
					.addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log))
					.setContentType(ContentType.JSON).build();
		}
		return requestSpec;
	}



}
