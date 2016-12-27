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
//		"D:/7��30��/�㽭/��ҵ/Ͷ����ѯ/toPlatform"
//		File file1 = new File("D:/api����/8��19API");//@@change allway
		File file1 = new File("D:/aappii");//@@change allway
		InputStream is = null;
		String msg;
		String extensionName = "";
		if(file1.exists()){
			System.out.println("�ҵ�D:/aappii�ļ���");
		}
		File sourceFile = new File("SourceFile");
		BackUp.copyFolder(sourceFile.getAbsolutePath(), HtmlTool.BASE_PATH);
		try {
//			File apiFile = new File("api");//@@ //api�ļ���
			List<File> dptHtmlFiles = new ArrayList<File>();
			for(File file2 : file1.listFiles()){
				if(file2.isFile()) continue;
				String province = file2.getName();//ʡ �ļ���(�㽭,����)
				System.out.println(province);
				List<File> prodHtmlFiles = new ArrayList<File>();
				for(File file3 : file2.listFiles()){
					if(file3.isFile()) continue;
					String prod = file3.getName();//��Ʒ �ļ��� (��ҵ,��ǿ)
					System.out.println(prod);
					List<File> requestHtmlFiles = new ArrayList<File>();
					for(File file4 : file3.listFiles()){
						if(file4.isFile()) continue;
						String request = file4.getName();//�������� �ļ���(Ͷ����ѯ,���Ѽ���)
						System.out.println(request);
						List<File> fromToHtmlFiles = new ArrayList<File>();
						for(File file5 : file4.listFiles()){
							if(file5.isFile()) continue;
							String fromTo = file5.getName();//fromTo �ļ��� (����,����)
							System.out.println(fromTo);
							List<File> fieldHtmlFiles = new ArrayList<File>();
							for(File file6 : file5.listFiles()){
								File leafFile = file6;//���յ�   xxx.xlsx xxx.xml�ļ�
								System.out.println(leafFile);
								extensionName = FilenameUtils.getExtension(leafFile.getName());
								if( ("xlsx".equals(extensionName) || "xls".equals(extensionName)) ){
									//��������ȥȡ��excel�ļ�����ͬ��txt�ο����ģ���ȡ�ñ��ĺ�ת���� ��Эƽ̨������Χ�ı�׼Map<String, List<Map<String, Object>>>
									Map<String, List<Map<String, Object>>> xmlMap = BoBoXml2MapTool.generateMap(leafFile);
									is = new FileInputStream(leafFile);
									//�õ��ַ�����ʽ��List
									List<String> cellsList = ExcelToListTool.exportListFromExcel(is, extensionName, 0);
									//����� List��������Map<PltmTransFieldVO, List<PltmTransCdeVO>> dataMap 
									Map<PltmTransFieldVO, List<PltmTransCdeVO>> dataMap = PltmTransFieldService.changeListToMap(cellsList);
									//��dataMap����generateFieldHtml()��������generateCdeHtml()
									File fieldHtmlFile = HtmlTool.generateFieldHtml(file5, dataMap ,xmlMap);
									fieldHtmlFiles.add(fieldHtmlFile);
								}
							}
							File fromToHtmlFile = HtmlTool.generateFromToHtml(file5,fieldHtmlFiles) ; //file5Ҫ�ĳ�Ŀ���ļ�,������Դ�ļ�
							fromToHtmlFiles.add(fromToHtmlFile);
						}
						File requestHtmlFile = HtmlTool.generateRequestHtml(file4,fromToHtmlFiles) ; //requestHtmlFileΪ����,����ҳ��
						requestHtmlFiles.add(requestHtmlFile);
					}
					File prodHtmlFile = HtmlTool.generateProdHtml(file3,requestHtmlFiles) ; //prodHtmlFildΪͶ����ѯ,���Ѽ���ҳ��
					prodHtmlFiles.add(prodHtmlFile);
				}
				File dptHtmlFile = HtmlTool.generateDptHtml(file2,prodHtmlFiles) ; //dptHtmlFileΪ��ҵ��,��ǿ��ҳ��
				dptHtmlFiles.add(dptHtmlFile);
			}
//			HtmlTool.generateApiHtml(file1, dptHtmlFiles);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (is != null) {
				is.close();
			}
			System.out.println("------------------ת�����----------------");
		}
	}

	
}
