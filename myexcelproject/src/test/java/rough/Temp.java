package rough;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import framework.util.ExcelUtil;

public class Temp {
	
	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException, IOException  {
		List<String> abc = Lists.newArrayList();
		abc.add("one");
		abc.add("two");
		abc.add("three");
		Multimap<String, String> multi = ArrayListMultimap.create();
		
		for(String s : abc){
			multi.put(s, "Krishna");
			multi.put(s, "navya");
		}
		
		Collection<String> al = multi.get("one");
		System.out.println(al.toString());
		//System.out.println(al.get(1));
		
		//FileInputStream fis = new FileInputStream("E:/Tej/rough.xlsx");
		//Workbook wrkbook = WorkbookFactory.create(fis);
		//Sheet wrksheet = wrkbook.getSheet("HomePage");
		//System.out.println(wrksheet.getRow(1).getCell(1).toString());
		ExcelUtil xl = new ExcelUtil("E:/Tej/rough.xlsx", "HomePage");
		System.out.println(xl.wrksheet.getRow(0).getCell(0).toString());
		//System.out.println(xl.readCell("Object", 0));
		xl.readCell(0, 0);
		xl.loadColumnDict();
		System.out.println(xl.readCell("Type", 5));
	}
	
}
