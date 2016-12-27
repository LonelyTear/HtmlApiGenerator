package tool.htmlpage;

import java.io.File;
import java.util.Scanner;
/**
 * 主要用于�?>"之类的特殊字符转换成js里面�?amp;&gt;&lt;等形�?
 */
public class GenerateJSVariable {

	public static void main(String[] args) throws Exception{
		File f = new File("src/tool/htmlpage/input.txt");
		Scanner s = new Scanner(f);
		StringBuffer sb  = new StringBuffer();
		sb.append("var variable =");
		while(s.hasNext()){
			String line = "@#";
			line += s.next();
			line += "#@";
			line = line.replace("\"", "&quot;");
			line = line.replace("@#","\"");
			line = line.replace("#@","\"");
			line = line.replace("<", "&lt;");
			line = line.replace(">", "&gt;");
			line +="+";
			sb.append(line+"\n");
			
		}
		String str = sb.toString();
		str = str.substring(0, str.length()-2);
		str +=";";
		System.out.println(str);
		
	}
}
