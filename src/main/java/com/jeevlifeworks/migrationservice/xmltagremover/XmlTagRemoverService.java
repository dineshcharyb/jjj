package com.jeevlifeworks.migrationservice.xmltagremover;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.jeevlifeworks.migrationservice.ApplicationLevel;

import au.com.bytecode.opencsv.CSVReader;

@Service
public class XmlTagRemoverService {

	@Value("${destinationFolderDir}")
	String destinationFolderDir;

	@Value("${csvFilePathForRemovalTags}")
	String csvFilePathForRemovalTags;

	@Autowired
	ApplicationLevel applicationLevel;

	public void xmlTagRemover(File file)
			throws SAXException, IOException, ParserConfigurationException, TransformerException {

		/**
		 * parsing XMl file
		 */
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(file);
		System.out.println("document parsed");
		String safetyreportid = null;

		/**
		 * Here getting safetyreportid and storing into a string ariable
		 */
		NodeList categories1 = doc.getElementsByTagName("safetyreportid");

		for (int i = 0; i < categories1.getLength(); i++) {

			Node node = categories1.item(0);
			System.out.println(node.getTextContent());
			safetyreportid = node.getTextContent();
		}

		/**
		 * Here reading the CSV file where we have stored all our elements to be removed
		 */

		//Build reader instance
		CSVReader reader = new CSVReader(new FileReader(csvFilePathForRemovalTags), ',', '"', 0);

		// Read all rows at once
		List<String[]> allRows = reader.readAll();

		NodeList categories = null;
		for (int i = 1; i < allRows.size(); i++) {
			categories = doc.getElementsByTagName(allRows.get(i)[1]);

			while (categories.getLength() > 0) {
				System.out.println("inside while");
				Node node = categories.item(0);
				/**
				 * Here generating UUID to store as Key in Map and we are taking this map from
				 * application level Here we have used LinkedHashMap to maintian insertionOrder
				 */
				String uuid = UUID.randomUUID().toString();
				/**
				 * Adding all the values to LinkedHashMap Map
				 */
				applicationLevel.elementsData.put(uuid, new Object[] { safetyreportid,
						node.getParentNode().getNodeName(), node.getNodeName(), node.getTextContent() });

				/**
				 * From Here we are removing the node
				 */
				node.getParentNode().removeChild(node);
			}
		}

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		System.out.println("creating file");
		StreamResult result = new StreamResult(new File(destinationFolderDir + "\\" + file.getName() + ".xml"));
		transformer.transform(source, result);
		System.out.println("close");

	}

	/**
	 * Function to write Excel file
	 * 
	 * @throws Exception
	 */
	public void writeExcel() throws Exception {
		// workbook object
		XSSFWorkbook workbook = new XSSFWorkbook();

		// spreadsheet object
		XSSFSheet spreadsheet = workbook.createSheet(" XML Elements ");

		// creating a row object
		XSSFRow row;

		Map<String, Object[]> elementsData = applicationLevel.elementsData;

		Set<String> keyid = elementsData.keySet();

		int rowid = 0;

		// writing the data into the sheets...

		for (String key : keyid) {

			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = elementsData.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}

		// .xlsx is the format for Excel Sheets...
		// writing the workbook into the file...
		FileOutputStream out = new FileOutputStream(new File(destinationFolderDir + "\\" + "elements.xlsx"));

		workbook.write(out);
		out.close();
	}
}
