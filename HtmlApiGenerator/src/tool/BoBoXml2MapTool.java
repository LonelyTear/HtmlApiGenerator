package tool;

import java.io.File;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import vo.PltmTransCdeVO;
import vo.PltmTransFieldVO;

public class BoBoXml2MapTool {
	
	private static Map<String, List<Map<String, Object>>> convertFile2Map(File file) {

		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		Element root = document.getRootElement();//
		Map<String, List<Map<String, Object>>> outerMap = new TreeMap<String, List<Map<String, Object>>>();
		String keyName = "";
		for (Iterator businessVoIter = root.elementIterator("HEAD"); businessVoIter.hasNext();) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Element businessElement = (Element) businessVoIter.next(); // ����BusinessVo��������businessVoMap��
			keyName = businessElement.getName();
			Map<String, Object> innerMap = new TreeMap<String, Object>();
			for (Iterator m = businessElement.elementIterator(); m.hasNext();) {// ����AttributeVo
																				// ��������avList��
				Element leaf = (Element) m.next(); // ��ӦNode
				// System.out.println(leaf.getName());// ��ӡ��PROD_NO
				innerMap.put(leaf.getName(), leaf.getText());
			}
			list.add(innerMap);
			outerMap.put(keyName, list);
		}

		for (Iterator businessVoListIter = root.elementIterator("BODY"); businessVoListIter.hasNext();) {
			Element businessVoElement = (Element) businessVoListIter.next();
			// System.out.println(businessVoElement.getName());//``(body)
			// System.out.println(e1.getName());//
			for (Iterator businessVoIter = businessVoElement.elementIterator(); businessVoIter.hasNext();) {
				List list = new ArrayList();
				Element businessElement = (Element) businessVoIter.next(); // ����BusinessVo��������businessVoMap��
				if (businessElement.getName().endsWith("_LIST")) {
					// System.out.println(businessElement.getName());//��ӡ��COVERAGE_LIST
					for (Iterator node = businessElement.elementIterator(); node.hasNext();) {// ����DataObjVo��������dovList��
						Element innerNode = (Element) node.next(); // ��Ӧ BASE
						// System.out.println(dataObjElement.getName());//��ӡ��BASE

						keyName = innerNode.getName();
						Map innerMap = new TreeMap();
						for (Iterator m = innerNode.elementIterator(); m.hasNext();) {// ����AttributeVo
																						// ��������avList��
							Element leaf = (Element) m.next(); // ��ӦNode
							// System.out.println(leaf.getName()+leaf.getText());//
							// ��ӡ��PROD_NO
							innerMap.put(leaf.getName(), leaf.getText());
						}
						list.add(innerMap);
						outerMap.put(keyName, list);
					}
				} else { // û��LIST��ֱ���Ƕ���<VHL>~~~</VHL>
				// System.out.println(businessElement.getName());//��ӡ��TGT

					keyName = businessElement.getName();
					Map innerMap = new TreeMap();
					for (Iterator m = businessElement.elementIterator(); m.hasNext();) {// ����AttributeVo
																						// ��������avList��
						Element leaf = (Element) m.next(); // ��ӦNode
						// System.out.println(leaf.getName());// ��ӡ��PROD_NO
						innerMap.put(leaf.getName(), leaf.getText());
					}
					list.add(innerMap);
					outerMap.put(keyName, list);
				}
			}
		}
		return outerMap;
	}
	
	private static File convertExcelFileName2XmlFileName(File file){
		File parentFile = file.getParentFile();
		String fileName = file.getName();
		String extensionName = FilenameUtils.getExtension(file.getName());
		File dest = new File(parentFile,fileName.replace(extensionName, "xml"));
		if(dest.exists()){
			return dest;
		}else{
			return null;
		}
	}
	
	/*
	 * ���⿪��
	 */
	public static Map<String, List<Map<String, Object>>> generateMap(File file){
		File convertFile = convertExcelFileName2XmlFileName(file);
		if(convertFile == null)
			return null;
		Map<String, List<Map<String, Object>>> map = convertFile2Map(convertFile);
		return map;
	}
	
	
	
	
	
}