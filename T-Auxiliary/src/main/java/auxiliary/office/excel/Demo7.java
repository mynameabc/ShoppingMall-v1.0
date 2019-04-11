package auxiliary.office.excel;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.InputStream;

public class Demo7 {

    public static void main(String [] args) throws Exception {

        InputStream is = new FileInputStream("c://二货名单.xls");
        POIFSFileSystem fs = new POIFSFileSystem(is);
        HSSFWorkbook wb = new HSSFWorkbook(fs);

        ExcelExtractor excelExtractor = new ExcelExtractor(wb);
        excelExtractor.setIncludeSheetNames(false);         //我们不需要Sheet页的名字
        System.out.println(excelExtractor.getText());
    }
}
