package auxiliary.office.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;

public class Demo1 {

    public static void main(String [] args) throws Exception {

        Workbook wb = new HSSFWorkbook();       //工作簿
        wb.createSheet("Sheet1");
        FileOutputStream fileOutput = new FileOutputStream("c:\\用Poi搞出来的工作簿.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }
}
