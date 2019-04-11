package auxiliary.office.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

public class Demo4 {

    public static void main(String [] args) throws Exception {

        Workbook wb = new HSSFWorkbook();                    //工作簿
        Sheet sheet = wb.createSheet("第一个Sheet页");    //创建第一个Sheet页
        Row row = sheet.createRow(0);       //创建一个行

        Cell cell = row.createCell(0);      //创建一个单元格 第1列
        cell.setCellValue(new Date());         //给单元格设置值

        CreationHelper creationHelper = wb.getCreationHelper();
        CellStyle cellStyle = wb.createCellStyle();     //单元格样式
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));

        cell = row.createCell(1);           //创建第二列
        cell.setCellValue(communal.util.DateUtil.stringFormatDateTime("2010-12-12 12:00:01"));
        cell.setCellStyle(cellStyle);

        cell = row.createCell(2);
        cell.setCellValue(Calendar.getInstance());
        cell.setCellStyle(cellStyle);

        FileOutputStream fileOutput = new FileOutputStream("c:\\工作簿.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }
}
