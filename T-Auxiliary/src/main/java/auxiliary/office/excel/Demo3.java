package auxiliary.office.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;

public class Demo3 {

    public static void main(String [] args) throws Exception {

        Workbook wb = new HSSFWorkbook();                    //工作簿
        Sheet sheet = wb.createSheet("第一个Sheet页");    //创建第一个Sheet页
        Row row = sheet.createRow(0);       //创建一个行
        Cell cell = row.createCell(0);      //创建一个单元格 第1列
        cell.setCellValue(1);                  //给单元格设置值

        row.createCell(1).setCellValue(1.2);                    //创建一个单元格 第2列 值是1.2

        row.createCell(2).setCellValue("这是一个字符串类型");    //创建一个单元格 第3列 值为一个字符串

        row.createCell(3).setCellValue(false);                  //创建一个单元格 第4列 值为布尔类型

        FileOutputStream fileOutput = new FileOutputStream("c:\\用Poi搞出来的Cell.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }
}
