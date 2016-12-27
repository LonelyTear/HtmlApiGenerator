package vo;

import java.util.Comparator;

/**
 * fieldVO比较器
 * @author King
 *
 */
	public class FieldComparator implements Comparator<PltmTransFieldVO>{

	    /**
	     * 如果o1小于o2,返回一个负数;如果o1大于o2，返回一个正数;如果他们相等，则返回0;
	     */
	    @Override
	    public int compare(PltmTransFieldVO o1, PltmTransFieldVO o2) {
	    	String str1 = "";
	    	String str2 = "";
	       if("1".equals( o1.getCFromToMrk() ) ){ 
	    	   str1 = o1.getCFieldFrom();
	    	   str2 = o2.getCFieldFrom();
	       }else if("0".equals(o1.getCFromToMrk())){
	    	   str1 = o1.getCFieldTo();
	    	   str2 = o2.getCFieldTo();
	       }
//	       System.out.println("fieldO1= "+ str1+"   ###    "+"fieldO2= "+str2);
	       int flag  = str1.compareTo(str2);
	       return flag;
	    }

	}
