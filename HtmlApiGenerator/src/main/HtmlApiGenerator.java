package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import service.PltmTransFieldService;
import tool.BackUp;
import tool.BoBoXml2MapTool;
import tool.ExcelToListTool;
import tool.HtmlTool;
import vo.PltmTransCdeVO;
import vo.PltmTransFieldVO;


/**
 * 
 */

public class HtmlApiGenerator  {
	
	private static final Logger logger = Logger.getLogger(HtmlApiGenerator.class);

	public static void main (String args[])throws Exception {
//		"D:/7月30号/浙江/商业/投保查询/toPlatform"
//		File file1 = new File("D:/api车险/8月19API");//@@change allway
		File file1 = new File("D:/aappii");//@@change allway
		InputStream is = null;
		String msg;
		String extensionName = "";
		if(file1.exists()){
			System.out.println("找到D:/aappii文件夹");
		}
		File sourceFile = new File("SourceFile");
		BackUp.copyFolder(sourceFile.getAbsolutePath(), HtmlTool.BASE_PATH);
		try {
//			File apiFile = new File("api");//@@ //api文件夹
			List<File> dptHtmlFiles = new ArrayList<File>();
			for(File file2 : file1.listFiles()){
				if(file2.isFile()) continue;
				String province = file2.getName();//省 文件夹(浙江,北京)
				System.out.println(province);
				List<File> prodHtmlFiles = new ArrayList<File>();
				for(File file3 : file2.listFiles()){
					if(file3.isFile()) continue;
					String prod = file3.getName();//产品 文件夹 (商业,交强)
					System.out.println(prod);
					List<File> requestHtmlFiles = new ArrayList<File>();
					for(File file4 : file3.listFiles()){
						if(file4.isFile()) continue;
						String request = file4.getName();//请求类型 文件夹(投保查询,保费计算)
						System.out.println(request);
						List<File> fromToHtmlFiles = new ArrayList<File>();
						for(File file5 : file4.listFiles()){
							if(file5.isFile()) continue;
							String fromTo = file5.getName();//fromTo 文件夹 (请求,返回)
							System.out.println(fromTo);
							List<File> fieldHtmlFiles = new ArrayList<File>();
							for(File file6 : file5.listFiles()){
								File leafFile = file6;//最终的   xxx.xlsx xxx.xml文件
								System.out.println(leafFile);
								extensionName = FilenameUtils.getExtension(leafFile.getName());
								if( ("xlsx".equals(extensionName) || "xls".equals(extensionName)) ){
									//这里试着去取和excel文件名相同的txt参考报文，读取该报文后转换成 行协平台接收外围的标准Map<String, List<Map<String, Object>>>
									Map<String, List<Map<String, Object>>> xmlMap = BoBoXml2MapTool.generateMap(leafFile);
									is = new FileInputStream(leafFile);
									//得到字符串形式的List
									List<String> cellsList = ExcelToListTool.exportListFromExcel(is, extensionName, 0);
									//处理该 List用以生成Map<PltmTransFieldVO, List<PltmTransCdeVO>> dataMap 
									Map<PltmTransFieldVO, List<PltmTransCdeVO>> dataMap = PltmTransFieldService.changeListToMap(cellsList);
									//把dataMap传入generateFieldHtml()方法用于generateCdeHtml()
									File fieldHtmlFile = HtmlTool.generateFieldHtml(file5, dataMap ,xmlMap);
									fieldHtmlFiles.add(fieldHtmlFile);
								}
							}
							File fromToHtmlFile = HtmlTool.generateFromToHtml(file5,fieldHtmlFiles) ; //file5要改成目标文件,而不是源文件
							fromToHtmlFiles.add(fromToHtmlFile);
						}
						File requestHtmlFile = HtmlTool.generateRequestHtml(file4,fromToHtmlFiles) ; //requestHtmlFile为请求,返回页面
						requestHtmlFiles.add(requestHtmlFile);
					}
					File prodHtmlFile = HtmlTool.generateProdHtml(file3,requestHtmlFiles) ; //prodHtmlFild为投保查询,保费计算页面
					prodHtmlFiles.add(prodHtmlFile);
				}
				File dptHtmlFile = HtmlTool.generateDptHtml(file2,prodHtmlFiles) ; //dptHtmlFile为商业险,交强险页面
				dptHtmlFiles.add(dptHtmlFile);
			}
//			HtmlTool.generateApiHtml(file1, dptHtmlFiles);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (is != null) {
				is.close();
			}
			System.out.println("------------------转换完成----------------");
		}
	}

	
}
