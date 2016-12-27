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
	 * 最终生成多份cde.html
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
			FileOutputStream fos = new FileOutputStream(dest);// 开始生成目标Html
			osw = new OutputStreamWriter(fos, "gbk");
			osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
			osw.write("<link rel='stylesheet' type='text/css' href='../../../../../css/dpt/dpt.css'>");
			osw.write("<title>" + PathTool.getLastDirName(fieldVO.getCFieldTo()) + "---代码转换" + "</title>" + "</head>");
			osw.write("<body class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
			osw.write("<font size='5' color='red'>" + PathTool.getLastDirName(fieldVO.getCFieldTo()) + "------" + fieldVO.getCMemo() + "</font>");
			osw.write("<table border='1'>");
			osw.write("<tr bgcolor='#81DAF5'>");
			osw.write("<th>外围代码</th> <th>外围名称</th> <th>------</th> <th>对应平台代码</th> <th>对应平台名称</th> ");
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
	 * 生成field.html,在已知的 文件夹 / 下 建立一个 html 文件,其中有a标签指向 cde.html 文件 arg refFile
	 * 代表toFrom文件夹
	 */
	public static File generateFieldHtml(File refFile, Map<PltmTransFieldVO, List<PltmTransCdeVO>> inMap, Map<String, List<Map<String, Object>>> xmlMap)
			throws Exception {
		String fromToMark = "";// 内外转换标志
		File dest = null;
		String lastPathName = PathTool.getSomePathBylevel(refFile, 1);
		String somePath = PathTool.getSomePathBylevel(refFile, 4);
		if (lastPathName.contains("请求")) {// **
			lastPathName = "请求上平台";
		} else {
			lastPathName = "平台返回";
		}
		try {
			// 2,开始插数据
			int i = 1;
			// 这里应该生成一个网页,专门放@cde超链接 start
			dest = new File(BASE_PATH, somePath + "/" + FIELD + "/" + FIELD + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// 开始生成目标Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../../../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../../../css/field/field.css'>");
				osw.write("<script src='../../../../../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../../../../../js/field.js'></script>");
				osw.write("<title>" + "字段转换" + "</title>" + "</head>");
				osw.write("<body class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
				osw.write("<font size='5' color='red'>" + "</font>");
				osw.write("<table border='2'>");
				osw.write("<tr bgcolor='#64FE2E'>");// #888888
				osw.write("<tr bgcolor='#64FE2E'><td colspan='5' align='center'><b><font size='8'>"+lastPathName+"</font></b></td></tr>");// #888888
				osw.write("<th>字段名(外围)</th> <th>字段名(代理系统)</th> <th>必填</th> <th>默认值</th> <th>说明</th>");
				osw.write("</tr>");
				int j = 1;
				
				Map<PltmTransFieldVO, List<PltmTransCdeVO>> dataMap = SortTool.getTreeMap(inMap);
				for (Map.Entry<PltmTransFieldVO, List<PltmTransCdeVO>> field_cdeList : dataMap.entrySet()) {// 提取key(field)保存
					String fieldFrom = null;
					String fieldTo = null;
					PltmTransFieldVO fieldVO = field_cdeList.getKey();
					fromToMark = fieldVO.getCFromToMrk();// 内外转换标志
					if ("1".equals(fieldVO.getCFromToMrk())) {// **
						fieldFrom = fieldVO.getCFieldFrom();
						fieldTo = fieldVO.getCFieldTo();
					} else {
						fieldFrom = fieldVO.getCFieldTo();
						fieldTo = fieldVO.getCFieldFrom();
					}
					fieldVO.setCPkId("" + i);
					// System.out.println(fieldVO + "###" + fieldVO.getCMemo() +
					// "  存入数据库");
					
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
					osw.write("</td>");// @@特殊处理一下
					osw.write("<td>");
					osw.write(fieldVO.getCFieldDeft());
					osw.write("</td>");
					osw.write("<td>");
					osw.write(fieldVO.getCMemo() + "&nbsp;");
					if (cdeFile != null) {// 此处不等于null就表明是需要转码的字段
						osw.write("<a href='" + "../" + CDE + "/" + cdeFile.getName() + "'>" + "<font color='red'>" + "参考代码" + "</font>" + "<a>");
					}
					osw.write("</td>");
					osw.write("</tr>");
				}
				osw.write("</table>");// table结束

				String strk = "id";
				int k = 1;
				//输出xml
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
			// 这里应该生成一个网页,专门放@cde超链接 end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * 生成 fromTo.html ,在已知的 文件夹 / 下 建立一个 html 文件,其中有a标签指向 field.html 文件
	 */
	public static File generateFromToHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		File directLocationFile = null;
		String lastPathName = PathTool.getSomePathBylevel(refFile, 1);
		String somePath = PathTool.getSomePathBylevel(refFile, 4);
//		if (lastPathName.contains("上")) {// **
//			lastPathName = "请求";
//		} else {
//			lastPathName = "返回";
//		}
		try {
			// 2,开始插数据
			int i = 1;
			// 这里应该生成一个网页,专门放@field超链接 start
			dest = new File(BASE_PATH, somePath + "/" + refFile.getName() + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// 开始生成目标Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../../css/other/other.css'>");
				osw.write("<script src='../../../../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../../../../js/other.js'></script>");
				osw.write("<title>" + lastPathName + "</title>" + "</head>");
				osw.write("<body align='center' class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
//				osw.write("<div style='background-image:url(../../../picture/fromOrTo.gif);background-repeat:no-repeat;margin-left:auto; margin-right:auto;width:481px;height:94px'></div>");
				for (File file : fileList) {// 提取key(field)保存
					directLocationFile = file;
//					osw.write("<a href='" + "./" + FIELD + "/" + directLocationFile.getName() + "'>");
//						osw.write("加载完本页面直接跳转");//不再加载图片,而是加载完本页面内容后直接跳转
//					osw.write("<a><br/>");
				}
				osw.write("</body>" + "</html>");
				osw.write("<script>");
				osw.write("$(function(){");
				osw.write("window.location.href = '"+"./" + FIELD + "/" + directLocationFile.getName() + "'");//加载完后直接跳转js
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
			// 这里应该生成一个网页,专门放@field超链接 end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * requestHtmlFile为请求,返回页面<br/>
	 * 生成 投保查询.html ,在已知的 文件夹 / 下 建立一个 html 文件,其中有a标签指向 fromTo.html 文件
	 */
	public static File generateRequestHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		String somePath = PathTool.getSomePathBylevel(refFile, 3);
		try {
			// 2,开始插数据
			int i = 1;
			// 这里应该生成一个网页,专门放@field超链接 start
			dest = new File(BASE_PATH, somePath + "/" + refFile.getName() + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// 开始生成目标Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../../css/other/other.css'>");
				osw.write("<script src='../../../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../../../js/other.js'></script>");
				osw.write("<title>" + "请求 or 返回" + "</title>" + "</head>");
				osw.write("<body align='center' class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
				osw.write("<div style='background-image:url(../../../picture/fromOrTo.gif);background-repeat:no-repeat;margin-left:auto; margin-right:auto;width:481px;height:94px'></div>");
				for (File file : fileList) {// 提取key(field)保存
					osw.write("<a href='" + "." + PathTool.getSomePathBylevel(file.getParentFile(), 1) + "/" + file.getName() + "'>"); 
					if(file.getName().contains("请求")){
						osw.write("<img class='request'/>");
					}
					if(file.getName().contains("返回")){
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
			// 这里应该生成一个网页,专门放@field超链接 end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * prodHtmlFild为投保查询,保费计算页面<br/>
	 * 生成 商业.html ,在已知的 文件夹 / 下 建立一个 html 文件,其中有a标签指向 投保查询.html 文件
	 */
	public static File generateProdHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		String somePath = PathTool.getSomePathBylevel(refFile, 2);
		try {
			// 2,开始插数据
			int i = 1;
			// 这里应该生成一个网页,专门放@field超链接 start
			dest = new File(BASE_PATH, somePath + "/" + refFile.getName() + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// 开始生成目标Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../../css/other/other.css'>");
				osw.write("<script src='../../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../../js/other.js'></script>");
				osw.write("<title>" + "请求类型" + "</title>" + "</head>");
				osw.write("<body align='center' class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
				osw.write("<div style='background-image:url(../../picture/request.gif);background-repeat:no-repeat;margin-left:auto; margin-right:auto;width:481px;height:94px'></div>");
				int j = 1;
				for (File file : fileList) {// 提取key(field)保存
					osw.write("<a href='" + "." + PathTool.getSomePathBylevel(file.getParentFile(), 1) + "/" + file.getName() + "'>"); 
					if(file.getName().contains("投保询价")){
						osw.write("<img class='policyQuery'/>");
					}
					if(file.getName().contains("保费计算")){
						osw.write("<img class='premiumCalculation'/>");
					}
					if(file.getName().contains("问题校验")){
						osw.write("<img class='check'/>");
					}
					if(file.getName().contains("车船税")){
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
			// 这里应该生成一个网页,专门放@field超链接 end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * dptHtmlFile为商业险,交强险页面<br/>
	 * 生成 浙江.html ,在已知的 文件夹 / 下 建立一个 html 文件,其中有a标签指向 商业.html 文件
	 */
	public static File generateDptHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		String somePath = PathTool.getSomePathBylevel(refFile, 1);
		try {
			// 2,开始插数据
			int i = 1;
			// 这里应该生成一个网页,专门放@field超链接 start
			dest = new File(BASE_PATH, PathTool.getSomePathBylevel(refFile, 1) + "/" + refFile.getName() + ".html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// 开始生成目标Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>");
				osw.write("<link rel='stylesheet' type='text/css' href='../css/dpt/dpt.css'>");
				osw.write("<link rel='stylesheet' type='text/css' href='../css/other/other.css'>");
				osw.write("<script src='../js/jquery-1.4.2.min.js'></script>");
				osw.write("<script src='../js/other.js'></script>");
				osw.write("<title>" + "产品险别" + "</title>" + "</head>");
				osw.write("<body align='center' class='" + FileConstants.DPTPINYINMAP.get(PathTool.getFirstNameFromSomePath(somePath)) + "'>");
//				osw.write("<img src='../picture/prod.gif'/><br/>");
				osw.write("<div style='background-image:url(../picture/prod.gif);background-repeat:no-repeat;margin-left:auto; margin-right:auto;width:481px;height:94px'></div>");
//				int j = 1;
				for (File file : fileList) {// 提取key(field)保存
//					if (++j % 2 == 0) {
//						osw.write("<tr bgcolor ='#81F781'>");// #FFFFFF
//					} else {
//						osw.write("<tr bgcolor ='#F8E0EC'>");// #CCCCCC
//					}
					osw.write("<a href='" + "." + PathTool.getSomePathBylevel(file.getParentFile(), 1) + "/" + file.getName() + "'>"); 
					if(file.getName().contains("商业")){
						osw.write("<img class='commerical'/>");
					}
					if(file.getName().contains("交强")){
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
			// 这里应该生成一个网页,专门放@field超链接 end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}

	/*
	 * 生成 API.html ,在已知的 文件夹 / 下 建立一个 html 文件,其中有a标签指向 浙江.html 文件
	 */
	public static File generateApiHtml(File refFile, List<File> fileList) throws Exception {
		File dest = null;
		try {
			// 2,开始插数据
			int i = 1;
			// 这里应该生成一个网页,专门放@field超链接 start
			dest = new File(BASE_PATH, PathTool.getSomePathBylevel(refFile, 0) + "/" + "API〖" + refFile.getName() + "〗.html");
			OutputStreamWriter osw = null;
			if (!(dest.getParentFile().exists())) {
				dest.getParentFile().mkdirs();
			}
			try {
				FileOutputStream fos = new FileOutputStream(dest);// 开始生成目标Html
				osw = new OutputStreamWriter(fos, "gbk");
				osw.write("<!DOCTYPE html>" + "<html>" + "<head>" + "<title>" + "机构(省/市)" + "</title>" + "</head>" + "<body>");
				osw.write("<font size='5' color='red'>" + "</font>");
				osw.write("<table border='2'>");
				osw.write("<tr bgcolor='#64FE2E'>");// #888888
				osw.write("<th>机构(省/市)</th>");
				osw.write("</tr>");
				int j = 1;
				for (File file : fileList) {// 提取key(field)保存
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
			// 这里应该生成一个网页,专门放@field超链接 end
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println("finished");
		return dest;
	}
}
