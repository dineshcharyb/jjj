package com.jeevlifeworks.migrationservice.XMLModifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import com.jeevlifeworks.migrationservice.ApplicationLevel;

import au.com.bytecode.opencsv.CSVReader;

@Service
public class XMLModifierService {
	

	@Autowired
	ApplicationLevel applicationLevel;

	public void xmlEditorWithMultiFunction(File sourcefile,File configurationFile,String destinationFolderDir) throws SAXException, IOException, ParserConfigurationException,
			TransformerException, XPathExpressionException {

		/**
		 * parsing XMl file
		 */
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(sourcefile);
		System.out.println("document parsed");

		
		/**
		 * Read CSV file 
		 */
		
		CSVReader reader = new CSVReader(new FileReader(configurationFile), ',', '"', 0);
	
		// Read all rows at once
		List<String[]> allRows = reader.readAll();

		for (int j = 1; j < allRows.size(); j++) {
		
		
		String action=allRows.get(j)[0];
		
        System.out.println("Action     "+action);		
		
		if(action.equals("CopyNodeValue")) {
			
			doc=this.copyValueAndPasteToAnotherTag(doc,allRows.get(j));
			
		}
		else if(action.equals("CreateNewTag")) {
			
			doc=this.createNewTag(doc,allRows.get(j));
			
		}
		else if(action.equals("SwapTagValues")) {
			
			doc=this.swapValues(doc,allRows.get(j));
			
		}else if (action.equals("ReallocateTag")) {
			
			doc=this.reallocateTags(doc,allRows.get(j));
			
		}else if(action.equals("RenameTags")) {
			
			doc=this.renameTags(doc,allRows.get(j));
			
		}else if(action.equals("IfNotExistCreateNew")) {
			
			doc=this.ifNotExistCreateNewTag(doc,allRows.get(j));
			
		}else if(action.equals("AppendToNodeValue")) {
			
			doc=this.appendNodeValue(doc,allRows.get(j));
			
		}else if(action.equals("validatePatientBirthDate")) {
			
			doc=this.validatePatientBirthDate(doc,allRows.get(j));
			
		}else if(action.equals("truncateTagvalues")) {
			
			doc=this.truncateTagvalues(doc, allRows.get(j));
		}
		
		
		
	}
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		System.out.println("creating file");
		StreamResult result = new StreamResult(new File(destinationFolderDir +"/"+ sourcefile.getName()));
		transformer.transform(source, result);
		System.out.println("close");

		
	}
	
	// code to copy a value and add it to another tag value
	
	public Document copyValueAndPasteToAnotherTag(Document doc, String[] params) {
		
		

		NodeList node =	 doc.getElementsByTagName(params[1]);
		NodeList node2 = doc.getElementsByTagName(params[2]);
		
		String nodevalue=null;
		String nodevalue1=null;
		for (int i = 0; i < node.getLength(); i++) {
	
			Node node1 = node.item(1);
			System.out.println("node value   "+node1.getTextContent());
			nodevalue=node1.getTextContent();
		
		
		}
		
		for (int i = 0; i < node2.getLength(); i++) {
	
			Node node1 = node2.item(0);
			System.out.println("node value   "+node1.getTextContent());
			node1.setTextContent(nodevalue);
	
		}
		return doc;
	}
	
	public Document appendNodeValue(Document doc,String[] params) {
		NodeList node=doc.getElementsByTagName(params[1]);
		for(int i=0;i<node.getLength();i++) {
			String nodeValue=node.item(i).getTextContent();
			if(params[2].equals("WHO")) {
				
				node.item(i).setTextContent("WHO: "+nodeValue);
			}else {
				node.item(i).setTextContent("KRCT: "+nodeValue); 
			}
			
		}
		return doc;
	}
	
	
	public Document createNewTag(Document doc, String[] params) throws XPathExpressionException {
		
		 //The XPath part.
	    XPathFactory xfactory = XPathFactory.newInstance();
	    XPath xpath = xfactory.newXPath();
	    
	  NodeList nodes=doc.getElementsByTagName(params[1]);
	  if(nodes.getLength()==0) {
		  
	  
		
		NodeList node = doc.getElementsByTagName(params[3]);
		NodeList duplicateNumb = doc.getElementsByTagName(params[2]);
		
		
		  Node result = (Node)xpath.evaluate(params[4], doc, XPathConstants.NODE);
		 
		  String duplicateNumbvalue = duplicateNumb.item(1).getTextContent();
		  System.out.println(params[2]+"     duplicate number value to add as compnumber  "+duplicateNumbvalue);
		    Element toInsert = doc.createElement(params[1]);
		    toInsert.appendChild(doc.createTextNode(duplicateNumbvalue));
		  //  node.item(0).appendChild(toInsert);
		    result.getParentNode().insertBefore(toInsert, result);

	//	String duplicateNumbvalue = duplicateNumb.item(0).getTextContent();
	//	Element toInsert = doc.createElement(params[1]);
	//	toInsert.appendChild(doc.createTextNode(duplicateNumbvalue));
	//	node.item(0).appendChild(toInsert);
		
	  }
		    return doc;
		
	}
	
