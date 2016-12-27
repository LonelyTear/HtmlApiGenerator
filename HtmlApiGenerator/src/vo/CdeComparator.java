package vo;

import java.util.Comparator;

/**
 * cdeVo 比较器 
 * @author King
 *
 */
	public class CdeComparator implements Comparator<PltmTransCdeVO>{

	    /**
	     * 如果o1小于o2,返回一个负数;如果o1大于o2，返回一个正数;如果他们相等，则返回0;
	     */
	    @Override
	    public int compare(PltmTransCdeVO o1, PltmTransCdeVO o2) {
	    	String str1 = "";
	    	String str2 = "";
	       if("1".equals( o1.getCFromToMrk() ) ){ 
	    	   str1 = o1.getCCdeFrom();
	    	   str2 = o2.getCCdeFrom();
	       }else if("0".equals(o1.getCFromToMrk())){
	    	   str1 = o1.getCCdeTo();
	    	   str2 = o2.getCCdeTo();
	       }
	       int flag  = str1.compareTo(str2);
	       return flag;
	    }

	}
