package auxiliary.office.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;

public class Demo9 {

    public static void main(String [] args) throws Exception {

        Workbook wb = new HSSFWorkbook();                    //工作簿
        Sheet sheet = wb.createSheet("第一个Sheet页");    //创建第一个Sheet页
        Row row = sheet.createRow(1);       //创建一个行

        Cell cell = row.createCell(1);      //创建一个单元格
        cell.setCellValue(4);

        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);       //底部边框
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());     //底部颜色

        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);         //左边边框
        cellStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex());       //左边边框颜色

        cellStyle.setBorderRight(CellStyle.BORDER_THIN);        //右边边框
        cellStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());       //左边边框颜色

        cellStyle.setBorderTop(CellStyle.BORDER_THIN); //上边边框
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cell.setCellStyle(cellStyle);

        FileOutputStream fileOutput = new FileOutputStream("c:\\工作簿.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }
}
