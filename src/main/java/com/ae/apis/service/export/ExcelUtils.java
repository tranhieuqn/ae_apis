package com.ae.apis.service.export;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.Serializable;

public class ExcelUtils implements Serializable {
    private static final long serialVersionUID = -9078083261202261084L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtils.class);

    private ExcelUtils() {
    }

    public static ExcelUtils getInstance() {
        return ExcelUtilsHelper.INSTANCE;
    }

    private static class ExcelUtilsHelper {
        private static final ExcelUtils INSTANCE = new ExcelUtils();
    }

    public static CellStyle headerStyle(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        return cellStyle;
    }

    public static CellStyle stringStyle(Sheet sheet, String... params) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(false);
        font.setFontHeightInPoints((short) 12); // font size

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        if (params != null && params.length > 0) {
            short format = (short) BuiltinFormats.getBuiltinFormat(params[0]);
            cellStyle.setDataFormat(format);
        }
        return cellStyle;
    }


    public static CellStyle numberStyle(Sheet sheet, String... params) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(false);
        font.setFontHeightInPoints((short) 12); // font size

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        if (params != null && params.length > 0) {
            short format = (short) BuiltinFormats.getBuiltinFormat(params[0]);
            cellStyle.setDataFormat(format);
        }
        return cellStyle;
    }

    public static CellStyle dateStyle(Sheet sheet, String... params) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(false);
        font.setFontHeightInPoints((short) 12); // font size

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        if (params != null && params.length > 0) {
            short format = (short) BuiltinFormats.getBuiltinFormat(params[0]);
            cellStyle.setDataFormat(format);
        }
        return cellStyle;
    }


    // Auto resize column width
    public static void autoResizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }

    // Create output file
    public static void createOutputFile(Workbook workbook,String excelName, HttpServletResponse res) {
        try(OutputStream os = res.getOutputStream()) {
            // save
            res.setContentType("application/x-ms-excel");
            res.setContentType("application/x-felix; charset=us-ascii");
            res.setHeader("Content-Transfer-Encoding", "7bit");
            res.setHeader("Content-Disposition", String.format("attachment; filename=\"%s.xlsx\"", StringUtils.isEmpty(excelName) ? "Export" : excelName));

            workbook.write(os);
            os.close();
            workbook.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.error("renderExcel() export file exception: " + ex.getMessage());
        }
    }
}
