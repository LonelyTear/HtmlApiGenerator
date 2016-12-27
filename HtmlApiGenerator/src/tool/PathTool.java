package tool;

import java.io.File;

public class PathTool {
	/*
	 * "D:/7月30/浙江/商业/投保查询/toPlatform"
	 * arg level >= 1  ,level值越大取得路径越少
	 * level = 1, 返回  	/toPlatform
	 * level = 2, 返回  	/投保查询/toPlatform
	 * level = 3, 返回  	/商业/投保查询/toPlatform
	 * level = 4, 返回  	/浙江/商业/投保查询/toPlatform
	 * level = 5, 返回  	/7月30/浙江/商业/投保查询/toPlatform
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
	 *得到somePath的第一级文件夹名
	 */
	public static String getFirstNameFromSomePath(String somePath){
		String[] paths = somePath.split("/");
		return paths[1];
	}
	
	
	
	/*
	 * 创建目录
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
