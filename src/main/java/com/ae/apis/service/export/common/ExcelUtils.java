package com.ae.apis.service.export.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
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

    public static CellStyle headerStyle(SXSSFSheet sheet) {
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

    public static CellStyle stringStyle(SXSSFSheet sheet, String... params) {
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
            CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(params[0]));
        }
        return cellStyle;
    }


    public static CellStyle numberStyle(SXSSFSheet sheet, String... params) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(false);
        font.setFontHeightInPoints((short) 12); // font size

        // Create CellStyle
        var cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);

        if (params != null && params.length > 0) {
            CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(params[0]));
        }
        return cellStyle;
    }

    public static CellStyle dateStyle(SXSSFSheet sheet, String... params) {
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
            CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(params[0]));
        }
        return cellStyle;
    }


    // Auto resize column width
    public static void autoResizeColumn(SXSSFSheet sheet, int lastColumn) {
        int w;
        int maxColumnWidth = 255 * 256;
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.trackAllColumnsForAutoSizing();
            sheet.autoSizeColumn(columnIndex);
            // The maximum column width
            // for an individual cell is 255 characters
            w = (int) (sheet.getColumnWidth(columnIndex) * 1.3);
            if (w > maxColumnWidth) {
                w = maxColumnWidth;
            }
            sheet.setColumnWidth(columnIndex, (int) (w));
        }
    }

    // Create output file
    public static void createOutputFile(SXSSFWorkbook workbook, String excelName, HttpServletResponse res) {
        try (OutputStream os = res.getOutputStream()) {
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
