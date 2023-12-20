package com.jeevlifeworks.migrationservice.XMLSplitter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Service
public class XMLSplitterService {
    /**
     * method which create xml file for each safetyreportid 
     * @param xmlTemplate
     * @param destFolder
     */
//	public void writeXml(XMLTemplate xmlTemplate,String destFolder) throws ParserConfigurationException, TransformerException {
//
//		String safetyReportId = null;
//
//		for (int i = 0; i < xmlTemplate.getSafetyReport().getLength(); i++) {
//
//			// Get Document Builder
//			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
//
//			// Build Document
//			Document doc = documentBuilder.newDocument();
//
//			// creting root element
//			Element ichicsr = doc.createElement("ichicsr");
//			doc.setXmlVersion(xmlTemplate.getVersion());
//			if (xmlTemplate.getRootElementAttributeName() != null) {
//				ichicsr.setAttribute(xmlTemplate.getRootElementAttributeName(),
//						xmlTemplate.getRootElementAttributeValue());
//			}
//			doc.appendChild(ichicsr);
//			Node ichicsrmessageheaderNode = doc.importNode(xmlTemplate.getIchicsrMessageHeader().item(0), true);
//			ichicsr.appendChild(ichicsrmessageheaderNode);
//			Node safetyReportNode = doc.importNode(xmlTemplate.getSafetyReport().item(i), true);
//
//			ichicsr.appendChild(safetyReportNode);
//
//			Element eElement = (Element) safetyReportNode;
//			safetyReportId = eElement.getElementsByTagName("safetyreportid").item(0).getTextContent();
//			// safetyReportId
//			System.out.println("safetyreportid" + safetyReportId);
//
//			// create the xml file
//			// transform the DOM Object to an XML File
//			TransformerFactory transformerFactory = TransformerFactory.newInstance();
//			Transformer transformer = transformerFactory.newTransformer();
//
//			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
//			// transformer.setOutputProperty(OutputKeys.ENCODING, "no");
//			DOMImplementation domImpl = doc.getImplementation();
//			DocumentType doctype = domImpl.createDocumentType("doctype", xmlTemplate.getPublicId(),
//					xmlTemplate.getSystemId());
//
//			// transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
//			// doctype.getPublicId());
//		//	transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doctype.getSystemId());
//
//			DOMSource domSource = new DOMSource(doc);
//
//			if(safetyReportId.contains("/" ) || safetyReportId.contains("?")) {
//				safetyReportId=safetyReportId.replace("/","_");
//				safetyReportId=safetyReportId.replace("?", "_");
//				safetyReportId=safetyReportId+"_Modified";
//			}
//
//			StreamResult streamResult = new StreamResult(new File(destFolder +"/"+"SafetyReport_"+ safetyReportId + ".xml"));
//
//			transformer.transform(domSource, streamResult);
//
//			System.out.println("Done creating XML File");
//		}
//
//	}
	
	/**
	 * method which parse the xml file and split the xml file based on safetyreport
	 * @param file1
	 * @param fileName
	 * @param destFolder
	 */
//	public void readXml1(File file1, String fileName,String destFolder)
//			throws ParserConfigurationException, SAXException, IOException, TransformerException {
//
//		// Creating an object to pass it to another function
//		XMLTemplate xmlTemplate = new XMLTemplate();
//
//		// Get Document Builder
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = factory.newDocumentBuilder();
//
//		// Build Document
//		Document document = builder.parse(file1);
//		System.out.println("Document parsed");
//
//		// Normalize the XML Structure; It's just too important !!
//		document.getDocumentElement().normalize();
//
//		// Here comes the root node
//		Element root = document.getDocumentElement();
//
//		// Here checking the attributes
//		if (root.hasAttributes()) {
//			// get attributes names and values
//			NamedNodeMap nodeMap = root.getAttributes();
//			for (int i = 0; i < nodeMap.getLength(); i++) {
//
//				Node node = nodeMap.item(i);
//				xmlTemplate.setRootElementAttributeName(node.getNodeName());
//				xmlTemplate.setRootElementAttributeValue(node.getNodeValue());
//
//			}
//
//		}
//
//		// getting ichicsrmessageheader node
//		NodeList ichicsrmessageheader = root.getElementsByTagName("ichicsrmessageheader");
//		// getting all safetyreport nodes
//		NodeList safetyReportList = root.getElementsByTagName("safetyreport");
//
//		xmlTemplate.setRootNode(root);
//		xmlTemplate.setIchicsrMessageHeader(ichicsrmessageheader);
//		xmlTemplate.setSafetyReport(safetyReportList);
//		xmlTemplate.setSystemId(document.getDoctype().getSystemId());
//		System.out.println( "System id    "+document.getDoctype().getSystemId());
//		xmlTemplate.setPublicId(document.getDoctype().getPublicId());
//		System.out.println("Public id     "+document.getDoctype().getPublicId());
//		xmlTemplate.setVersion(document.getXmlVersion());
//
//		// calling write Xml function
//
////		this.writeXml(xmlTemplate,destFolder);
//
// }
}