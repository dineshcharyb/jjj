package com.jeevlifeworks.migrationservice.xmleditorwithmultifunction;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

@RestController
public class XmlEditorWithMultiFunctionController {
	
	@Autowired
	XmlEditorWithMultiFunctionService xmlEditorWithMultiFunctionServivce;
	
//	public String xmlEditorWithMultiFunction() throws SAXException, IOException, ParserConfigurationException, TransformerException, XPathExpressionException { 
//		return xmlEditorWithMultiFunctionServivce.xmlEditorWithMultiFunction();
//	}

}
