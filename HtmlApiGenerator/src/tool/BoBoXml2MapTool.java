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
			Element businessElement = (Element) businessVoIter.next(); // 生成BusinessVo，并放入businessVoMap中
			keyName = businessElement.getName();
			Map<String, Object> innerMap = new TreeMap<String, Object>();
			for (Iterator m = businessElement.elementIterator(); m.hasNext();) {// 生成AttributeVo
																				// ，并放入avList中
				Element leaf = (Element) m.next(); // 对应Node
				// System.out.println(leaf.getName());// 打印出PROD_NO
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
				Element businessElement = (Element) businessVoIter.next(); // 生成BusinessVo，并放入businessVoMap中
				if (businessElement.getName().endsWith("_LIST")) {
					// System.out.println(businessElement.getName());//打印出COVERAGE_LIST
					for (Iterator node = businessElement.elementIterator(); node.hasNext();) {// 生成DataObjVo，并放入dovList中
						Element innerNode = (Element) node.next(); // 对应 BASE
						// System.out.println(dataObjElement.getName());//打印出BASE

						keyName = innerNode.getName();
						Map innerMap = new TreeMap();
						for (Iterator m = innerNode.elementIterator(); m.hasNext();) {// 生成AttributeVo
																						// ，并放入avList中
							Element leaf = (Element) m.next(); // 对应Node
							// System.out.println(leaf.getName()+leaf.getText());//
							// 打印出PROD_NO
							innerMap.put(leaf.getName(), leaf.getText());
						}
						list.add(innerMap);
						outerMap.put(keyName, list);
					}
				} else { // 没有LIST，直接是对象<VHL>~~~</VHL>
				// System.out.println(businessElement.getName());//打印出TGT

					keyName = businessElement.getName();
					Map innerMap = new TreeMap();
					for (Iterator m = businessElement.elementIterator(); m.hasNext();) {// 生成AttributeVo
																						// ，并放入avList中
						Element leaf = (Element) m.next(); // 对应Node
						// System.out.println(leaf.getName());// 打印出PROD_NO
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
	 * 对外开放
	 */
	public static Map<String, List<Map<String, Object>>> generateMap(File file){
		File convertFile = convertExcelFileName2XmlFileName(file);
		if(convertFile == null)
			return null;
		Map<String, List<Map<String, Object>>> map = convertFile2Map(convertFile);
		return map;
	}
	
	
	
	
	
}