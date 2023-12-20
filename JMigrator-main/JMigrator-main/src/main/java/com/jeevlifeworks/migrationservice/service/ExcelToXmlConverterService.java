package com.jeevlifeworks.migrationservice.service;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

@Service
public class ExcelToXmlConverterService {

	public String excelToXmlConverter() throws IOException, XPathExpressionException, ParserConfigurationException,
			SAXException, TransformerException {
		
		/**
		 * Reading Excel file
		 */
		FileInputStream file = new FileInputStream(new File("D:/XML Template/Jeev data/testing/Pregnancy_data2.xlsx"));

		System.out.println(file);

		// Create Workbook instance holding reference to .xlsx file
		XSSFWorkbook  workbook = new XSSFWorkbook(file);

		// Get desired sheet from the workbook
		XSSFSheet pregnancyDataSheet = workbook.getSheetAt(0);
		// Get desired sheet from the workbook
		XSSFSheet xpathSheet = workbook.getSheetAt(3);

		System.out.println("Fetching xml file");
		String filepath = "D:/XML Template/Jeev data/testing/sample_Pregnancydata.xml";
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(filepath);
		doc.getDocumentElement().normalize();

		/**
		 * For loop for the first sheet from this sheet only we are creating multiple
		 * xml files
		 */

		for (int i = 3; i <= 378; i++) {

			/**
			 * here getting i th row from the first sheet and for each row we are creating
			 * one xml file
			 */
			Row pregnancyDataExcelRow = pregnancyDataSheet.getRow(i);

			// here getting pregnancyregistry number to give a file name
			String pregnancyRegistrynumber = pregnancyDataExcelRow.getCell(1).getStringCellValue();

			/**
			 * This loop is for Xpath From here we are getting xpath and updating values to
			 * the xml tag
			 */
			for (int j = 1; j <= 68; j++) {
				System.out.println("3");

				/**
				 * here we are getting each xpath
				 */
				Row xpathRow = xpathSheet.getRow(j);
				/**
				 * here getting value of each xpath
				 */
				String excelXPath = xpathRow.getCell(4).getStringCellValue();

				/**
				 * here checking the xpathvalue cell type if it is numeric making it as String
				 * so that we will not get any problems further
				 */
				CellType cell = xpathRow.getCell(5).getCellTypeEnum();
				String xPathValue = null;
				switch (cell) {

				case NUMERIC:
					xpathRow.getCell(5).setCellType(CellType.STRING);
					xPathValue = xpathRow.getCell(5).getStringCellValue();
					break;
				case STRING:
					xPathValue = xpathRow.getCell(5).getStringCellValue();
					break;
				}
				xPathValue = xpathRow.getCell(5).getStringCellValue();

				System.out.println("Prnting xpath value     " + xPathValue);

				String xPathExcelvalue = null;

				/**
				 * here if the xpath value is null then we are not doing any action on that x
				 * path we are keeping that xpath value ass it is in Xml file
				 */
				if (!xPathValue.equals("null")) {

					/**
					 * here we are checking the xpathvalue and according to that we are taking the
					 * action
					 */
					if (xPathValue.equals("Excel")) {

						String sheetName = xpathRow.getCell(6).getStringCellValue();

						System.out.println("sheetName      " + sheetName);

						int sheetNo = 0;

						// here storing sheetno
						switch (sheetName) {

						case "Sheet1":
							sheetNo = 0;
							System.out.println("sheet ==0");
							break;
						case "Sheet2":
							sheetNo = 1;
							System.out.println("sheet ==1");
							break;
						case "Sheet3":
							sheetNo = 2;
							System.out.println("sheet ==2");
							break;
						}

						// Get desired sheet from the workbook
						XSSFSheet dynamicSheet = workbook.getSheetAt(sheetNo);

						if (sheetNo == 1 || sheetNo == 2) {

							System.out.println("Inside if");

							System.out.println("dynamix sheet last row         " + dynamicSheet.getLastRowNum());

							for (int k = 3; k < dynamicSheet.getLastRowNum(); k++) {

								System.out.println("Inside for");

								System.out.println(pregnancyRegistrynumber + "                   "
										+ dynamicSheet.getRow(k).getCell(1).getStringCellValue());

								if (pregnancyRegistrynumber
										.equalsIgnoreCase(dynamicSheet.getRow(k).getCell(1).getStringCellValue())) {

									System.out.println("Inside for if");

									// xPathExcelvalue=pregnancyDataExcelRow.getCell((int)
									// xpathRow.getCell(7).getNumericCellValue()).getStringCellValue();

									xPathExcelvalue = dynamicSheet.getRow(k)
											.getCell((int) xpathRow.getCell(7).getNumericCellValue())
											.getStringCellValue();

									System.out.println("xpathvalue inside for if  " + xPathExcelvalue);

									break;

								} else {

									if (sheetNo == 1) {

										xPathExcelvalue = "Product Used For Unknown Indication";
									} else if (sheetNo == 2) {
										xPathExcelvalue = "dummy";

									}

								}

							}
						} else {
							pregnancyDataExcelRow.getCell((int) xpathRow.getCell(7).getNumericCellValue())
									.setCellType(CellType.STRING);
							xPathExcelvalue = pregnancyDataExcelRow
									.getCell((int) xpathRow.getCell(7).getNumericCellValue()).getStringCellValue();
						}
					}
					/**
					 * here if the xpath value is systemdate then we will take the system date
					 */
					else if (xPathValue.equals("System Date")) {
						Date date = new Date();
						DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
						xPathExcelvalue = dateFormat.format(date);

					}
					/**
					 * here getting the Excel date format code according to that date if the date is
					 * blank then directly mapping to blank
					 */
					else if (xPathValue.equals("ExcelDateFormat")) {
						CellType cell1 = pregnancyDataExcelRow.getCell((int) xpathRow.getCell(7).getNumericCellValue())
								.getCellTypeEnum();
						System.out.println("Cell1      " + cell1.name());
						switch (cell) {

						case BLANK:
							xPathExcelvalue = "";
							break;
						case STRING:
							pregnancyDataExcelRow.getCell((int) xpathRow.getCell(7).getNumericCellValue())
									.setCellType(CellType.STRING);

							String sdate = pregnancyDataExcelRow
									.getCell((int) xpathRow.getCell(7).getNumericCellValue()).getStringCellValue();
							if (!sdate.isEmpty()) {
								System.out.println("sdate    " + sdate);
								String dateFormatcode = checkDateFormat(sdate);
								System.out.println("dateformatcode      " + dateFormatcode);
								xPathExcelvalue = dateFormatcode;

							} else {
								xPathExcelvalue = "";
							}
							break;
						}
					}
					/**
					 * here converting the Excel date format according to that date if the date is
					 * blank then directly mapping to blank
					 */
					else if (xPathValue.equals("ExcelDate")) {

						CellType cell1 = pregnancyDataExcelRow.getCell((int) xpathRow.getCell(7).getNumericCellValue())
								.getCellTypeEnum();
						System.out.println("Cell2      " + cell1.name());
						switch (cell1) {

						case BLANK:
							xPathExcelvalue = "";
							break;
						case STRING:
							pregnancyDataExcelRow.getCell((int) xpathRow.getCell(7).getNumericCellValue())
									.setCellType(CellType.STRING);
							String sdate = pregnancyDataExcelRow
									.getCell((int) xpathRow.getCell(7).getNumericCellValue()).getStringCellValue();
							if (!sdate.isEmpty()) {
								
								String dateFormatcode = checkDateFormat(sdate);
								System.out.println("dateformatcode      " + dateFormatcode);
								String formattedDate = checkDate(dateFormatcode, sdate);
								System.out.println("formattedDate      " + formattedDate);

								xPathExcelvalue = formattedDate;
							} else {
								xPathExcelvalue = "";
							}
							break;
						}

					} else {
						xPathExcelvalue = xPathValue;
					}

					System.out.println("Xpath Excel value      " + xPathExcelvalue);

					/**
					 * here if the xPathExcel Value is not null then updating the base Xml file
					 */
					if (xPathExcelvalue != null) {
						XPath xPath1 = XPathFactory.newInstance().newXPath();
						Node node = (Node) xPath1.evaluate(excelXPath, doc, XPathConstants.NODE);

						// Set the node content
						node.setTextContent(xPathExcelvalue);
					}
				}

			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(
					"D:/XML Template/Jeev data/testing/testing1/Safetyreport " + pregnancyRegistrynumber + ".xml"));
			transformer.transform(source, result);

			System.out.println("Done");

		}
		return "Updated";

	}

	public String checkDateFormat(String sdate) {
		if (sdate.contains("??-??")) {
			return "602";
		} else if (sdate.contains("??")) {
			return "610";
		} else {
			return "102";
		}
	}

	public String checkDate(String dateFormatcode, String sdate) {
		System.out.println("Inside checkdate      " + sdate);
		if (sdate.contains("??-???")) {
			System.out.println("first");
			String sdate1 = sdate.replace("??-???", "01-Jan");

			System.out.println("Inside checkdate first if    " + sdate1);
			Date date1 = new Date(sdate1);
			DateFormat dateFormatter = new SimpleDateFormat("yyyy");
			String output = dateFormatter.format(date1);
			return output;
		} else if (sdate.contains("??")) {
			System.out.println("Second");

			String sdate1 = sdate.replace("??", "01");
			System.out.println("sdate after replacing      " + sdate1);
			Date date1 = new Date(sdate1);
			System.out.println("Inside checkdate second if    " + sdate);
			DateFormat dateFormatter = new SimpleDateFormat("yyyyMM");
			String output = dateFormatter.format(date1);
			return output;
		} else {
			Date date1 = new Date(sdate);
			DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
			String output = dateFormatter.format(date1);
			return output;
		}
	}
}

