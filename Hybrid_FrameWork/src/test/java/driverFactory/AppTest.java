package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class AppTest {
	WebDriver driver;
	String Fileinput = "./DataTables/DataEngine.xlsx";
	String Fileoutput ="./DataTables/HybridResults.xlsx";
	ExtentReports reports;
	ExtentTest logger;
	String TCSheet ="MasterTestcases";
	@Test
	public void startTest() throws Throwable
	{
		String module_Pass="";
		String module_Fail="";
		// create object for excel fileutill class
		ExcelFileUtil xl = new ExcelFileUtil(Fileinput);
		//count no of rows in xl sheet
		int rc = xl.rowCount(TCSheet);
		//iterate all rows in TCSheet
		for(int i=1; i<=rc; i++)
		{
			if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
			{
				//read module name cell into one variable
				String TCModule = xl.getCellData(TCSheet, i, 1);
				//define path extent reports 
				reports = new ExtentReports("./target/Extentreports/"+TCModule+FunctionLibrary.generateDate()+".html");
				logger = reports.startTest(TCModule);
				logger.assignAuthor("Srirama raju");
				for(int j=1;j<=xl.rowCount(TCModule);j++)
				{
					//read all cells from TCModule
					String Description = xl.getCellData(TCModule, j, 0);
					String object_Type = xl.getCellData(TCModule, j, 1);
					String Ltype = xl.getCellData(TCModule, j, 2);
					String Lvalue = xl.getCellData(TCModule, j, 3);
					String Tdata = xl.getCellData(TCModule, j, 4);
					try {
						if(object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(Ltype, Lvalue, Tdata);
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Ltype, Lvalue, Tdata);
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(Tdata);
							logger.log(LogStatus.INFO, Description);
						}
						if(object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
                        if(object_Type.equalsIgnoreCase("dropdownAction"))
                        {
                        	FunctionLibrary.dropdownAction(Ltype, Lvalue, Tdata);
                        	logger.log(LogStatus.INFO, Description);
                        }
                        if(object_Type.equalsIgnoreCase("capturestock"))
                        {
                        	FunctionLibrary.capturestock(Ltype, Lvalue);
                        	logger.log(LogStatus.INFO, Description);
                        }
                        if(object_Type.equalsIgnoreCase("stocktable"))
                        {
                        	FunctionLibrary.stocktable();
                        	logger.log(LogStatus.INFO, Description);
                        }
                        if(object_Type.equalsIgnoreCase("capturesupnum"))
                        {
                        	FunctionLibrary.capturesupnum(Ltype, Lvalue);
                        	logger.log(LogStatus.INFO, Description);
                        }
                        if(object_Type.equalsIgnoreCase("supplierTable"))
                        {
                        	FunctionLibrary.supplierTable();
                        	logger.log(LogStatus.INFO, Description);
                        }
                        if(object_Type.equalsIgnoreCase("captureCusnum"))
                        {
                        	FunctionLibrary.captureCusnum(Ltype, Lvalue);
                        	logger.log(LogStatus.INFO, Description);
                        }
                        if(object_Type.equalsIgnoreCase("customerTable"))
                        {
                        	FunctionLibrary.customerTable();
                        	logger.log(LogStatus.INFO, Description);
                        }
                        if(object_Type.equalsIgnoreCase("movetoElement"))
                        {
                        	FunctionLibrary.movetoElement(Ltype, Lvalue);
                        	logger.log(LogStatus.INFO, Description);
                        }
                        if(object_Type.equalsIgnoreCase("caputurecatename"))
                        {
                        	FunctionLibrary.caputurecatename(Ltype, Lvalue);
                        	logger.log(LogStatus.INFO, Description);
                         }
                        if(object_Type.equalsIgnoreCase("categoryTable"))
                        {
                         FunctionLibrary.categoryTable();	
                         logger.log(LogStatus.INFO, Description);
                        }
						//writr pass into status cell TCModule
						xl.setCelldata(TCModule, j, 5, "Pass", Fileoutput);
						logger.log(LogStatus.PASS, Description);
						module_Pass="true";

					} catch (Exception e) {
						System.out.println(e.getMessage());
						//write fail into status cell TCModule
						xl.setCelldata(TCModule, j, 5, "Fail", Fileoutput);
						logger.log(LogStatus.FAIL, Description);
						module_Fail="false";
						File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen, new File("./target/Screenshot/"+Description+FunctionLibrary.generateDate()+".png"));		 
					}
					if(module_Pass.equalsIgnoreCase("True"))
					{
						//write pass into TCSheet
						xl.setCelldata(TCSheet, i, 3, "Pass", Fileoutput);
					}
					if(module_Fail.equalsIgnoreCase("fail")) 
					{
						//write fail into TCsheet
						xl.setCelldata(TCSheet, i, 3, "fail", Fileoutput);
					}

				}
				reports.endTest(logger);
				reports.flush();
			}
			else
			{
				//write as blocked into status cell in TCsheet
				xl.setCelldata(TCSheet, i, 3, "Blocked", Fileoutput);
			}
		}
	}
}
