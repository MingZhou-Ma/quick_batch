package com.wanguo.quick_batch.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 描述：
 *
 * @author Badguy
 */
public class ExcelUtil {

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void exportXls(String sheetName, String titleValue, String[] headTitle, String[][] data, HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName);

        // 设置列宽
        for (int i = 0; i < headTitle.length; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }
        // 设置标题行列合并
        //sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));

        // 标题样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont titleFont = wb.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 20);
        titleStyle.setFont(titleFont);

        // 标题行
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(headTitle.length / 2);
        titleCell.setCellValue(titleValue);
        titleCell.setCellStyle(titleStyle);

        // 表头样式
        HSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont headFont = wb.createFont();
        headFont.setBold(true);
        headStyle.setFont(headFont);

        // 表头行
        HSSFRow headRow = sheet.createRow(1);
        HSSFCell headCell;
        for (int i = 0; i < headTitle.length; i++) {
            headCell = headRow.createCell(i);
            headCell.setCellValue(headTitle[i]);
            headCell.setCellStyle(headStyle);
        }

        // 内容样式
        HSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        // 内容行
        for (String[] aData : data) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int j = 0; j < aData.length; j++) {
                HSSFCell dataCell = dataRow.createCell(j);
                dataCell.setCellValue(aData[j]);
                dataCell.setCellStyle(dataStyle);
            }
        }

        //输出Excel文件
        response.setHeader("Content-disposition", "attachment; filename=details.xls");
        response.addHeader("Cache-Control", "no-cache");

        OutputStream output = response.getOutputStream();
        wb.write(output);
        output.flush();
        output.close();
    }

    public static void exportXlsx(String sheetName, String titleValue, String[] headTitle, String[][] data, HttpServletResponse response) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);

        // 设置列宽
        for (int i = 0; i < headTitle.length; i++) {
            sheet.setColumnWidth(i, 20 * 256);
        }
        // 设置标题行列合并
        //sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 2));

        // 标题样式
        XSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont titleFont = wb.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 20);
        titleStyle.setFont(titleFont);

        // 标题行
        XSSFRow titleRow = sheet.createRow(0);
        XSSFCell titleCell = titleRow.createCell(headTitle.length / 2);
        titleCell.setCellValue(titleValue);
        titleCell.setCellStyle(titleStyle);

        // 表头样式
        XSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        XSSFFont headFont = wb.createFont();
        headFont.setBold(true);
        headStyle.setFont(headFont);

        // 表头行
        XSSFRow headRow = sheet.createRow(1);
        XSSFCell headCell;
        for (int i = 0; i < headTitle.length; i++) {
            headCell = headRow.createCell(i);
            headCell.setCellValue(headTitle[i]);
            headCell.setCellStyle(headStyle);
        }

        // 内容样式
        XSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setAlignment(HorizontalAlignment.CENTER);

        // 内容行
        for (String[] aData : data) {
            XSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            for (int j = 0; j < aData.length; j++) {
                XSSFCell dataCell = dataRow.createCell(j);
                dataCell.setCellValue(aData[j]);
                dataCell.setCellStyle(dataStyle);
            }
        }

        //输出Excel文件
        response.setHeader("Content-disposition", "attachment; filename=details.xlsx");
        response.addHeader("Cache-Control", "no-cache");

        OutputStream output = response.getOutputStream();
        wb.write(output);
        output.flush();
        output.close();
    }

    public static Workbook getWorkbook(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) {     //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    public static void checkExcelValid(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel");
        }
    }

    public static void read(int... args) {
        try {
            File excelFile = new File("E:\\aaa.xlsx"); // 创建文件对象
            FileInputStream is = new FileInputStream(excelFile); // 文件流
            checkExcelValid(excelFile);
            Workbook workbook = getWorkbook(is, excelFile);
            if (null != workbook) {
                for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
                    Sheet sheet = workbook.getSheetAt(numSheet);
                    if (null == sheet) {
                        continue;
                    }
                    for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        if (null == row) {
                            continue;
                        }
                        for (int arg : args) {
                            Cell cell = row.getCell(arg);
                            if (null == cell) {
                                continue;
                            }
                            double s = row.getCell(arg).getNumericCellValue();
                            long longVal = Math.round(s);
                            Object inputValue;
                            if (Double.parseDouble(longVal + ".0") == s) {
                                inputValue = longVal;
                            } else {
                                inputValue = s;
                            }
                            System.out.println(String.valueOf(inputValue));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
