package vo;

import java.util.Comparator;

/**
 * fieldVO�Ƚ���
 * @author King
 *
 */
	public class FieldComparator implements Comparator<PltmTransFieldVO>{

	    /**
	     * ���o1С��o2,����һ������;���o1����o2������һ������;���������ȣ��򷵻�0;
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
