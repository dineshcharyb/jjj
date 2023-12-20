package com.jeevlifeworks.migrationservice.controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import com.jeevlifeworks.migrationservice.service.ExcelToXmlConverterService;



public class ExcelToXmlConverterController {
	
	@Autowired
	ExcelToXmlConverterService excelToXmlConverterService;
	
	@RequestMapping("/exceltoxml")
	public String excelToXmlConverter() throws IOException, XPathExpressionException, ParserConfigurationException, SAXException, TransformerException {
		return excelToXmlConverterService.excelToXmlConverter();
	}

}
