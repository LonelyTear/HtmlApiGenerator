package tool;

import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import vo.PltmTransCdeVO;
import vo.PltmTransFieldVO;

public class BoBoMap2XmlTool {
	private static int increment = 1 ;
	/**
	 * 说明： 把Map转换成和外围通信的标准Xml格式String
	 * @param xmlMap 系统标准Map，格式必须是Map<String, List<Map<String, Object>>> 
	 * @param root 将要生成的Xml形式的String的根节点名 
	 * @return String 
	 * @author 金剑波
	 */
	public static void oswMap2Xml(
			String outKey ,
			OutputStreamWriter osw , 
			Map<String, List<Map<String, Object>>> xmlMap,
			Map<PltmTransFieldVO,List<PltmTransCdeVO>> dataMap
			) throws Exception{
		List<Map<String, Object>> list = xmlMap.get(outKey);
		if(list != null){
			if(list.size() > 1){													//判断list的大小是否大于1，
				osw.write("&emsp;&lt"+outKey+"_LIST&gt<br/>");								//大于1则在之后添加 _LIST的中间结点
				oswAddList(outKey,osw,list,dataMap);
				osw.write("&emsp;&lt"+outKey+"_LIST&gt<br/>");								//_LIST封尾
			}else{																//list大小 <=1的话 直接添加，不用外套一层_List的中间结点
				oswAddList(outKey,osw,list,dataMap);
			}
		}
	}
	
	/*
	 * 把List<Map<String, Object>> list 按一定格式 添加到 StringBuilder 的strBd中
	 * @param strBd		把list中的数据拼接成一定格式需要的字符串类
	 * @param outKey 	系统标准Map的key
	 * @param list 		系统标准Map的和key对应的value
	 * @return String 
	 * @author 金剑波
	 */
	public static void oswAddList(
			String outKey ,
			OutputStreamWriter osw,
			List<Map<String, Object>> list ,
			Map<PltmTransFieldVO,List<PltmTransCdeVO>> dataMap
			) throws Exception{
		
		for(Map<String, Object> innerMap :list){										//取list中的每一个内部map
			osw.write("&emsp;&emsp;&lt"+outKey+"&gt<br/>");											//添加VHL，BASE之类的节点名
			
			String idStr = "id";
			for(Map.Entry<String,Object> keyAndValue : innerMap.entrySet()){			//取map的键和值
				String key = keyAndValue.getKey();
				String value = keyAndValue.getValue().toString();
				PltmTransFieldVO fieldVO = null;
				
				boolean flag = false;//默认没找到 false
				String fromToMark = "";
				String leafName = "";
//				if(key.equals( "VHL_BRAND") ){
//					System.out.println("hehe");
//				}
				for (Map.Entry<PltmTransFieldVO, List<PltmTransCdeVO>> field_cdeList : dataMap.entrySet()) {// 提取key(field)保存
					String fieldFrom = null;
					String fieldTo = null;
					fieldVO= field_cdeList.getKey();
					fromToMark = fieldVO.getCFromToMrk();// 内外转换标志
					if ("1".equals(fieldVO.getCFromToMrk())) {// **
						fieldFrom = fieldVO.getCFieldFrom();
						fieldTo = fieldVO.getCFieldTo();
					} else {
						fieldFrom = fieldVO.getCFieldTo();
						fieldTo = fieldVO.getCFieldFrom();
					}
					if(fieldFrom.equals("VHL\\USE_TYPE;VHL\\VEHICLE_CATEGORY")){//有多对一，此时只需对VHL\USE_TYPE进行比较
						if((outKey+"\\"+key).equals("VHL\\USE_TYPE")){
							flag = true;
							break;
						}else{
							continue;
						}
							
					}
					//分割fieldFrom,取最后一个然后去比较，如果相等则产生关联
//					leafName = PathTool.getLastDirName(fieldFrom);
//					if(leafName.equals("VHL_BRAND")){
//						System.out.println("wuwu");
//					}
					if((outKey+"\\"+key).equals(fieldFrom)){
						flag = true;
						break;
					}
					if((outKey+"_LIST\\"+outKey+"\\"+key).equals(fieldFrom)){//三层结构的情况   如：CAR_LIST\CAR\MODEL_DESC
						flag = true;
						break;
					}
				}
				osw.write("<div>");														//键作为叶子节点标签名
				if(flag == true){
					osw.write("<div id='"+ idStr + increment +"' class='field' >");
				}else{
					osw.write("<div id='"+ idStr + increment +"' class='none' >");
				}
						osw.write("&emsp;&emsp;&emsp;&lt"+key+"&gt");
						osw.write("<font color='#2E64FE'>"+value+"</font>");
						osw.write("&lt/"+key+"&gt");
				osw.write("</div>");
				if(flag == true){
					osw.write("<div id='"+ idStr + increment + idStr + increment +"' class='tips'>"+fieldVO.getCMemo()+"</div>");
				}
				osw.write("</div><br/>");
				increment++;														//值作为叶子节点标签体内容
			}
			osw.write("&emsp;&emsp;&lt/"+outKey+"&gt<br/>");											//VHL,BASE之类的节点封尾
		}
	}
	
	
	
