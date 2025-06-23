package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
Workbook wb;
//write constructor for reading excel file path
public ExcelFileUtil(String Excelpath) throws Throwable
{
	FileInputStream fi = new FileInputStream(Excelpath);
	wb = WorkbookFactory.create(fi);
}
//method for counting rows in a sheet
public int rowCount(String sheetname)
{
	 return wb.getSheet(sheetname).getLastRowNum();
}
//method for reading cell data
public String getCellData(String sheetname,int row,int column) 
	{
		String data ="";
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
		int celldata =(int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
		 data = String.valueOf(celldata);
		}
		else
		{
			  data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();		  
		}
		return data;
	}
//method for writing results
public void setCelldata(String sheetname,int row,int column,String status,String WriteExcel)throws Throwable {
	//get sheet from wb
	Sheet ws = wb.getSheet(sheetname);
	//get row from sheet
	Row rowNUm = ws.getRow(row);
	Cell cell = rowNUm.createCell(column);
	//write status
	cell.setCellValue(status);
	if(status.equalsIgnoreCase("Pass"))
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
	 style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
	 style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 font.setBold(true);
	 style.setFont(font);
	 rowNUm.getCell(column).setCellStyle(style);
	 
	}
	else if(status.equalsIgnoreCase("Fail")) 
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
	 style.setFillForegroundColor(IndexedColors.RED.getIndex());
	 style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 font.setBold(true);
	 style.setFont(font);
	 rowNUm.getCell(column).setCellStyle(style);	
	}
	else if(status.equalsIgnoreCase("Blocked")) 
	{
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
	 style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
	 style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	 font.setBold(true);
	 style.setFont(font);
	 rowNUm.getCell(column).setCellStyle(style);
	}
	FileOutputStream fo = new FileOutputStream(WriteExcel);
	wb.write(fo);
}

}


 