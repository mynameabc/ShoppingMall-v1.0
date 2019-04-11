package auxiliary.office.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;

public class Demo2 {

    public static void main(String [] args) throws Exception {

        Workbook wb = new HSSFWorkbook();       //工作簿
        wb.createSheet("第一个Sheet页");
        wb.createSheet("第二个Sheet页");
        FileOutputStream fileOutput = new FileOutputStream("c:\\用Poi搞出来的Sheet页.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }
}
