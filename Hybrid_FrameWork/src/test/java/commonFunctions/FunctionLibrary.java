package commonFunctions;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	//method for launching browser
	public static WebDriver startBrowser() throws Throwable 
	{ 
		conpro = new Properties();
		//load property file
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver =  new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			try {
				throw new IllegalArgumentException("Browser value is not matching");
			} catch (IllegalArgumentException i) {
				System.out.println(i.getMessage());
			}	
		}
		return driver;
	}
	// method for launching url
	public static void openUrl() 
	{
		driver.get(conpro.getProperty("Url"));
	}
	//method for waiting for any webelement
	public static void waitForElement(String LocatorType,String LocatorValue,String wait)
	{
		WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(wait)));
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
		}
	}
	//method for textbox
	public static void typeAction(String LocatorType,String LocatorValue,String TestData)
	{   
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
		}
	}
	// method for buttons, links radiobutton,checkboxes and images
	public static void clickAction(String LocatorType, String LocatorValue)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	// method for validate title
	public static void validateTitle(String Expected_title)
	{
		String Actuval_Title = driver.getTitle();
		try {
			Assert.assertEquals(Actuval_Title, Expected_title, "Title is Not Matching");
		} catch (AssertionError a) {
			System.out.println(a.getMessage());
		}
	}
	public static void closeBrowser()
	{
		driver.quit();
	}
	//method for date generate
	public static String generateDate()
	{
		Date date = new Date();
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
		return df.format(date);

	}
	//method for dropdown or list box
public static void dropdownAction(String LocatorType,String LocatorValue,String TestData)
{
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		//convert testdata value into integertype
		int value = Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.xpath(LocatorValue)));
	    element.selectByIndex(value);
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		//convert testdata value into integertype
		int value = Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.name(LocatorValue)));
		element.selectByIndex(value);
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		//convert testdata value into integertype
		int value = Integer.parseInt(TestData);
		Select element = new Select(driver.findElement(By.id(LocatorValue)));
		element.selectByIndex(value);
	}
	 
}
    //method to capture stock number into note pad
public static void capturestock(String LocatorType,String LocatorValue) throws Throwable
{
	String StockNum ="";
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		StockNum= driver.findElement(By.xpath(LocatorValue)).getAttribute("value");	
	}
	if(LocatorType.equalsIgnoreCase("name")) 
	{
		 StockNum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		 StockNum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	//create note pad under capture data folder and write stock number
	FileWriter fw = new FileWriter("./CaptureData/Stocknumber.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(StockNum);
	bw.flush();
	bw.close();
}
// method for validate stock number in stock table
public static void stocktable() throws Throwable
{
	//read stock number from above stock number note pad
	FileReader fr = new FileReader("./CaptureData/Stocknumber.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
	//click search panel  if search text box is displayed
	driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(Exp_data);
	driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
	Thread.sleep(2000);
	String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	Reporter.log(Exp_data+"    "+Act_data,true);
	try {
		Assert.assertEquals(Act_data, Exp_data, "Stock number not found in table");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
}
// method for supplier capture number
public static void capturesupnum(String LocatorType,String LocatorValue)throws Throwable
{
	String SupplierNum="";
	if(LocatorType.equalsIgnoreCase("Xpath"))
	{
		SupplierNum= driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		SupplierNum= driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		SupplierNum= driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	//craete note pad above under capture data folder and write supplier number
	FileWriter fr = new FileWriter("./CaptureData/SupplierNumber.txt");
	BufferedWriter bw = new BufferedWriter(fr);
	bw.write(SupplierNum);
	bw.flush();
	bw.close();
}
// method for supplier number read note pad
public static void supplierTable()throws Throwable
{
	//read above supplier number note pad 
	FileReader fr = new FileReader("./CaptureData/SupplierNumber.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
	//click search panel if search text box is displayed
	driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(Exp_data);
	driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
	Thread.sleep(2000);
	String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(Act_data+"    "+Exp_data,true);
	try {
		Assert.assertEquals(Exp_data, Act_data,"Supplier number is not found");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
}
//method for capture customer number 
public static void captureCusnum(String LocatorType, String LocatorValue)throws Throwable
{
	String customerNum ="";
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		customerNum = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		customerNum = driver.findElement(By.name(LocatorValue)).getAttribute("value");
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		customerNum = driver.findElement(By.id(LocatorValue)).getAttribute("value");
	}
	//create note pad above under capture data folder and write customer number
	FileWriter fw = new FileWriter("./CaptureData/CustomerNumber.txt");
	BufferedWriter bw = new BufferedWriter(fw);
	bw.write(customerNum);
	bw.flush();
	bw.close();
}
// method for read customer number note pad
public static void customerTable() throws Throwable
{
	//read the above customer number note pad
	FileReader fr = new FileReader("./CaptureData/CustomerNumber.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
	//click search panel  if search text box is displayed
	driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(Exp_data);
	driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
	Thread.sleep(2000);
	String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
	Reporter.log(Act_data+"     "+Exp_data,true);
	try {
		Assert.assertEquals(Exp_data, Act_data,"customer number is not found");
	} catch (AssertionError b) {
		System.out.println(b.getMessage());
	}
}
//method for move to element stock category link
public static void movetoElement(String LocatorType,String LocatorValue)
{
	Actions ac = new Actions(driver);
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		ac.moveToElement(driver.findElement(By.xpath(LocatorValue)));
		ac.perform();
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		ac.moveToElement(driver.findElement(By.name(LocatorValue)));
		ac.perform();
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		ac.moveToElement(driver.findElement(By.id(LocatorValue)));
		ac.perform();
	}
}
// method for  capture category  name into note pad
public static void caputurecatename(String LocatorType,String LocatorValue) throws Throwable
{
    String categoryName = "";
    
    if(LocatorType.equalsIgnoreCase("xpath"))
    {
    	categoryName = driver.findElement(By.xpath(LocatorValue)).getAttribute("value");	
    }
    if(LocatorType.equalsIgnoreCase("name"))
    {
    	categoryName = driver.findElement(By.name(LocatorValue)).getAttribute("value");	    
    }
     if(LocatorType.equalsIgnoreCase("id"))
    {
    	categoryName = driver.findElement(By.id(LocatorValue)).getAttribute("value");
    }
    //create under capture data category name folder and write category name
    FileWriter fw = new FileWriter("./CaptureData/Categoryname.txt");
    BufferedWriter bw = new BufferedWriter(fw);
    bw.write(categoryName);
    bw.flush();
    bw.close();
}
//method for read category name note pad
public static void categoryTable()throws Throwable
{
	//read above category name note pad
	FileReader fr = new FileReader("./CaptureData/Categoryname.txt");
	BufferedReader br = new BufferedReader(fr);
	String Exp_data = br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
		
		driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(Exp_data);
	driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
	Thread.sleep(2000);
	String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[4]/div/span/span")).getText();
	Reporter.log(Act_data+"    "+Exp_data,true);
	try {
		Assert.assertEquals(Exp_data, Act_data,"Stock category name is not found");
	} catch (AssertionError k) {
		System.out.println(k.getMessage());
	}
}
}
