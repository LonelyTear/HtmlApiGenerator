package vo;

import java.util.Comparator;

/**
 * cdeVo �Ƚ��� 
 * @author King
 *
 */
	public class CdeComparator implements Comparator<PltmTransCdeVO>{

	    /**
	     * ���o1С��o2,����һ������;���o1����o2������һ������;���������ȣ��򷵻�0;
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