	//_______________________________________________________________________________
		public static void outputPacketXml(
				String fromToMark ,
				OutputStreamWriter osw, 
				Map<String, List<Map<String, Object>>> xmlMap,
				Map<PltmTransFieldVO,List<PltmTransCdeVO>> dataMap
				){
			try {
				if (xmlMap != null) {
					//1.输出一下定义行
					osw.write("&lt?xml version=\"1.0\"  encoding=\"GBK\"?&gt<br/>");
					if ("1".equals(fromToMark)) {
						osw.write("&ltPACKET type=\"REQUEST\" version=\"1.0\"&gt<br/>");
					} else {
						osw.write("&ltPACKET type=\"RESPONSE\" version=\"1.0\"&gt<br/>");
					}
					//$$在这里调整位置
					//2.先生成HEAD
					oswMap2Xml("HEAD",osw , xmlMap , dataMap);
					//3.再生成BODY
					osw.write("&ltBODY&gt<br/>");
					oswMap2Xml("BASE",osw ,xmlMap , dataMap);
					oswMap2Xml("VHL",osw ,xmlMap , dataMap);
					oswMap2Xml("TAX",osw ,xmlMap , dataMap);
					oswMap2Xml("CAR",osw ,xmlMap , dataMap);
					oswMap2Xml("COVERAGE",osw ,xmlMap , dataMap);
					oswMap2Xml("ADJUST",osw ,xmlMap , dataMap);
					oswMap2Xml("CHECKCODE",osw ,xmlMap , dataMap);
					oswMap2Xml("SPECLIST",osw ,xmlMap , dataMap);
					oswMap2Xml("PAY",osw ,xmlMap , dataMap);
					oswMap2Xml("UNDRMSG",osw ,xmlMap , dataMap);
					osw.write("&lt/BODY&gt<br/>");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 说明： 把Map转换成和外围通信的标准Xml格式String
	 * @param dataMap 系统标准Map，格式必须是Map<String, List<Map<String, Object>>> 
	 * @param root 将要生成的Xml形式的String的根节点名 
	 * @return String 
	 * @author 金剑波
	 */
	public static String boboMap2Xml(Map<String, List<Map<String, Object>>> dataMap, String root){
		StringBuilder strBd = new StringBuilder();
		strBd.append("<"+root+">\n");												//添加根结点，一般都为PACKET
		for(Map.Entry<String, List<Map<String, Object>>> outMap : dataMap.entrySet()){//遍历标准map
			String outKey = outMap.getKey();										//取标准map的key
			List<Map<String, Object>> list = outMap.getValue();						//取标准map的value
			if(list.size() > 1){													//判断list的大小是否大于1，
				strBd.append("\t<"+outKey+"_LIST>\n");								//大于1则在之后添加 _LIST的中间结点
				addList2StrBd(strBd,outKey,list);
				strBd.append("\t<"+outKey+"_LIST>\n");								//_LIST封尾
			}else{																	//list大小 <=1的话 直接添加，不用外套一层_List的中间结点
				addList2StrBd(strBd,outKey,list);
			}
		}
		strBd.append("</"+root+">\n");												//根结点封尾
		return strBd.toString();
	}
	
	/*
	 * 把List<Map<String, Object>> list 按一定格式 添加到 StringBuilder 的strBd中
	 * @param strBd		把list中的数据拼接成一定格式需要的字符串类
	 * @param outKey 	系统标准Map的key
	 * @param list 		系统标准Map的和key对应的value
	 * @return String 
	 * @author 金剑波
	 */
	public static void addList2StrBd(StringBuilder strBd ,String outKey , List<Map<String, Object>> list ){
		for(Map<String, Object> innerMap :list){										//取list中的每一个内部map
			strBd.append("\t\t<"+outKey+">\n");											//添加VHL，BASE之类的节点名
			for(Map.Entry<String,Object> keyAndValue : innerMap.entrySet()){			//取map的键和值
				strBd.append("\t\t\t<"+keyAndValue.getKey()+">");						//键作为叶子节点标签名
				strBd.append(keyAndValue.getValue());									//值作为叶子节点标签体内容
				strBd.append("</"+keyAndValue.getKey()+">\n"); 							//叶子节点封尾
			}
			strBd.append("\t\t</"+outKey+">\n");											//VHL,BASE之类的节点封尾
		}
	}
}
