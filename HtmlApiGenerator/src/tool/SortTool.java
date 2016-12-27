package tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import vo.CdeComparator;
import vo.FieldComparator;
import vo.PltmTransCdeVO;
import vo.PltmTransFieldVO;

public class SortTool {
	public static Map<PltmTransFieldVO, List<PltmTransCdeVO>> getTreeMap (Map<PltmTransFieldVO, List<PltmTransCdeVO>> inMap){
		Map<PltmTransFieldVO, List<PltmTransCdeVO>> outMap = new TreeMap<PltmTransFieldVO, List<PltmTransCdeVO>> (new FieldComparator());
		for (Map.Entry<PltmTransFieldVO, List<PltmTransCdeVO>> obj : inMap.entrySet()) {// Ã·»°key(field)±£¥Ê
			outMap.put(obj.getKey()	, obj.getValue())	;
		}
		return outMap;
	}
	
	public static List<PltmTransCdeVO> getArrayList (List<PltmTransCdeVO> inList){
		Collections.sort(inList, new CdeComparator());
		return inList;
	}

}
