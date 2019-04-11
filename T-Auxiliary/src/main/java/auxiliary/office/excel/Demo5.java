package auxiliary.office.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.util.Date;

public class Demo5 {

    public static void main(String [] args) throws Exception {

        Workbook wb = new HSSFWorkbook();                    //工作簿
        Sheet sheet = wb.createSheet("第一个Sheet页");    //创建第一个Sheet页
        Row row = sheet.createRow(0);       //创建一个行
        Cell cell = row.createCell(0);      //创建一个单元格 第1列
        cell.setCellValue(new Date());                  //给单元格设置值

        row.createCell(1).setCellValue(1);
        row.createCell(2).setCellValue("一个字符串");
        row.createCell(3).setCellValue(true);
        row.createCell(4).setCellValue(HSSFCell.CELL_TYPE_NUMERIC);
        row.createCell(5).setCellValue(false);

        FileOutputStream fileOutput = new FileOutputStream("c:\\工作簿.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }
}
