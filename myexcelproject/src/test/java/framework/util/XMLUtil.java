package framework.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import framework.config.Settings;

public class XMLUtil {

	public static void main(String[] args) {
		XMLUtil xml = new XMLUtil();
		xml.generateXML();
	}
	
  /*Method Name:generateXML()
	Arguments: None
	Description: To generate the testNG XML based on the Run Config Excel Sheet and save it at a specified location.
	Author: Krishna Taduri
	Developed on: 21/02/2019*/  
	public void generateXML() {
		Document doc;
		try {
			doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			// suite element
			Element suite = doc.createElement("suite");
			doc.appendChild(suite);
			ExcelUtil excelUtil = ExcelUtil.getInstance(Settings.RunConfigExcelPath, Settings.RunCongSheetName);
			int rowCount = excelUtil.getRowCount();
			for (int rowNum = 0; rowNum <= rowCount; rowNum++) {
				String TestCaseName = excelUtil.readCell("TestCaseName", rowNum);
				String ExecutionFlag = excelUtil.readCell("ExecutionFlag", rowNum);
				if (ExecutionFlag.equalsIgnoreCase("Y")) {
					//test element
					Element test = doc.createElement("test");
					suite.appendChild(test);
					//classes element
					Element classes =  doc.createElement("classes");
					test.appendChild(classes);
					//class element
					Element classElement = doc.createElement("class");
					classElement.setAttribute("name",TestCaseName);
					classes.appendChild(classElement);
				}
			}
			doc.normalize();
			writeFile(doc,Settings.XMLPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*Method writeFile(Document xml, String SaveAtLocation)
	Arguments: Document Object , File Path of the location where it is to be saved.
	Description: To save a document object at a specified location
	Author: Krishna Taduri
	Developed on: 21/02/2019*/  
	public static final void writeFile(Document xml, String SaveAtLocation) throws Exception {
		Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		Writer out = new StringWriter();
		tf.transform(new DOMSource(xml), new StreamResult(out));
		File f = new File(SaveAtLocation);
		Writer w = new FileWriter(f);
		w.write(out.toString());
		w.flush();
		w.close();
	}
}
