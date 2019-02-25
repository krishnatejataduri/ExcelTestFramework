package rough;

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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	
	public static void main(String[] args) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc;
		try {
			db = dbf.newDocumentBuilder();
			try {
				doc = db.parse(new File("C:\\Users\\krishna.teja.taduri\\git\\ExcelTestFramework\\myexcelproject\\src\\test\\java\\rough\\testng.xml"));
				doc.getDocumentElement().normalize();
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				Element element = (Element)doc.getElementsByTagName("classes").item(0);
				element.getParentNode().removeChild(element);
				doc.normalize();
   			    try {
					prettyPrint(doc);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
   			   
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final void prettyPrint(Document xml) throws Exception {
		
		        Transformer tf = TransformerFactory.newInstance().newTransformer();
		
		        tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		        tf.setOutputProperty(OutputKeys.INDENT, "yes");
		
		        Writer out = new StringWriter();
		
		        tf.transform(new DOMSource(xml), new StreamResult(out));
		        File f = new File("C:\\Users\\krishna.teja.taduri\\git\\ExcelTestFramework\\myexcelproject\\resources\\testng.xml");
		        Writer w = new FileWriter(f);
		        w.write(out.toString());
		        w.flush();
		        w.close();
		
		    }


}
