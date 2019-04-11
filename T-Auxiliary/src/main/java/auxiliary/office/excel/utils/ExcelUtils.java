package auxiliary.office.excel.utils;

import auxiliary.office.excel.EXCEL;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExcelUtils {

    private String fileName;

    private Workbook workbook;

    private HttpServletResponse response;

    public ExcelUtils() {}

    public ExcelUtils(String fileName, HttpServletResponse response) {
        this.response = response;
        if (!StringUtils.isEmpty(fileName)) {
            this.fileName = fileName;                //保存的文件名
        } else {

        }

        this.workbook = new HSSFWorkbook();      //工作簿
    }

    public void createSheet(String sheetName, String firstTitle, String[] rowName, List<Object[]> dataList) {

        Sheet sheet = this.workbook.createSheet(sheetName);             //创建第一个Sheet页

        Row row = sheet.createRow(0);       //创建一个行
        Cell cell = row.createCell(0);

        CellStyle valueStyle = this.getStyle(this.workbook);       //单元格样式对象

        CellStyle titleStyle = this.getTitleStyle(this.workbook);       //获取列头样式对象
        cell.setCellStyle(titleStyle);
        cell.setCellValue(firstTitle);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(
                0,                  //开始行
                1,                  //结束行
                0,                  //开始列
                (rowName.length - 1)       //结束列
        );
        sheet.addMergedRegion(cellRangeAddress);

        RegionUtil.setBorderTop(1, cellRangeAddress, sheet, this.workbook);
        RegionUtil.setBorderBottom(1, cellRangeAddress, sheet, this.workbook);
        RegionUtil.setBorderLeft(1, cellRangeAddress, sheet, this.workbook);
        RegionUtil.setBorderRight(1, cellRangeAddress, sheet, this.workbook);

        int columnNum = rowName.length;
        Row rowRowName = sheet.createRow(2);                // 在索引2的位置创建行(最顶端的行开始的第二行)

        //将列头设置到sheet的单元格中
        {
            for (int index = 0; index < columnNum; index++) {
                Cell cellRowName = rowRowName.createCell(index);                //创建列头对应个数的单元格
                cellRowName.setCellType(HSSFCell.CELL_TYPE_STRING);             //设置列头单元格的数据类型
                RichTextString text = new HSSFRichTextString(rowName[index]);
                cellRowName.setCellValue(text);                                 //设置列头单元格的值
                cellRowName.setCellStyle(titleStyle);                           //设置列头单元格样式
            }
        }

        //将查询出的数据设置到sheet对应的单元格中
        {
            this.setValue(sheet, dataList, valueStyle);
        }

        //让列宽随着导出的列长自动适应
        {
            for (int colNum = 0; colNum < columnNum; colNum++) {
                int columnWidth = sheet.getColumnWidth(colNum) / 256;
                for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {

                    Row currentRow;
                    //当前行未被使用过
                    if (sheet.getRow(rowNum) == null) {
                        currentRow = sheet.createRow(rowNum);
                    } else {
                        currentRow = sheet.getRow(rowNum);
                    }

                    if (currentRow.getCell(colNum) != null) {
                        Cell currentCell = currentRow.getCell(colNum);
                        if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {

                            if (!StringUtils.isEmpty(currentCell)) {
                                int length = currentCell.getStringCellValue().getBytes().length;
                                if (columnWidth < length) {
                                    columnWidth = length;
                                }
                            }
                        }
                    }
                }
                if (colNum == 0) {
                    sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
                } else {
                    sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
                }
            }
        }
    }

    /**
     * 设置值
     * @param sheet
     * @param dataList
     * @param valueStyle
     */
    private void setValue(Sheet sheet, List<Object[]> dataList, CellStyle valueStyle) {

        if (null != dataList || dataList.size() >= 1) {

            int count = dataList.size();
            for (int index = 0; index < count; index++) {

                Object[] obj = dataList.get(index);//遍历每个对象
                Row row = sheet.createRow(index + 3);//创建所需的行数
                row.setHeightInPoints(20);
                for (int j = 0; j < obj.length; j++) {

                    Cell cell = null;   //设置单元格的数据类型
                    cell = row.createCell(j, Cell.CELL_TYPE_STRING);
                    if (obj[j] != null && !"".equals(obj[j])) {
                        cell.setCellValue(obj[j].toString());      //设置单元格的值
                    } else {
                        cell.setCellValue("");
                    }

/*                    if (j == 0) {
                        cell = row.createCell(j, Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(index + 1);
                    } else {
                        cell = row.createCell(j, Cell.CELL_TYPE_STRING);
                        if (!"".equals(obj[j]) && obj[j] != null) {
                            cell.setCellValue(obj[j].toString());      //设置单元格的值
                        } else {
                            cell.setCellValue("");                    //设置单元格的值
                        }
                    }*/
                    cell.setCellStyle(valueStyle);                                   //设置单元格样式
                }
            }
        }
    }

    public void close() throws Exception {

        if (this.workbook != null) {
            try {
                if (this.response != null) {
                    this.response.setContentType("application/vnd.ms-excel;charset=utf-8");
                    this.response.setHeader("Content-Disposition", "attachment;filename=\""+new String(this.fileName.getBytes("gb2312"),"ISO8859-1"));
                    OutputStream out = this.response.getOutputStream();
                    workbook.write(out);
                    out.close();
                } else {
                    FileOutputStream outputStream = new FileOutputStream(EXCEL.Excel_Save_Path + this.fileName);
                    workbook.write(outputStream);
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
/*
        FileOutputStream fileOutput = new FileOutputStream(EXCEL.Excel_Save_Path + this.fileName);
        this.workbook.write(fileOutput);
        fileOutput.close();
*/
    }

    /**
     * 表头和标题样式设置
     * @param workbook
     * @return
     */
    private CellStyle getTitleStyle(Workbook workbook) {

        //设置字体
        Font font = workbook.createFont();

        //设置字体大小
        font.setFontHeightInPoints((short) 12);

        //字体加粗
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        //设置字体名字
        font.setFontName("微软雅黑");

        //设置样式;
        CellStyle cellStyle = workbook.createCellStyle();

        //上
        {
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);                      //边框
            cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());        //颜色
        }

        //下
        {
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);                   //边框
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());     //颜色
        }

        //左
        {
            cellStyle.setLeftBorderColor(CellStyle.BORDER_THIN);                //边框
            cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());       //颜色
        }

        //右
        {
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);                    //边框
            cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());       //颜色
        }

        //在样式用应用设置的字体;
        cellStyle.setFont(font);

        //设置自动换行;
        cellStyle.setWrapText(false);

        //设置水平对齐的样式为居中对齐;
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        //设置垂直对齐的样式为居中对齐;
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        return cellStyle;
    }

    /*
 * 列数据信息单元格样式
 */
    public CellStyle getStyle(Workbook workbook) {
        // 设置字体
        Font font = workbook.createFont();
        //设置字体名字
        font.setFontName("微软雅黑");
        //设置样式;
        CellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(HSSFColor.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(HSSFColor.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return style;
    }

    public static void main(String [] args) throws Exception {

/*        String[] rowsName = new String[]{"序列", "姓名","手机号码","呢称","所属机构","业务量","日期"};

        List<Object[]>  dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < 10; i++) {
            objs = new Object[rowsName.length];
            objs[0] = i + 1;
            objs[1] = "林少君";
            objs[2] = "135591934693";
            objs[3] = "LoderStar";
            objs[4] = "技术支撑部";
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            objs[5] = 100;
            objs[6] = date;
            dataList.add(objs);
        }

        ExcelUtils excelUtils = new ExcelUtils("aaa.xls", null);
        excelUtils.createSheet("员工业绩报表", "员工业绩报表", rowsName, dataList);
        excelUtils.close();*/
    }
}
