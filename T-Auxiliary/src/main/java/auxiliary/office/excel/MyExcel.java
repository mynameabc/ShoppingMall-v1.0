package auxiliary.office.excel;

import communal.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;

/**
 * POI导出excel文件工具类
 */
@Component
public class MyExcel {

    private Workbook wb;

    private Sheet sheet;

    public MyExcel() {}

    /**
     * 导出
     * @param version
     */
    public MyExcel(String version) {

        if (version.equals(EXCEL.OLD_VERSION)) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }

        this.sheet = wb.createSheet("第一个Sheet页");    //创建第一个Sheet页
    }

    /**
     * 导出
     * @param version
     * @param sheetName
     */
    public MyExcel(String version, String sheetName) {

        if (version.equals(EXCEL.OLD_VERSION)) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }

        this.sheet = wb.createSheet(sheetName);    //创建第一个Sheet页
    }



    /**
     * 创建行
     * @param number
     * @return
     */
    public Row createRow(int number) {
        return sheet.createRow(number);
    }

    public Cell setDouble(Row row, int number, Double value) {
        Cell cell = row.createCell(number);    //创建一个单元格
        cell.setCellValue(value);              //给单元格设置值
        return cell;
    }

    public Cell setString(Row row, int number, String value) {
        Cell cell = row.createCell(number);    //创建一个单元格
        cell.setCellValue(value);              //给单元格设置值
        return cell;
    }

    public Cell setBoolean(Row row, int number, boolean value) {
        Cell cell = row.createCell(number);    //创建一个单元格
        cell.setCellValue(value);              //给单元格设置值
        return cell;
    }

    public Cell setDate(Row row, int number, String value) {
        Cell cell = row.createCell(number);    //创建一个单元格
        cell.setCellValue(value);              //给单元格设置值
        return cell;
    }

    public Cell setDateTime(Row row, int number, String value) {
        Cell cell = row.createCell(number);                           //创建一个单元格
        cell.setCellValue(DateUtil.stringFormatDateTime(value));      //给单元格设置值
        return cell;
    }

    public void write(String pathFile) {
        try {
            FileOutputStream fileOutput = new FileOutputStream(pathFile);
            this.wb.write(fileOutput);
            fileOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {

        MyExcel myExcel = new MyExcel(EXCEL.OLD_VERSION);
        Row row = myExcel.createRow(0);

        myExcel.write("c:\\aaa.xls");
    }
}
