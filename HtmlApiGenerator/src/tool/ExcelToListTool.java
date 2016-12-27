package tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

  
/** 
 * Excel��� 
 * ��excel����ֻҪ�����ݵ���ȫ��������List<String>��ȥ��ÿ����Ԫ���������#_#�ָ�
 * @author �𽣲� 
 * @version 1.0 
 * @since 1.0 
 */  
public  class ExcelToListTool {  
	
	
    /** 
     * Excel 2003 
     */  
    private final static String XLS = "xls";  
    /** 
     * Excel 2007 
     */  
    private final static String XLSX = "xlsx";  
    /** 
     * �ָ��� 
     */  
    private final static String SEPARATOR = "#_#";  
  
    /** 
     * ��Excel�ļ���Sheet������List 
     *  
     * @param file 
     * @param sheetNum 
     * @return 
     */  
    public static List<String> exportListFromExcel(File file, int sheetNum)  
            throws IOException {  
        return exportListFromExcel(new FileInputStream(file),  
                FilenameUtils.getExtension(file.getName()), sheetNum);  
    }  
  
    /** 
     * ��Excel����Sheet������List 
     *  
     * @param is 
     * @param extensionName 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    public static List<String> exportListFromExcel(InputStream is,  
            String extensionName, int sheetNum) throws IOException {  
    	org.apache.poi.ss.usermodel.Workbook workbook = null;  
  
        if (extensionName.toLowerCase().equals(XLS)) {  
            workbook = new org.apache.poi.hssf.usermodel.HSSFWorkbook(is);  
        } else if (extensionName.toLowerCase().equals(XLSX)) {  
            workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(is);  
        }  
        return exportListFromExcel(workbook, sheetNum);  
    }  
  
    /** 
     * ��ָ����Sheet������List 
     *  
     * @param workbook 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    private static List<String> exportListFromExcel(org.apache.poi.ss.usermodel.Workbook workbook,  
            int sheetNum) {  
  
        Sheet sheet = workbook.getSheetAt(sheetNum);  
  
        // ������ʽ���  
        FormulaEvaluator evaluator = workbook.getCreationHelper()  
                .createFormulaEvaluator();  
  
        List<String> list = new ArrayList<String>();  
  
        int minRowIx = sheet.getFirstRowNum();  
        int maxRowIx = sheet.getLastRowNum();  
        for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {  
            Row row = sheet.getRow(rowIx);  
            StringBuilder sb = new StringBuilder();  
  
            short minColIx = row.getFirstCellNum();  
            short maxColIx = row.getLastCellNum();  
            if(maxColIx == -1){
            	continue;
            }
            
            boolean nullFlag = true;
            for (short colIx = 0; colIx < maxColIx; colIx++) { 
            	Cell cell = row.getCell(new Integer(colIx));
//            	System.out.println(cell.toString());
            	if( (cell == null) || ("".equals(cell.toString())) ){
//            		System.out.println("null"); // doNothing
            	}else{
            		nullFlag = false;
            	}
            }
            if(nullFlag){
            	continue;
            }
            sb.append((rowIx+1)+SEPARATOR);
            for (short colIx = 0; colIx < maxColIx; colIx++) {  
                Cell cell = row.getCell(new Integer(colIx));
                CellValue cellValue = evaluator.evaluate(cell);  
                if (cellValue == null) {  
                	sb.append(SEPARATOR);
                    continue;  
                }else{
                	 if(cell.getCellComment()!= null){
//                     	System.out.println(cell.getCellComment().getString().getIndexOfFormattingRun(1));
                     }
                }
                // ������ʽ���������ֻ����Boolean��Numeric��String�����������ͣ��������Error��  
                // �����������ͣ����ݹٷ��ĵ�����ȫ���Ժ���http://poi.apache.org/spreadsheet/eval.html  
                switch (cellValue.getCellType()) {  
                case Cell.CELL_TYPE_BOOLEAN:  
                    sb.append(cellValue.getBooleanValue()+SEPARATOR);  
                    break;  
                case Cell.CELL_TYPE_NUMERIC:  
                    // ������������ͻᱻת��Ϊ�������ͣ���Ҫ�б�����ִ���  
                    if (DateUtil.isCellDateFormatted(cell)) {  
                        sb.append(cell.getDateCellValue()+SEPARATOR);  
                    } else {  
                        sb.append(new Double(cellValue.getNumberValue()).intValue()+SEPARATOR);  
                    }  
                    break;  
                case Cell.CELL_TYPE_STRING:  
                    sb.append( cellValue.getStringValue()+SEPARATOR);  
                    break;  
                case Cell.CELL_TYPE_FORMULA:  
                    break;  
                case Cell.CELL_TYPE_BLANK:  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                    break;  
                default:  
                    break;  
                }  
            }  
//            System.out.println(sb); //##
            list.add(sb.toString()); 
        }  
        return list;  
    }  
    
    public static void main(String[] args) {
		try {
			ExcelToListTool s =new ExcelToListTool();
			File file = new File("src/platformTemplate2.xlsx");
//			exportListFromExcel(new FileInputStream(("C:/Users/King/Desktop/n.xlsx")), FilenameUtils.getExtension(file.getName()) , 0 );
			exportListFromExcel(file,0);
//			exportListFromExcel(new File("C:/Users/King/Desktop/sy_out_to_pltm .xlsx"),0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} 