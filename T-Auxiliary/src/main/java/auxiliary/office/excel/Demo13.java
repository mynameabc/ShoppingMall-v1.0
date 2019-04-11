package auxiliary.office.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Demo13 {

    public static void main(String [] args) throws Exception {

        InputStream inputStream = new FileInputStream("c:\\工作簿.xls");
        POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
        Workbook wb = new HSSFWorkbook(fileSystem);
        Sheet sheet = wb.getSheetAt(0);             //获取第一个Sheet页
        Row row = sheet.createRow(0);                  //获取第一行
        Cell cell = row.getCell(0);                 //获取单元格
        if (null == cell) {
            cell = row.createCell(3);
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue("测试单元格");

        FileOutputStream fileOutput = new FileOutputStream("C:\\工作簿.xls");
        wb.write(fileOutput);
        fileOutput.close();
    }
}
