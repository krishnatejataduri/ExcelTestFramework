package framework.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	public Workbook wrkbook;
	public Sheet wrksheet;
	public HashMap<String,Integer> ColumnDict = new HashMap<>();
	public FileInputStream fis;
	public HashMap<String,String> runTimeORTable = new HashMap<>();
	
	public ExcelUtil(String path, String sheetName) throws EncryptedDocumentException, InvalidFormatException, IOException{
		fis = new FileInputStream(path);
		this.wrkbook = WorkbookFactory.create(fis);
		this.wrksheet = wrkbook.getSheet(sheetName);
		loadColumnDict();
	}
	
	public HashMap<String,String> loadRunTimeORTable(){
		try{
		for(int r=1;r<=getRowCount();r++){
			runTimeORTable.put(readCell("Object", r), readCell("Locator", r));
		}
		}
		catch(Exception e){
			System.out.println("Encountered the below error while trying to add values to the run time data table: /n"+ e.getStackTrace() );
		}
		finally{
		return runTimeORTable;
		}
	}
	
	public static ExcelUtil getInstance(String path, String sheetName){
		try {
			return new ExcelUtil(path, sheetName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Please check if the file exists at location: "+path);
			e.printStackTrace();
			return null;
		}
	}
	
	
	public int getRowCount(){
		return wrksheet.getLastRowNum();
	}
	
	public int getColumnCount(){
		return wrksheet.getRow(0).getLastCellNum();
	}
	
	public String readCell(int col, int row){
		return wrksheet.getRow(row).getCell(col).getStringCellValue();
	}
	
	
	public void loadColumnDict(){
		for(int col=0;col<getColumnCount();col++){
			String cellVal = readCell(col,0);
			if((cellVal!=null && cellVal!="")){
				String colName = cellVal;
				ColumnDict.put(cellVal, col);
			}
			else{
				break;
			}
		}
	}
	
	public  int getColNum(String ColName){
		int ColNum = Integer.parseInt(ColumnDict.get(ColName).toString());
		return ColNum;
	}
	
	public String readCell(String ColName, int Row){
		return wrksheet.getRow(Row).getCell(getColNum(ColName)).getStringCellValue();
	}

}