package tool;

import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import vo.PltmTransCdeVO;
import vo.PltmTransFieldVO;

public class BoBoMap2XmlTool {
	private static int increment = 1 ;
	/**
	 * ˵���� ��Mapת���ɺ���Χͨ�ŵı�׼Xml��ʽString
	 * @param xmlMap ϵͳ��׼Map����ʽ������Map<String, List<Map<String, Object>>> 
	 * @param root ��Ҫ���ɵ�Xml��ʽ��String�ĸ��ڵ��� 
	 * @return String 
	 * @author �𽣲�
	 */
	public static void oswMap2Xml(
			String outKey ,
			OutputStreamWriter osw , 
			Map<String, List<Map<String, Object>>> xmlMap,
			Map<PltmTransFieldVO,List<PltmTransCdeVO>> dataMap
			) throws Exception{
		List<Map<String, Object>> list = xmlMap.get(outKey);
		if(list != null){
			if(list.size() > 1){													//�ж�list�Ĵ�С�Ƿ����1��
				osw.write("&emsp;&lt"+outKey+"_LIST&gt<br/>");								//����1����֮����� _LIST���м���
				oswAddList(outKey,osw,list,dataMap);
				osw.write("&emsp;&lt"+outKey+"_LIST&gt<br/>");								//_LIST��β
			}else{																//list��С <=1�Ļ� ֱ����ӣ���������һ��_List���м���
				oswAddList(outKey,osw,list,dataMap);
			}
		}
	}
	
