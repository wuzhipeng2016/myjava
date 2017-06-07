  
  
import java.io.FileInputStream;  
import java.util.Iterator;  
  
import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.poifs.filesystem.POIFSFileSystem;  
import org.apache.poi.ss.usermodel.Cell;  
  
/** 
 * @see poi 设计原理介绍 
 * @author liuqing 
 * 
 */  
public class TestA {  
      
    public static void main(String args[]) throws Exception {  
        //找到大楼的位置  
        FileInputStream input = new FileInputStream("dir.xls");  
        //告诉管理员  
        POIFSFileSystem f = new POIFSFileSystem(input);  
        //走到大楼楼下  
        HSSFWorkbook wb = new HSSFWorkbook(f);  
        //确认自己走到第几单元  
        HSSFSheet sheet = wb.getSheetAt(0);  
        //看一看有没有楼层  
        Iterator rows = sheet.rowIterator();  
        while (rows.hasNext()) {  
            //如果有我们一层层问  
            HSSFRow row = (HSSFRow)rows.next();  
            Iterator cells = row.cellIterator();  
            //如果有人开门  
            while(cells.hasNext()) {  
                //我们一户一户的登记  
                HSSFCell cell = (HSSFCell) cells.next();  
                //是先生还是小姐(对应的数据类型)  
                int cellType = cell.getCellType();  
                System.out.print(getValue(cell,cellType));  
            }  
            System.out.println("");  
        }  
          
    }  
      
    /** 
     * 值对象封装 
     * @param cell 
     * @param cellType 
     * @return 
     */  
    public static Object getValue(Cell cell,int cellType) {  
        if (cellType == Cell.CELL_TYPE_NUMERIC) {  
            return cell.getNumericCellValue() + "       |   ";  
        }  
        else if (cellType == Cell.CELL_TYPE_STRING) {  
            return cell.getRichStringCellValue() + "        |   ";  
        }  
        else if (cellType == Cell.CELL_TYPE_BOOLEAN) {  
            return cell.getBooleanCellValue() + "       |   ";  
        }  
        else if (cellType == Cell.CELL_TYPE_FORMULA) {  
            return cell.getCellFormula() + "        |   ";  
        }  
        else if (cellType == Cell.CELL_TYPE_BLANK) {  
            return "" + "       |   ";  
        }  
        else if (cellType == Cell.CELL_TYPE_ERROR) {  
            return "" + "       |   ";  
        }  
        else {  
            return "" + "       |   ";  
        }  
          
    }  
  
}  
