package tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import vo.FileConstants;
import vo.PltmTransCdeVO;
import vo.PltmTransFieldVO;

public class HtmlTool {
	public static String BASE_PATH = "D:/API";
	// public static String BASE_PATH ="d:/html/";
	public static String CDE = "cde";
	public static String FIELD = "field";
	// public static File CDE_DIR = new File(BASE_PATH + "cde/");
	// public static File FIELD_DIR = new File(BASE_PATH + "field/");
	
	static {
		File basePath = new File(BASE_PATH);
		if (!(basePath.exists())) {
			basePath.mkdirs();
		}
		File css = new File("src/css");
	}

	/*
	 * �������ɶ��cde.html
	 */
	public static File generateCdeHtml(File refFile, PltmTransFieldVO fieldVO, List<PltmTransCdeVO> cdeList) {
		File dest = null;
		String cdeHtmlName = null;
		String somePath = PathTool.getSomePathBylevel(refFile, 4);
		if ("1".equals(fieldVO.getCFromToMrk())) {// **
			cdeHtmlName = fieldVO.getCFieldTo();
		} else {
			cdeHtmlName = PathTool.getLastDirName(fieldVO.getCFieldFrom());
		}
		if ((cdeList == null) || (cdeList.size() == 0))
			return null;
		dest = new File(BASE_PATH, somePath + "/" + CDE + "/" + PathTool.getLastDirName(fieldVO.getCFieldTo()) + ".html");
		OutputStreamWriter osw = null;
		if (!(dest.getParentFile().exists())) {
			dest.getParentFile().mkdirs();
		}
		try {
			String cdeFrom = null;
			String nameFrom = null;
			String cdeTo = null;
			String nameTo = null;
			FileOutputStream fos = new FileOutputStream(dest);// ��ʼ����Ŀ��Html
			osw = new OutputStreamWriter(fos, "gbk");
			osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
			osw.write("<link rel='stylesheet' type='text/css' href='../../../../../css/dpt/dpt.css'>");
			osw.write("<title>" + PathTool.getLastDirName(fieldVO.getCFieldTo()) + "---����ת��" + "</title>" + "</head>");
			osw.write("<body class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
			osw.write("<font size='5' color='red'>" + PathTool.getLastDirName(fieldVO.getCFieldTo()) + "------" + fieldVO.getCMemo() + "</font>");
			osw.write("<table border='1'>");
			osw.write("<tr bgcolor='#81DAF5'>");
			osw.write("<th>��Χ����</th> <th>��Χ����</th> <th>------</th> <th>��Ӧƽ̨����</th> <th>��Ӧƽ̨����</th> ");
			osw.write("</tr'>");
			int j = 1;
			for (int i = 0; i < cdeList.size(); i++) {
				PltmTransCdeVO cdeVo = cdeList.get(i);

				if ("1".equals(fieldVO.getCFromToMrk())) {// **
					cdeFrom = cdeVo.getCCdeFrom();
					nameFrom = cdeVo.getCNmeFrom();
					cdeTo = cdeVo.getCCdeTo();
					nameTo = cdeVo.getCNmeTo();
				} else {
					cdeTo = cdeVo.getCCdeFrom();
					nameTo = cdeVo.getCNmeFrom();
					cdeFrom = cdeVo.getCCdeTo();
					nameFrom = cdeVo.getCNmeTo();
				}
				if (++j % 2 == 0) {
					osw.write("<tr bgcolor ='#A9F5F2'>");
				} else {
					osw.write("<tr bgcolor ='#F8E0E6'>");
				}
				osw.write("<td>" + cdeFrom + "</td>");
				osw.write("<td>" + nameFrom + "</td>");
				osw.write("<td>" + "" + "</td>");
				osw.write("<td>" + cdeTo + "</td>");
				osw.write("<td>" + nameTo + "</td>");
				osw.write("</tr>");
			}
			osw.write("</table>");
			osw.write("</body>" + "</html>");
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return dest;
	}

	/*
	 * ����field.html,����֪�� �ļ��� / �� ����һ�� html �ļ�,������a��ǩָ�� cde.html �ļ� arg refFile
	 * ����toFrom�ļ���
	 */
	public static File generateFieldHtml(File refFile, Map<PltmTransFieldVO, List<PltmTransCdeVO>> inMap, Map<String, List<Map<String, Object>>> xmlMap)
			throws Exception {
		String fromToMark = "";// ����ת����־
		File dest = null;
		String lastPathName = PathTool.getSomePathBylevel(refFile, 1);
		String somePath = PathTool.getSomePathBylevel(refFile, 4);
		if (lastPathName.contains("����")) {// **
			lastPathName = "������ƽ̨";
		} else {
			lastPathName = "ƽ̨����";
		}
		try {
			// 2,��ʼ������
			int i = 1;
			// ����Ӧ������һ����ҳ,ר�ŷ�@cde������ start
			dest = new File(BASE_PATH, somePath + "/" + FIELD + "/" + FIELD + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// ��ʼ����Ŀ��Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../../../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../../../css/field/field.css'>");
				osw.write("<script src='../../../../../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../../../../../js/field.js'></script>");
				osw.write("<title>" + "�ֶ�ת��" + "</title>" + "</head>");
				osw.write("<body class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
				osw.write("<font size='5' color='red'>" + "</font>");
				osw.write("<table border='2'>");
				osw.write("<tr bgcolor='#64FE2E'>");// #888888
				osw.write("<tr bgcolor='#64FE2E'><td colspan='5' align='center'><b><font size='8'>"+lastPathName+"</font></b></td></tr>");// #888888
				osw.write("<th>�ֶ���(��Χ)</th> <th>�ֶ���(����ϵͳ)</th> <th>����</th> <th>Ĭ��ֵ</th> <th>˵��</th>");
				osw.write("</tr>");
				int j = 1;
				
				Map<PltmTransFieldVO, List<PltmTransCdeVO>> dataMap = SortTool.getTreeMap(inMap);
				for (Map.Entry<PltmTransFieldVO, List<PltmTransCdeVO>> field_cdeList : dataMap.entrySet()) {// ��ȡkey(field)����
					String fieldFrom = null;
					String fieldTo = null;
					PltmTransFieldVO fieldVO = field_cdeList.getKey();
					fromToMark = fieldVO.getCFromToMrk();// ����ת����־
					if ("1".equals(fieldVO.getCFromToMrk())) {// **
						fieldFrom = fieldVO.getCFieldFrom();
						fieldTo = fieldVO.getCFieldTo();
					} else {
						fieldFrom = fieldVO.getCFieldTo();
						fieldTo = fieldVO.getCFieldFrom();
					}
					fieldVO.setCPkId("" + i);
					// System.out.println(fieldVO + "###" + fieldVO.getCMemo() +
					// "  �������ݿ�");
					
					List<PltmTransCdeVO> cdeList = SortTool.getArrayList( field_cdeList.getValue() );
					// if ((cdeList == null) || (cdeList.size() == 0)) {
					// // doNothing
					// } else {

					File cdeFile = HtmlTool.generateCdeHtml(refFile, fieldVO, cdeList);// @cde
					if (++j % 2 == 0) {
						osw.write("<tr bgcolor ='#81F781'>");// #FFFFFF
					} else {
						osw.write("<tr bgcolor ='#F8E0EC'>");// #CCCCCC
					}
					osw.write("<td>");
					osw.write(fieldFrom);
					osw.write("</td>");
					osw.write("<td>");
					osw.write(fieldTo);
					osw.write("</td>");
					osw.write("<td>");
					osw.write(fieldVO.getCIsNullableMrk());
					osw.write("</td>");// @@���⴦��һ��
					osw.write("<td>");
					osw.write(fieldVO.getCFieldDeft());
					osw.write("</td>");
					osw.write("<td>");
					osw.write(fieldVO.getCMemo() + "&nbsp;");
					if (cdeFile != null) {// �˴�������null�ͱ�������Ҫת����ֶ�
						osw.write("<a href='" + "../" + CDE + "/" + cdeFile.getName() + "'>" + "<font color='red'>" + "�ο�����" + "</font>" + "<a>");
					}
					osw.write("</td>");
					osw.write("</tr>");
				}
				osw.write("</table>");// table����

				String strk = "id";
				int k = 1;
				//���xml
				BoBoMap2XmlTool.outputPacketXml(fromToMark, osw, xmlMap,dataMap);
				osw.write("</body>" + "</html>");
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (osw != null) {
					try {
						osw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			// ����Ӧ������һ����ҳ,ר�ŷ�@cde������ end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * ���� fromTo.html ,����֪�� �ļ��� / �� ����һ�� html �ļ�,������a��ǩָ�� field.html �ļ�
	 */
	public static File generateFromToHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		File directLocationFile = null;
		String lastPathName = PathTool.getSomePathBylevel(refFile, 1);
		String somePath = PathTool.getSomePathBylevel(refFile, 4);
//		if (lastPathName.contains("��")) {// **
//			lastPathName = "����";
//		} else {
//			lastPathName = "����";
//		}
		try {
			// 2,��ʼ������
			int i = 1;
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ start
			dest = new File(BASE_PATH, somePath + "/" + refFile.getName() + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// ��ʼ����Ŀ��Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../../css/other/other.css'>");
				osw.write("<script src='../../../../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../../../../js/other.js'></script>");
				osw.write("<title>" + lastPathName + "</title>" + "</head>");
				osw.write("<body align='center' class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
//				osw.write("<div style='background-image:url(../../../picture/fromOrTo.gif);background-repeat:no-repeat;margin-left:auto; margin-right:auto;width:481px;height:94px'></div>");
				for (File file : fileList) {// ��ȡkey(field)����
					directLocationFile = file;
//					osw.write("<a href='" + "./" + FIELD + "/" + directLocationFile.getName() + "'>");
//						osw.write("�����걾ҳ��ֱ����ת");//���ټ���ͼƬ,���Ǽ����걾ҳ�����ݺ�ֱ����ת
//					osw.write("<a><br/>");
				}
				osw.write("</body>" + "</html>");
				osw.write("<script>");
				osw.write("$(function(){");
				osw.write("window.location.href = '"+"./" + FIELD + "/" + directLocationFile.getName() + "'");//�������ֱ����תjs
				osw.write("})");
				osw.write("</script>");
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (osw != null) {
					try {
						osw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * requestHtmlFileΪ����,����ҳ��<br/>
	 * ���� Ͷ����ѯ.html ,����֪�� �ļ��� / �� ����һ�� html �ļ�,������a��ǩָ�� fromTo.html �ļ�
	 */
	public static File generateRequestHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		String somePath = PathTool.getSomePathBylevel(refFile, 3);
		try {
			// 2,��ʼ������
			int i = 1;
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ start
			dest = new File(BASE_PATH, somePath + "/" + refFile.getName() + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// ��ʼ����Ŀ��Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../css/other/other.css'>");
				osw.write("<script src='../../../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../../../js/other.js'></script>");
				osw.write("<title>" + "���� or ����" + "</title>" + "</head>");
				osw.write("<body align='center' class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
				osw.write("<div style='background-image:url(../../../picture/fromOrTo.gif);background-repeat:no-repeat;margin-left:auto; margin-right:auto;width:481px;height:94px'></div>");
				for (File file : fileList) {// ��ȡkey(field)����
					osw.write("<a href='" + "." + PathTool.getSomePathBylevel(file.getParentFile(), 1) + "/" + file.getName() + "'>"); 
					if(file.getName().contains("����")){
						osw.write("<img class='request'/>");
					}
					if(file.getName().contains("����")){
						osw.write("<img class='response'/>");
					}
					osw.write("<a><br/>");
				}
				osw.write("</body>" + "</html>");
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (osw != null) {
					try {
						osw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * prodHtmlFildΪͶ����ѯ,���Ѽ���ҳ��<br/>
	 * ���� ��ҵ.html ,����֪�� �ļ��� / �� ����һ�� html �ļ�,������a��ǩָ�� Ͷ����ѯ.html �ļ�
	 */
	public static File generateProdHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		String somePath = PathTool.getSomePathBylevel(refFile, 2);
		try {
			// 2,��ʼ������
			int i = 1;
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ start
			dest = new File(BASE_PATH, somePath + "/" + refFile.getName() + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// ��ʼ����Ŀ��Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../css/other/other.css'>");
				osw.write("<script src='../../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../../js/other.js'></script>");
				osw.write("<title>" + "��������" + "</title>" + "</head>");
				osw.write("<body align='center' class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
				osw.write("<div style='background-image:url(../../picture/request.gif);background-repeat:no-repeat;margin-left:auto; margin-right:auto;width:481px;height:94px'></div>");
				int j = 1;
				for (File file : fileList) {// ��ȡkey(field)����
					osw.write("<a href='" + "." + PathTool.getSomePathBylevel(file.getParentFile(), 1) + "/" + file.getName() + "'>"); 
					if(file.getName().contains("Ͷ��ѯ��")){
						osw.write("<img class='policyQuery'/>");
					}
					if(file.getName().contains("���Ѽ���")){
						osw.write("<img class='premiumCalculation'/>");
					}
					if(file.getName().contains("����У��")){
						osw.write("<img class='check'/>");
					}
					if(file.getName().contains("����˰")){
						osw.write("<img class='localTax'/>");
					}
					osw.write("<a><br/>");
					
					
					osw.write("</tr>");
				}
				osw.write("</body>" + "</html>");
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (osw != null) {
					try {
						osw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * dptHtmlFileΪ��ҵ��,��ǿ��ҳ��<br/>
	 * ���� �㽭.html ,����֪�� �ļ��� / �� ����һ�� html �ļ�,������a��ǩָ�� ��ҵ.html �ļ�
	 */
	public static File generateDptHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		String somePath = PathTool.getSomePathBylevel(refFile, 1);
		try {
			// 2,��ʼ������
			int i = 1;
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ start
			dest = new File(BASE_PATH, PathTool.getSomePathBylevel(refFile, 1) + "/" + refFile.getName() + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// ��ʼ����Ŀ��Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../css/other/other.css'>");
				osw.write("<script src='../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../js/other.js'></script>");
				osw.write("<title>" + "��Ʒ�ձ�" + "</title>" + "</head>");
				osw.write("<body align='center' class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
//				osw.write("<img src='../picture/prod.gif'/><br/>");
				osw.write("<div style='background-image:url(../picture/prod.gif);background-repeat:no-repeat;margin-left:auto; margin-right:auto;width:481px;height:94px'></div>");
//				int j = 1;
				for (File file : fileList) {// ��ȡkey(field)����
//					if (++j % 2 == 0) {
//						osw.write("<tr bgcolor ='#81F781'>");// #FFFFFF
//					} else {
//						osw.write("<tr bgcolor ='#F8E0EC'>");// #CCCCCC
//					}
					osw.write("<a href='" + "." + PathTool.getSomePathBylevel(file.getParentFile(), 1) + "/" + file.getName() + "'>"); 
					if(file.getName().contains("��ҵ")){
						osw.write("<img class='commerical'/>");
					}
					if(file.getName().contains("��ǿ")){
						osw.write("<img class='compulsory'/>");
					}
					osw.write("</a><br/>");
				}
				osw.write("</body>" + "</html>");
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (osw != null) {
					try {
						osw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * ���� API.html ,����֪�� �ļ��� / �� ����һ�� html �ļ�,������a��ǩָ�� �㽭.html �ļ�
	 */
	public static File generateApiHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		try {
			// 2,��ʼ������
			int i = 1;
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ start
			dest = new File(BASE_PATH, PathTool.getSomePathBylevel(refFile, 0) + "/" + "API��" + refFile.getName() + "��.html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// ��ʼ����Ŀ��Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>" + "<title>" + "����(ʡ/��)" + "</title>" + "</head>" + "<body>");
				osw.write("<font size='5' color='red'>" + "</font>");
				osw.write("<table border='2'>");
				osw.write("<tr bgcolor='#64FE2E'>");// #888888
				osw.write("<th>����(ʡ/��)</th>");
				osw.write("</tr>");
				int j = 1;
				for (File file : fileList) {// ��ȡkey(field)����
					if (++j % 2 == 0) {
						osw.write("<tr bgcolor ='#81F781'>");// #FFFFFF
					} else {
						osw.write("<tr bgcolor ='#F8E0EC'>");// #CCCCCC
					}
					osw.write("<td>");
					osw.write("<a href='" + "." + PathTool.getSomePathBylevel(file.getParentFile(), 1) + "/" + file.getName() + "'>" + "<font color='red'>"
							+ file.getName() + "</font>" + "<a>");
					osw.write("</td>");
					osw.write("</tr>");
				}
				osw.write("</table>");
				osw.write("</body>" + "</html>");
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				if (osw != null) {
					try {
						osw.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			// ����Ӧ������һ����ҳ,ר�ŷ�@field������ end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}
}
