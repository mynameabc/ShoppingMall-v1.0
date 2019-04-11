package auxiliary.office.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;

public class Demo11 {

    public static void main(String [] args) throws Exception {

        Workbook wb = new HSSFWorkbook();                    //工作簿
        Sheet sheet = wb.createSheet("第一个Sheet页");    //创建第一个Sheet页
        Row row = sheet.createRow(1);       //创建一个行

        Cell cell = row.createCell(1);
        cell.setCellValue("单元格合并测试");

        sheet.addMergedRegion(new CellRangeAddress(
                1,      //起始行
                2,      //结束行
                1,       //起始列
                2)       //结束列
        );

        FileOutputStream fileOutput = new FileOutputStream("c:\\工作簿.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }
}
