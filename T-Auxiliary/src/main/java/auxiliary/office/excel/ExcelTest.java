package auxiliary.office.excel;

import model.entity.extend.EmployeeExtend;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelTest {

    public void aaa(String sheetName, List<EmployeeExtend> dataList) throws Exception {

        if (null == dataList || (dataList.size() <= 0))
            return;

        Workbook wb = new HSSFWorkbook();           //工作簿
        Sheet sheet = wb.createSheet(sheetName);    //创建第一个Sheet页

        sheet.setColumnWidth(0,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(1,  (int)((13 + 0.72) * 256));
        sheet.setColumnWidth(2,  (int)((16 + 0.72) * 256));
        sheet.setColumnWidth(3,  (int)((15 + 0.72) * 256));
        sheet.setColumnWidth(4,  (int)((5 + 0.72) * 256));
        sheet.setColumnWidth(5,  (int)((15 + 0.72) * 256));
        sheet.setColumnWidth(6,  (int)((16 + 0.72) * 256));

        int count = 0;
        for (int index = 0; index < dataList.size(); index++) {

            EmployeeExtend employeeExtend = dataList.get(index);
            Row row = sheet.createRow(index + 1);      //创建一个行

            row.setHeightInPoints(23);

            stringValue(wb, row, 0, String.valueOf(index + 1), HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER);
            stringValue(wb, row, 1, employeeExtend.getJobNumber(), HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER);
            stringValue(wb, row, 2, employeeExtend.getOfficeName(), HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER);
            stringValue(wb, row, 3, employeeExtend.getRealName(), HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER);

            if (employeeExtend.getSex()) {
                stringValue(wb, row, 4, "男", HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER);
            } else {
                stringValue(wb, row, 4, "女", HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER);
            }

            stringValue(wb, row, 5, employeeExtend.getMobile(), HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER);
            stringValue(wb, row, 6, employeeExtend.getName(), HSSFCellStyle.ALIGN_CENTER, HSSFCellStyle.VERTICAL_CENTER);
        }

        CreationHelper creationHelper = wb.getCreationHelper();
        CellStyle cellStyle = wb.createCellStyle();     //单元格样式
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd hh:mm:ss"));

        FileOutputStream fileOutput = new FileOutputStream("c:\\工作簿.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }

    /**
     * 创建一个单元格并为其设定指定的对齐方式
     * @param wb        工作簿
     * @param row       行
     * @param column    列
     * @param halign    水平方向对齐方式
     * @param valign    垂直方向对齐方式
     */
    private static void stringValue(Workbook wb, Row row, int column, String value, short halign, short valign) {

        Cell cell = row.createCell(column);     //创建单元格
        cell.setCellValue(value);               //设置值
        CellStyle cellStyle = wb.createCellStyle();                 //创建单元格样式
        cellStyle.setAlignment(halign);                             //设置单元格水平方向对齐方式
        cellStyle.setVerticalAlignment(valign);                     //设置单元格垂直方向对齐方式

        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);                   //底部边框
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());     //底部颜色

        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);                     //左边边框
        cellStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex());       //左边边框颜色

        cellStyle.setBorderRight(CellStyle.BORDER_THIN);                    //右边边框
        cellStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());       //左边边框颜色

        cellStyle.setBorderTop(CellStyle.BORDER_THIN);                      //上边边框
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cell.setCellStyle(cellStyle);                               //设置单元格样式
    }

    private static void booleanValue(Workbook wb, Row row, int column, boolean value, short halign, short valign) {

        Cell cell = row.createCell(column);     //创建单元格
        cell.setCellValue(value);               //设置富文本
        CellStyle cellStyle = wb.createCellStyle();                 //创建单元格样式
        cellStyle.setAlignment(halign);                             //设置单元格水平方向对齐方式
        cellStyle.setVerticalAlignment(valign);                     //设置单元格垂直方向对齐方式

        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);           //底部边框
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());     //底部颜色

        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);         //左边边框
        cellStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex());       //左边边框颜色

        cellStyle.setBorderRight(CellStyle.BORDER_THIN);        //右边边框
        cellStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());       //左边边框颜色

        cellStyle.setBorderTop(CellStyle.BORDER_THIN); //上边边框
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cell.setCellStyle(cellStyle);                               //设置单元格样式
    }

    private static void doubleValue(Workbook wb, Row row, int column, Double value, short halign, short valign) {

        Cell cell = row.createCell(column);     //创建单元格
        cell.setCellValue(value);               //设置富文本
        CellStyle cellStyle = wb.createCellStyle();                 //创建单元格样式
        cellStyle.setAlignment(halign);                             //设置单元格水平方向对齐方式
        cellStyle.setVerticalAlignment(valign);                     //设置单元格垂直方向对齐方式

        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);           //底部边框
        cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());     //底部颜色

        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);         //左边边框
        cellStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex());       //左边边框颜色

        cellStyle.setBorderRight(CellStyle.BORDER_THIN);        //右边边框
        cellStyle.setRightBorderColor(IndexedColors.BLUE.getIndex());       //左边边框颜色

        cellStyle.setBorderTop(CellStyle.BORDER_THIN); //上边边框
        cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

        cell.setCellStyle(cellStyle);                               //设置单元格样式
    }

    public static void main(String args[]) throws Exception {

        List<EmployeeExtend> employeeExtendList = new ArrayList<>();

        EmployeeExtend employeeExtend = new EmployeeExtend();
        employeeExtend.setJobNumber("这是工号");
        employeeExtend.setOfficeName("这是机构");
        employeeExtend.setRealName("这是真实姓名");
        employeeExtend.setSex(true);
        employeeExtend.setMobile("13559193463");
        employeeExtend.setName("呢称");

        EmployeeExtend employeeExtend1 = new EmployeeExtend();
        employeeExtend1.setJobNumber("这是工号");
        employeeExtend1.setOfficeName("这是机构");
        employeeExtend1.setRealName("这是真实姓名");
        employeeExtend1.setSex(true);
        employeeExtend1.setMobile("13559193463");
        employeeExtend1.setName("呢称");

        employeeExtendList.add(employeeExtend);
        employeeExtendList.add(employeeExtend1);

        new ExcelTest().aaa("这是sheetName", employeeExtendList);
    }
}
