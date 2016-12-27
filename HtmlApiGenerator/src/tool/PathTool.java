package tool;

import java.io.File;

public class PathTool {
	/*
	 * "D:/7��30/�㽭/��ҵ/Ͷ����ѯ/toPlatform"
	 * arg level >= 1  ,levelֵԽ��ȡ��·��Խ��
	 * level = 1, ����  	/toPlatform
	 * level = 2, ����  	/Ͷ����ѯ/toPlatform
	 * level = 3, ����  	/��ҵ/Ͷ����ѯ/toPlatform
	 * level = 4, ����  	/�㽭/��ҵ/Ͷ����ѯ/toPlatform
	 * level = 5, ����  	/7��30/�㽭/��ҵ/Ͷ����ѯ/toPlatform
	 */
	public static String getSomePathBylevel(File file ,int level ){
		String AbsolutePath = file.getAbsolutePath();
		String somePath = "";
		String[] nodePath = AbsolutePath.split("\\\\");
		for(int i = nodePath.length - level ; i <= nodePath.length-1 ; i++){
			somePath += "/"+nodePath[i];
		}
//		System.out.println(somePath);
		return somePath;
	}
	
	/*
	 *�õ�somePath�ĵ�һ���ļ�����
	 */
	public static String getFirstNameFromSomePath(String somePath){
		String[] paths = somePath.split("/");
		return paths[1];
	}
	
	
	
	/*
	 * ����Ŀ¼
	 */
	public static void makeDirs(File file){
		if (!(file.getParentFile().exists())) {
			file.getParentFile().mkdirs();
		}
	}
	
	// Body\RiskItem\ClaimAdjustReason
	public static String getLastDirName(String path){
		String[] dirNames = path.split("\\\\");
		return dirNames[dirNames.length-1];
	}
	
//	public static void main(String[] args) {
//		System.out.println(getLastDirName("Body\\RiskItem\\ClaimAdjustReason"));
//	}
	
}