	public Document swapValues(Document doc, String[] params) {

		NodeList nodeList=doc.getElementsByTagName(params[1]);
		NodeList nodeList1=doc.getElementsByTagName(params[2]);
		
		
		if(nodeList.getLength()==1&&nodeList1.getLength()==1) {
		String sponsorstudynumb=nodeList.item(0).getTextContent();
		String studynumb=nodeList1.item(0).getTextContent();
		
		nodeList.item(0).setTextContent(studynumb);
		nodeList1.item(0).setTextContent(sponsorstudynumb);
		}
		return doc;
	}
	
	public Document renameTags(Document doc, String[] params) {
		NodeList nodeList=doc.getElementsByTagName(params[1]);
		for (int i = 0; i < nodeList.getLength(); i++) {
		doc.renameNode(nodeList.item(i), nodeList.item(i).getNamespaceURI(),params[2]);
		}
		return doc;
	}
	
	public Document reallocateTags(Document doc, String[] params) throws XPathExpressionException {
		
		 //The XPath part.
	    XPathFactory xfactory = XPathFactory.newInstance();
	    XPath xpath = xfactory.newXPath();
		
	    Node result = (Node)xpath.evaluate(params[5], doc, XPathConstants.NODE);
	    Node result1 = (Node)xpath.evaluate(params[6], doc, XPathConstants.NODE);
	    
		NodeList nodeList = doc.getElementsByTagName(params[1]);
		NodeList nodeList1 = doc.getElementsByTagName(params[2]);

		if (nodeList.getLength() > 0) {
			Node node = nodeList.item(0);
			nodeList1.item(0).appendChild(node);
			//   Element toInsert = doc.createElement(params[1]);
			//    toInsert.appendChild(doc.createTextNode(duplicateNumbvalue));
			  //  node.item(0).appendChild(toInsert);
			    result.getParentNode().insertBefore(node, result.getPreviousSibling());
			System.out.println("appended");
			
			if(nodeList.item(0).getTextContent().equals("1")) {
				NodeList nodeList2=doc.getElementsByTagName(params[3]);
				
				if(nodeList2.getLength()==0) {
		//	Element toInsert = doc.createElement(params[3]);
		//	toInsert.appendChild(doc.createTextNode(params[4]));
		//	nodeList1.item(0).appendChild(toInsert);
			
			
			 Element toInsert = doc.createElement(params[3]);
			    toInsert.appendChild(doc.createTextNode(params[4]));
			  //  node.item(0).appendChild(toInsert);
			    result.getParentNode().insertBefore(toInsert, result1);
			 //   result.getParentNode().
				}
			}

		}
		return doc;
	}
	
	public Document validatePatientBirthDate(Document doc,String[] params) throws XPathExpressionException {
		System.out.println("Inside validate ");
		NodeList nodeList=doc.getElementsByTagName("patientbirthdateformat");
		NodeList nodeList1=doc.getElementsByTagName("patientbirthdate");
		
		 //The XPath part.
	    XPathFactory xfactory = XPathFactory.newInstance();
	    XPath xpath = xfactory.newXPath();
	    Node result = (Node)xpath.evaluate("/ichicsr/safetyreport/patient/patientbirthdate", doc, XPathConstants.NODE);
		System.out.println("Before if");
		if(nodeList.getLength()==0&&nodeList1.getLength()>0) {
			System.out.println("Inside if ");
			String patientBirthdateFormat=null;
			String patientbirthdate=nodeList1.item(0).getTextContent();
			int patientbirthdatelength=patientbirthdate.length();
			System.out.println("Before if again");
			if(patientbirthdatelength==8) {
				patientBirthdateFormat="102";	
			}else if(patientbirthdatelength==6){
				patientBirthdateFormat="610";
			}else if(patientbirthdatelength==4) {
				patientBirthdateFormat="602";
			}
			System.out.println("After if else");
			Element toInsert = doc.createElement("patientbirthdateformat");
			toInsert.appendChild(doc.createTextNode(patientBirthdateFormat));
			result.getParentNode().insertBefore(toInsert, result);
		}
		System.out.println("After if");
		return doc;
	}
	
	
	public Document truncateTagvalues(Document doc,String[] params) {
		NodeList nodeList=doc.getElementsByTagName(params[1]);
		String valueToTruncate=nodeList.item(0).getTextContent();
		String truncatedvaalue=valueToTruncate.substring(0,7);
		nodeList.item(0).setTextContent(truncatedvaalue);
		return doc;
	}
	
	public Document ifNotExistCreateNewTag(Document doc, String[] params) {
		NodeList nodeList=doc.getElementsByTagName(params[2]);
		System.out.println("drugresult length    "+nodeList.getLength());
		if(nodeList.getLength()==0) {
			
			NodeList nodeList1=doc.getElementsByTagName(params[1]);
			NodeList nodeList2=doc.getElementsByTagName(params[3]);
			
			for(int i=0;i<nodeList1.getLength();i++) {
				
				Element toInsert = doc.createElement("drugresult");
				toInsert.appendChild(doc.createTextNode(nodeList1.item(0).getTextContent()));
				nodeList2.item(i).appendChild(toInsert);
			}
			
		}
		return doc;
	}
}	
