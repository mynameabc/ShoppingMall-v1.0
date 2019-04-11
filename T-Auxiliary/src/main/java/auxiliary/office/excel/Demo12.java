package auxiliary.office.excel;

import auxiliary.office.excel.utils.ExcelUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;

public class Demo12 {

    public static void main(String [] args) throws Exception {

        Workbook wb = new HSSFWorkbook();                    //工作簿
        Sheet sheet = wb.createSheet("第一个Sheet页");    //创建第一个Sheet页
        Row row = sheet.createRow(1);       //创建一个行

        //创建一个字体处理类
        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 24);
        font.setFontName("Courier New");
        font.setItalic(true);       //斜体
        font.setStrikeout(true);    //无效线

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);

        Cell cell = row.createCell((short)1);
        cell.setCellValue("This is test of fonts");
        cell.setCellStyle(cellStyle);

        FileOutputStream fileOutput = new FileOutputStream("c:\\工作簿.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }
}