	/*
	 * ��List<Map<String, Object>> list ��һ����ʽ ��ӵ� StringBuilder ��strBd��
	 * @param strBd		��list�е�����ƴ�ӳ�һ����ʽ��Ҫ���ַ�����
	 * @param outKey 	ϵͳ��׼Map��key
	 * @param list 		ϵͳ��׼Map�ĺ�key��Ӧ��value
	 * @return String 
	 * @author �𽣲�
	 */
	public static void oswAddList(
			String outKey ,
			OutputStreamWriter osw,
			List<Map<String, Object>> list ,
			Map<PltmTransFieldVO,List<PltmTransCdeVO>> dataMap
			) throws Exception{
		
		for(Map<String, Object> innerMap :list){										//ȡlist�е�ÿһ���ڲ�map
			osw.write("&emsp;&emsp;&lt"+outKey+"&gt<br/>");											//���VHL��BASE֮��Ľڵ���
			
			String idStr = "id";
			for(Map.Entry<String,Object> keyAndValue : innerMap.entrySet()){			//ȡmap�ļ���ֵ
				String key = keyAndValue.getKey();
				String value = keyAndValue.getValue().toString();
				PltmTransFieldVO fieldVO = null;
				
				boolean flag = false;//Ĭ��û�ҵ� false
				String fromToMark = "";
				String leafName = "";
//				if(key.equals( "VHL_BRAND") ){
//					System.out.println("hehe");
//				}
				for (Map.Entry<PltmTransFieldVO, List<PltmTransCdeVO>> field_cdeList : dataMap.entrySet()) {// ��ȡkey(field)����
					String fieldFrom = null;
					String fieldTo = null;
					fieldVO= field_cdeList.getKey();
					fromToMark = fieldVO.getCFromToMrk();// ����ת����־
					if ("1".equals(fieldVO.getCFromToMrk())) {// **
						fieldFrom = fieldVO.getCFieldFrom();
						fieldTo = fieldVO.getCFieldTo();
					} else {
						fieldFrom = fieldVO.getCFieldTo();
						fieldTo = fieldVO.getCFieldFrom();
					}
					if(fieldFrom.equals("VHL\\USE_TYPE;VHL\\VEHICLE_CATEGORY")){//�ж��һ����ʱֻ���VHL\USE_TYPE���бȽ�
						if((outKey+"\\"+key).equals("VHL\\USE_TYPE")){
							flag = true;
							break;
						}else{
							continue;
						}
							
					}
					//�ָ�fieldFrom,ȡ���һ��Ȼ��ȥ�Ƚϣ����������������
//					leafName = PathTool.getLastDirName(fieldFrom);
//					if(leafName.equals("VHL_BRAND")){
//						System.out.println("wuwu");
//					}
					if((outKey+"\\"+key).equals(fieldFrom)){
						flag = true;
						break;
					}
					if((outKey+"_LIST\\"+outKey+"\\"+key).equals(fieldFrom)){//����ṹ�����   �磺CAR_LIST\CAR\MODEL_DESC
						flag = true;
						break;
					}
				}
				osw.write("<div>");														//����ΪҶ�ӽڵ��ǩ��
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
				increment++;														//ֵ��ΪҶ�ӽڵ��ǩ������
			}
			osw.write("&emsp;&emsp;&lt/"+outKey+"&gt<br/>");											//VHL,BASE֮��Ľڵ��β
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
					//1.���һ�¶�����
					osw.write("&lt?xml version=\"1.0\"  encoding=\"GBK\"?&gt<br/>");
					if ("1".equals(fromToMark)) {
						osw.write("&ltPACKET type=\"REQUEST\" version=\"1.0\"&gt<br/>");
					} else {
						osw.write("&ltPACKET type=\"RESPONSE\" version=\"1.0\"&gt<br/>");
					}
					//$$���������λ��
					//2.������HEAD
					oswMap2Xml("HEAD",osw , xmlMap , dataMap);
					//3.������BODY
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
	 * ˵���� ��Mapת���ɺ���Χͨ�ŵı�׼Xml��ʽString
	 * @param dataMap ϵͳ��׼Map����ʽ������Map<String, List<Map<String, Object>>> 
	 * @param root ��Ҫ���ɵ�Xml��ʽ��String�ĸ��ڵ��� 
	 * @return String 
	 * @author �𽣲�
	 */
	public static String boboMap2Xml(Map<String, List<Map<String, Object>>> dataMap, String root){
		StringBuilder strBd = new StringBuilder();
		strBd.append("<"+root+">\n");												//��Ӹ���㣬һ�㶼ΪPACKET
		for(Map.Entry<String, List<Map<String, Object>>> outMap : dataMap.entrySet()){//������׼map
			String outKey = outMap.getKey();										//ȡ��׼map��key
			List<Map<String, Object>> list = outMap.getValue();						//ȡ��׼map��value
			if(list.size() > 1){													//�ж�list�Ĵ�С�Ƿ����1��
				strBd.append("\t<"+outKey+"_LIST>\n");								//����1����֮����� _LIST���м���
				addList2StrBd(strBd,outKey,list);
				strBd.append("\t<"+outKey+"_LIST>\n");								//_LIST��β
			}else{																	//list��С <=1�Ļ� ֱ����ӣ���������һ��_List���м���
				addList2StrBd(strBd,outKey,list);
			}
		}
		strBd.append("</"+root+">\n");												//������β
		return strBd.toString();
	}
	
	/*
	 * ��List<Map<String, Object>> list ��һ����ʽ ��ӵ� StringBuilder ��strBd��
	 * @param strBd		��list�е�����ƴ�ӳ�һ����ʽ��Ҫ���ַ�����
	 * @param outKey 	ϵͳ��׼Map��key
	 * @param list 		ϵͳ��׼Map�ĺ�key��Ӧ��value
	 * @return String 
	 * @author �𽣲�
	 */
	public static void addList2StrBd(StringBuilder strBd ,String outKey , List<Map<String, Object>> list ){
		for(Map<String, Object> innerMap :list){										//ȡlist�е�ÿһ���ڲ�map
			strBd.append("\t\t<"+outKey+">\n");											//���VHL��BASE֮��Ľڵ���
			for(Map.Entry<String,Object> keyAndValue : innerMap.entrySet()){			//ȡmap�ļ���ֵ
				strBd.append("\t\t\t<"+keyAndValue.getKey()+">");						//����ΪҶ�ӽڵ��ǩ��
				strBd.append(keyAndValue.getValue());									//ֵ��ΪҶ�ӽڵ��ǩ������
				strBd.append("</"+keyAndValue.getKey()+">\n"); 							//Ҷ�ӽڵ��β
			}
			strBd.append("\t\t</"+outKey+">\n");											//VHL,BASE֮��Ľڵ��β
		}
	}
}
