package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vo.PltmConstants;
import vo.PltmTransCdeVO;
import vo.PltmTransFieldVO;

public class PltmTransFieldService {

	

	

	/*
	 * 1.把cdllsList转换成dataMap
	 * 
	 */
	public static Map<PltmTransFieldVO, List<PltmTransCdeVO>> changeListToMap(List<String> cellsList) throws Exception {
		//1start
		Map<PltmTransFieldVO, List<PltmTransCdeVO>> dataMap = new HashMap<PltmTransFieldVO, List<PltmTransCdeVO>>();
		String cmpId = null;
		String dptCde = null;
		String prodNo = null;
		String reqType = null;
		String fromToMrk = null;
		// 获取Excel文件头部公共数据
		String[] line = cellsList.get(1).split("#_#");
		int size = cellsList.get(1).split("#_#").length;
		for (int j = 1; j < size; j++) {
			if( (line[j] == null) ||("".equals(line[j])) ) {
				throw new Exception("第"+line[0]+"行，第1到第5个单元格内容缺失，请您仔细检查规范");
			}
		}
		//第一行机构,产品, 请求类型,内外转换标志的处理
		cmpId = line[1];
		dptCde = line[2];
		prodNo = line[3];
		reqType = line[4];
		fromToMrk = line[5];
		//excel表中第四行开始要导入真实库中的数据，3+1 = 4
		for (int i = 3; i < cellsList.size(); i++) {
			line = cellsList.get(i).split("#_#");
			String[] cells = cellsList.get(i).split("#_#");
			PltmTransFieldVO fieldVo = new PltmTransFieldVO(cmpId, dptCde, prodNo, reqType, fromToMrk, PltmConstants.ENABLE);
			for (int j = 1; j <= 5 ; j++) {
				try{
					if( (line[j] == null) ||("".equals(line[j])) ) {
						throw new Exception("第"+line[0]+"行，第1到第5个单元格内容缺失，请您仔细检查规范");
					}
				}catch(ArrayIndexOutOfBoundsException outBoundE){
					throw new Exception("第"+line[0]+"行，第1到第5个单元格内容缺失，请您仔细检查规范");
				}
			}
			fieldVo.setCFieldFrom(cells[1]);
			fieldVo.setCFieldTo(cells[2]);
			fieldVo.setCMemo(cells[3]);
			fieldVo.setCIsNullableMrk(cells[4]);
			fieldVo.setCFieldDeft(cells[5]);
			if (cells.length > 6) {
				fieldVo.setCTransferMrk(PltmConstants.TRANSFER_ON); 
			} else {
				fieldVo.setCTransferMrk(PltmConstants.TRANSFER_OFF);
			}
			List<PltmTransCdeVO> list = new ArrayList<PltmTransCdeVO>();
			// 判断在dataMap当中该键下面的List是否有数据
			if (dataMap.get(fieldVo) != null) {
				list = dataMap.get(fieldVo);
			}
			if (cells.length > 6) {
				for (int j = 6; j <= 9 ; j++) {
					try{
						if( (line[j] == null) ||("".equals(line[j])) ) {
							throw new Exception("第"+line[0]+"行，第6到第9个单元格内容缺失，请您仔细检查规范");
						}
					}catch(Exception e){//@@,突然发现 这里还可以捕到数组下标越界异常,所以需要再套一层
						throw new Exception("第"+line[0]+"行，第6到第9个单元格内容缺失，请您仔细检查规范");
					}
				}	
				PltmTransCdeVO cdeVo = new PltmTransCdeVO();
				cdeVo.setCCmpId(cmpId);
				cdeVo.setCDptCde(dptCde);
				cdeVo.setCProdNo(prodNo);
				cdeVo.setCReqType(reqType);
				cdeVo.setCFromToMrk(fromToMrk);
				cdeVo.setCEfftMrk(PltmConstants.ENABLE);
				cdeVo.setCCdeFrom(cells[6]);
				cdeVo.setCCdeTo(cells[7]);
				cdeVo.setCNmeFrom(cells[8]);
				cdeVo.setCNmeTo(cells[9]);
				list.add(cdeVo);
			}
			dataMap.put(fieldVo, list);
		}
		return dataMap;
	}
	

	
	
}
